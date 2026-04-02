//Attribut au paquet
package hashiGRP3.Controller;

//Imports
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.MoteurIndice;
import hashiGRP3.Logic.Aide.Techniques.*;
import hashiGRP3.Logic.General;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Pont;
import hashiGRP3.ObjectGraphique.ileGraphique;
import hashiGRP3.ObjectGraphique.pontGraphique;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/** Classe de controlleur d'une grille de jeu */
public class GrilleController extends ManageController {

    /** La grille au niveau logique */
    private Hashi hashi;

    /** Le système d'aide */
    private MoteurIndice moteurIndice;
    private ChangeListener<Bounds> boundsListener;

    AnimationTimer animationTimer;
    double startup;

    double elapsedBefore = 0;
    @FXML
    private VBox sidePanel;
    @FXML
    private Pane gamePane;
    @FXML
    private Label timer;
    @FXML
    private Label win;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button hintButton;
    @FXML
    private Button hypothesisButton;
    @FXML
    private Label labelTitreGrille;

    private boolean onCheck = false;

    private static final List<KeyCode> KONAMI_CODE = List.of(
            KeyCode.Z, KeyCode.Z, KeyCode.S, KeyCode.S,
            KeyCode.Q, KeyCode.D, KeyCode.Q, KeyCode.D,
            KeyCode.B, KeyCode.A);
    private int konamiIndex = 0;
    private boolean konamiActivated = false;

