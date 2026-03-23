package hashiGRP3.Controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.MoteurIndice;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolation;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturation;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsDeux;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsUn;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsUnSpe;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationQuatreCoin;
import hashiGRP3.Logic.General;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Pont;
import hashiGRP3.ObjectGraphique.ileGraphique;
import hashiGRP3.ObjectGraphique.pontGraphique;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

/* Class */
public class GrilleController extends ManageController {

    // ← ICI au niveau de la classe, pas dans une méthode
    private Hashi hashi;
    private MoteurIndice moteurIndice;
    private ChangeListener<Bounds> boundsListener;

    AnimationTimer animationTimer;
    double startup;

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
                double t = (currentTime - startup) / 1000000000;
                timer.setText("Chrono : " + (int) t);
            }
        };

        gamePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(e -> {
                    handleKonamiKey(e.getCode());
                    if (isKonamiCodeEntered()) {
                        for (var pont : hashi.getPonts()) {
                            pont.setEtatActuel(pont.getEtatCorrect());
                        }
                        drawGrid(hashi, gamePane.getWidth());
                    }
                });
            }
        });
    }

    private void chargerGrille() {
        sidePanel.getChildren().clear();
        int grid_num = General.getNum_grille();

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
                    new TechniqueSaturationMoinsUnSpe(), new TechniqueSaturationQuatreCoin()));

            General.setHashi(hashi);
            hashi.remplirHistorique();

            win.setVisible(false);

            // si on est en mode hypothèse on remet la fenétre de base
            if (General.getHashi().getHypothese()) {
                onHypothesisClick();
            }

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

            drawGrid(hashi, parent.getWidth());

        } catch (URISyntaxException | java.io.IOException ex) {
            System.err.println("Erreur au chargement : " + ex.getMessage());
        }
    }

    @Override
    public void refreshGrilles() {
        chargerGrille();
    }

    public void start_timer() {
        startup = System.nanoTime();
        animationTimer.start();
    }

    public void stop_timer() {
        animationTimer.stop();
    }

    private void drawGrid(Hashi hashi, double paneWidth) {
        int nbColonnes = hashi.getTaille().x;
        int nbLignes = hashi.getTaille().y;

        StackPane parent = (StackPane) gamePane.getParent();
        double cellSize = Math.min(
                (parent.getWidth() - 40) / (nbColonnes + 1),
                (parent.getHeight() - 40) / (nbLignes + 1));

        double gridWidth = cellSize * (nbColonnes + 1);
        double gridHeight = cellSize * (nbLignes + 1);

        gamePane.setPrefSize(gridWidth, gridHeight);
        gamePane.setMaxSize(gridWidth, gridHeight);

        gamePane.getChildren().clear();

        dessinerGrille(nbColonnes, nbLignes, cellSize);
        dessinerPonts(hashi, cellSize);
        dessinerIle(hashi, cellSize);

        if (hashi.estGagne()) {
            win.setVisible(true);
        }
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
        if (onCheck) {
            onCheck = false;
            onCheckClick();
        }

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
        drawGrid(hashi, gamePane.getWidth());
        undoButton.setDisable(true);
        redoButton.setDisable(true);
    }

    @FXML
    protected void onHypothesisClick() {
        onCheck = false;
        Label title = createTitle("Mode Hypothèse");

        hashi.setModeHypothese(true);
        
        if (hintButton != null) hintButton.setDisable(true);
        if (checkButton != null) checkButton.setDisable(true);
        if (hypothesisButton != null) hypothesisButton.setDisable(true);

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

            if (hintButton != null) hintButton.setDisable(false);
            if (checkButton != null) checkButton.setDisable(false);
            if (hypothesisButton != null) hypothesisButton.setDisable(false);
        });
        
        btnCancel.setOnAction(e -> {
            System.out.println("Hypothèse annulée");
            hashi.annulerHypothese();
            General.getDb().annulerHypothese();
            drawGrid(hashi, gamePane.getWidth());
            sidePanel.getChildren().clear();

            if (hintButton != null) hintButton.setDisable(false);
            if (checkButton != null) checkButton.setDisable(false);
            if (hypothesisButton != null) hypothesisButton.setDisable(false);
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

            btnYes.setOnAction(e -> {

                System.out.println("retour à l'état correct");
                hashi.retourEtatCorrect();
                General.getDb().retourEtatCorrect();
                drawGrid(hashi, gamePane.getWidth());
                sidePanel.getChildren().clear();

            });

            btnNo.setOnAction(e -> {
                sidePanel.getChildren().clear();
                onCheck = false;
            });
            HBox actionBox = new HBox(10, btnYes, btnNo);
            updateSidePanel(title, status, new Separator(), question, actionBox);
        } else {
            sidePanel.getChildren().clear();
            onCheck = false;
        }

    }

    @FXML
    protected void onHintClick() {
        onCheck = false;
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
        // limiter la largeur du label au width du sidePanel pour forcer le retour à la
        // ligne
        techniqueName.maxWidthProperty().bind(sidePanel.widthProperty().subtract(30));

        Text explication = new Text(indice.getExplication());
        // lier le wrapping de l'explication à la largeur du sidePanel pour éviter
        // d'agrandir le panneau
        explication.wrappingWidthProperty().bind(sidePanel.widthProperty().subtract(30));

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
}