    @FXML
    public void initialize() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                double t = General.getElapsedTime();
                timer.setText("Chrono : " + (int) t);
            }
        };

        Tooltip t1 = new Tooltip("Annuler le dernier coup");
        t1.setShowDelay(Duration.millis(300));
        undoButton.setTooltip(t1);

        Tooltip t2 = new Tooltip("Rétablir le coup annulé");
        t2.setShowDelay(Duration.millis(300));
        redoButton.setTooltip(t2);

        Tooltip t3 = new Tooltip("Vérifier la grille et revenir à l'état sans erreur");
        t3.setShowDelay(Duration.millis(300));
        checkButton.setTooltip(t3);

        Tooltip t4 = new Tooltip("Afficher un indice pour la grille");
        t4.setShowDelay(Duration.millis(300));
        hintButton.setTooltip(t4);

        Tooltip t5 = new Tooltip("Activer le mode hypothèse (coups temporaires)");
        t5.setShowDelay(Duration.millis(300));
        hypothesisButton.setTooltip(t5);

        gamePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(e -> {
                    handleKonamiKey(e.getCode());
                    if (isKonamiCodeEntered()) {
                        for (var pont : hashi.getPonts()) {
                            pont.setEtatActuel(pont.getEtatCorrect());
                        }
                        konamiActivated = false;
                        konamiIndex = 0;
                        drawGrid(hashi, gamePane.getWidth());
                    }
                });
            }
        });
    }

    private void chargerGrille() {
        int grid_num = General.getNum_grille();

        if (labelTitreGrille != null) {
            labelTitreGrille.setText("Grille numéro " + grid_num);
        }

        int folderIndex = (grid_num - 1) / 5;
        String[] folders = { "7x7", "10x10", "12x12" };
        String folder = folders[folderIndex];
        int fileNumber = ((grid_num - 1) % 5) + 1;
        String resourcePath = "/hashiGRP3/" + folder + "/hashi" + fileNumber + ".txt";

        URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("Fichier " + resourcePath + " non trouvé !");
            return;
        }

        try {
            Path chemin = Path.of(url.toURI());
            hashi = Import.chargerFichier(chemin);
            hashi.initialisationToutLesPonts();
            hashi.initialisationToutLesConflits();
            moteurIndice = new MoteurIndice(List.of(new TechniqueSaturation(), new TechniqueIsolation(),
                    new TechniqueSaturationMoinsDeux(), new TechniqueSaturationMoinsUn(),
                    new TechniqueSaturationMoinsUnSpe(), new TechniqueSaturationCapaciteMax(),
                    new TechniqueIsolationDeuxIles(), new TechniqueIsolationTroisIles(),
                    new TechniqueIsolationSegmentIle(), new TechniqueIsolationIle(), new TechniquesBloquePont()));

            General.setHashi(hashi);
            int idPartie = General.getDb().creerPartie(General.getIdUtilisateur(), General.getNum_grille());
            General.setId_partie(idPartie);
            hashi.remplirHistorique();

            win.setVisible(false);

            undoButton.setDisable(hashi.isUndoEmpty());
            redoButton.setDisable(hashi.isRedoEmpty());

            StackPane parent = (StackPane) gamePane.getParent();

            // Supprimer l'ancien listener avant d'en ajouter un nouveau
            if (boundsListener != null) {
                parent.layoutBoundsProperty().removeListener(boundsListener);
            }
            boundsListener = (obs, oldBounds, newBounds) -> {
                drawGrid(hashi, newBounds.getWidth());
            };
            parent.layoutBoundsProperty().addListener(boundsListener);

            this.elapsedBefore = General.getDb().checkScorePartie();

            General.setElapsedTime(elapsedBefore);
            this.start_timer();

            System.out.println(this.startup);

            drawGrid(hashi, parent.getWidth());

        } catch (URISyntaxException | java.io.IOException ex) {
            System.err.println("Erreur au chargement : " + ex.getMessage());
        }
    }

    @Override
    public void refreshGrilles() {
        sidePanel.getChildren().clear();
        chargerGrille();
        if (General.getHashi().getHypothese()) {
            onHypothesisClick();
        }

    }

    public void start_timer() {
        General.startTimer();
        animationTimer.start();
    }

    public double stop_timer() {
        animationTimer.stop();
        General.stopTimer();
        double t = General.getElapsedTime();
        return t;
    }

    private void drawGrid(Hashi hashi, double paneWidth) {
        int nbColonnes = hashi.getTaille().x;
        int nbLignes = hashi.getTaille().y;

        StackPane parent = (StackPane) gamePane.getParent();
        double cellSize = Math.min(
                (parent.getWidth() - 70) / (nbColonnes + 1),
                (parent.getHeight() - 70) / (nbLignes + 1));

        double gridWidth = cellSize * (nbColonnes + 1);
        double gridHeight = cellSize * (nbLignes + 1);

        gamePane.setPrefSize(gridWidth, gridHeight);
        gamePane.setMaxSize(gridWidth, gridHeight);

        gamePane.getChildren().clear();

        dessinerGrille(nbColonnes, nbLignes, cellSize);
        dessinerPonts(hashi, cellSize);
        dessinerIle(hashi, cellSize);
        dessinerIndices(nbColonnes, nbLignes, cellSize);
        if (hashi.estGagne() && !hashi.getHypothese()) {
            double score = stop_timer();
            General.getDb().updateScorePartie(score);
            win.setVisible(true);
            // win.setVisible(true);
            showWin();
        }
    }

    private void dessinerIndices(int cols, int rows, double size) {
        double fontSize = Math.min(14, Math.max(8, size * 0.5));
        Color indexColor = Color.web("#888888");

        for (int x = 0; x < cols + 1; x++) {
            String label = String.format("%02d", x);
            double centerX = x * size + size / 2;
            Text top = new Text(label);
            top.setFont(Font.font("System", FontWeight.BOLD, fontSize));
            top.setFill(indexColor);
            top.setTranslateX(centerX - top.getLayoutBounds().getWidth() / 2);
            top.setTranslateY(-size + size * 0.80);
            gamePane.getChildren().add(top);
        }

        for (int y = 0; y < rows + 1; y++) {
            String label = String.format("%02d", y);
            double centerY = y * size + size * 0.72;
            Text left = new Text(label);
            left.setFont(Font.font("System", FontWeight.BOLD, fontSize));
            left.setFill(indexColor);
            left.setTranslateX(-size + size * 0.28);
            left.setTranslateY(centerY - left.getLayoutBounds().getHeight() / 2);
            gamePane.getChildren().add(left);
        }
    }

    /** Affiche une pop-op lorsqu'on gagne */
    private void showWin() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Attacher la pop-up à la fenêtre principale (mac)
        if (gamePane.getScene() != null && gamePane.getScene().getWindow() != null) {
            alert.initOwner(gamePane.getScene().getWindow());
        }

        alert.setTitle("Victoire !");
        alert.setHeaderText("Félicitations, vous avez réussi la grille !");
        alert.setContentText("Que souhaitez-vous faire ?");

        ButtonType btnRejouer = new ButtonType("Rejouer", ButtonData.OK_DONE);
        ButtonType btnMenu = new ButtonType("Retour au menu", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnRejouer, btnMenu);

        alert.showAndWait().ifPresent(result -> {
            if (result == btnRejouer) {
                onResetClick();
            } else if (result == btnMenu) {
                Button dummyButton = new Button();
                dummyButton.setUserData("selectGrille");
                ActionEvent event = new ActionEvent(dummyButton, null);
                changeScene(event);
            }
        });
    }

    private void dessinerGrille(int cols, int rows, double size) {
        for (int y = 0; y <= rows; y++) {
            for (int x = 0; x <= cols; x++) {
                Rectangle cell = new Rectangle(size, size);
                cell.setTranslateX(x * size);
                cell.setTranslateY(y * size);
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.web("#E0E0E0"));
                gamePane.getChildren().add(cell);
            }
        }
    }

    private void dessinerPonts(Hashi hashi, double size) {
        for (var pont : hashi.getPonts()) {
            pontGraphique pg = new pontGraphique(pont);
            gamePane.getChildren().add(pg.draw(size, this::onPontClicked));
        }
    }

    private void onPontClicked(Pont pont) {
        hashi.jouer(pont);
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
    }

    private void dessinerIle(Hashi hashi, double size) {
        for (Ile island : hashi.getIles()) {
            ileGraphique ig = new ileGraphique(island);
            gamePane.getChildren().add(ig.draw(size));
        }
    }

    @FXML
    private void onUndoClick() {
        hashi.undo();
        drawGrid(hashi, gamePane.getWidth());
        undoButton.setDisable(hashi.isUndoEmpty());
        redoButton.setDisable(hashi.isRedoEmpty());
    }

    @FXML
    private void onRedoClick() {
        hashi.redo();
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
    }

    @FXML
    private void onResetClick() {
        hashi.Reset();
        animationTimer.stop();
        General.resetTimer();
        General.getDb().updateScorePartie(0.0);
        start_timer();

        drawGrid(hashi, gamePane.getWidth());

        undoButton.setDisable(true);
        redoButton.setDisable(true);
    }

    @FXML
    protected void onHypothesisClick() {
        Label title = createTitle("Mode Hypothèse");

        hashi.setModeHypothese(true);

        if (hintButton != null)
            hintButton.setDisable(true);
        if (checkButton != null)
            checkButton.setDisable(true);
        if (hypothesisButton != null)
            hypothesisButton.setDisable(true);

        Text desc = new Text("Vous êtes en mode hypothèse. Vos coups sont temporaires.");
        desc.setWrappingWidth(180);
        Button btnValidate = new Button("Valider");
        Button btnCancel = new Button("Annuler");
        btnValidate.setStyle("-fx-text-fill: #005500;");
        btnCancel.setStyle("-fx-text-fill: #8b0000;");
        btnValidate.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnValidate, Priority.ALWAYS);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        btnValidate.setOnAction(e -> {
            System.out.println("Hypothèse validée");
            hashi.validerHypothese();
            General.getDb().validerHypothese();
            drawGrid(hashi, gamePane.getWidth());
            sidePanel.getChildren().clear();

            if (hintButton != null)
                hintButton.setDisable(false);
            if (checkButton != null)
                checkButton.setDisable(false);
            if (hypothesisButton != null)
                hypothesisButton.setDisable(false);
        });

        btnCancel.setOnAction(e -> {
            System.out.println("Hypothèse annulée");
            hashi.annulerHypothese();
            General.getDb().annulerHypothese();
            drawGrid(hashi, gamePane.getWidth());
            sidePanel.getChildren().clear();

            if (hintButton != null)
                hintButton.setDisable(false);
            if (checkButton != null)
                checkButton.setDisable(false);
            if (hypothesisButton != null)
                hypothesisButton.setDisable(false);
        });

        HBox buttonBox = new HBox(10, btnValidate, btnCancel);
        updateSidePanel(title, desc, buttonBox);
    }

    @FXML
    protected void onCheckClick() {
        if (onCheck == false) {
            onCheck = true;
            Label title = createTitle("Vérification");
            Label status = new Label("Il y a " + hashi.getNbErreur() + " erreurs sur la grille.");
            status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            Text question = new Text("Voulez-vous revenir au dernier état sans erreur ?");
            question.setWrappingWidth(180);
            Button btnYes = new Button("Oui");
            Button btnNo = new Button("Non");
            btnYes.setStyle("-fx-base: #f0f0f0;");
            btnNo.setStyle("-fx-base: #f0f0f0;");
            btnYes.setPrefWidth(80);
            btnNo.setPrefWidth(80);
            HBox actionBox = new HBox(10, btnYes, btnNo);
            updateSidePanel(title, status, new Separator(), question, actionBox);
        } else {
            sidePanel.getChildren().clear();
            onCheck = false;
        }

    }

    @FXML
    protected void onHintClick() {
        Label title = createTitle("Indice");
        Optional<IndiceResultat> resultat = moteurIndice.proposerProchainIndice(hashi);
        if (resultat.isEmpty()) {
            Text msg = new Text("Aucun indice disponible pour le moment.");
            updateSidePanel(title, new Separator(), msg);
            return;
        }
        IndiceResultat indice = resultat.get();
        Label techniqueName = new Label(indice.getNomTechnique());
        techniqueName.setWrapText(true);
        Text explication = new Text(indice.getExplication());
        explication.setWrappingWidth(180);
        updateSidePanel(title, new Separator(), techniqueName, explication);
    }

    private void updateSidePanel(javafx.scene.Node... nodes) {
        sidePanel.getChildren().clear();
        sidePanel.getChildren().addAll(nodes);
    }

    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        label.setStyle("-fx-text-fill: #333333;");
        return label;
    }

    private void handleKonamiKey(KeyCode key) {
        if (konamiActivated)
            return;
        if (key == KONAMI_CODE.get(konamiIndex)) {
            konamiIndex++;
            if (konamiIndex == KONAMI_CODE.size()) {
                konamiActivated = true;
            }
        } else {
            konamiIndex = (key == KONAMI_CODE.get(0)) ? 1 : 0;
        }
    }

    public boolean isKonamiCodeEntered() {
        return konamiActivated;
    }

    /*
     * réactive les bouttons quand l'utilisateur quiet la grille (quand le mode
     * hypothèse est actif)
     */
    @FXML
    @Override
    public void changeScene(ActionEvent event) {
        double score = stop_timer();
        General.getDb().updateScorePartie(score);
        General.resetTimer();
        General.getHashi().setModeHypothese(false);
        if (hintButton != null)
            hintButton.setDisable(false);
        if (checkButton != null)
            checkButton.setDisable(false);
        if (hypothesisButton != null)
            hypothesisButton.setDisable(false);
        super.changeScene(event);
    }
}
