//Attribut au paquet
package hashiGRP3.Controller;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import hashiGRP3.Logic.EtapeTutoriel;
import hashiGRP3.Logic.ScenarioTutoriel;
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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

/** Contrôleur de la grille tutoriel */
public class TutoController extends ManageController {

    private Hashi hashi;
    private ChangeListener<Bounds> boundsListener;

    // --- Tutoriel guidé ---
    private List<EtapeTutoriel> etapesTutoriel = new ArrayList<>();
    private int etapeCourante = 0;

    @FXML private VBox sidePanel;
    @FXML private Pane gamePane;
    @FXML private Label timer;
    @FXML private Button undoButton;
    @FXML private Button redoButton;
    @FXML private Button checkButton;
    @FXML private Button hypothesisButton;

    private boolean onCheck = false;

    private static final List<KeyCode> KONAMI_CODE = List.of(
            KeyCode.Z, KeyCode.Z, KeyCode.S, KeyCode.S,
            KeyCode.Q, KeyCode.D, KeyCode.Q, KeyCode.D,
            KeyCode.B, KeyCode.A);
    private int konamiIndex = 0;
    private boolean konamiActivated = false;

    @FXML
    public void initialize() {
        Tooltip t1 = new Tooltip("Annuler le dernier coup");
        t1.setShowDelay(Duration.millis(300));
        undoButton.setTooltip(t1);

        Tooltip t2 = new Tooltip("Rétablir le coup annulé");
        t2.setShowDelay(Duration.millis(300));
        redoButton.setTooltip(t2);

        Tooltip t3 = new Tooltip("Vérifier la grille et revenir à l'état sans erreur");
        t3.setShowDelay(Duration.millis(300));
        checkButton.setTooltip(t3);

        Tooltip t4 = new Tooltip("Activer le mode hypothèse (coups temporaires)");
        t4.setShowDelay(Duration.millis(300));
        hypothesisButton.setTooltip(t4);

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
        sidePanel.getChildren().clear();
        onCheck = false;

        int numGrille = General.getNum_grille();
        String resourcePath = "/hashiGRP3/Grille_Tutoriel/hashi" + numGrille + ".txt";

        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Fichier " + resourcePath + " non trouvé !");
                return;
            }

            hashi = Import.chargerFichierDepuisStream(is, resourcePath);
            hashi.initialisationToutLesPonts();
            hashi.initialisationToutLesConflits();
            hashi.remplirHistorique();

            General.setHashi(hashi);

            undoButton.setDisable(hashi.isUndoEmpty());
            redoButton.setDisable(hashi.isRedoEmpty());

            StackPane parent = (StackPane) gamePane.getParent();

            if (boundsListener != null) {
                parent.layoutBoundsProperty().removeListener(boundsListener);
            }
            boundsListener = (obs, oldBounds, newBounds) -> {
                drawGrid(hashi, newBounds.getWidth());
            };
            parent.layoutBoundsProperty().addListener(boundsListener);

            drawGrid(hashi, parent.getWidth());

            // Charger le scénario tutoriel
            etapesTutoriel = ScenarioTutoriel.getEtapes(numGrille);
            etapeCourante = 0;
            if (!etapesTutoriel.isEmpty()) {
                afficherEtapeTutoriel();
            }

        } catch (java.io.IOException ex) {
            System.err.println("Erreur au chargement : " + ex.getMessage());
        }
    }

    @Override
    public void refreshGrilles() {
        chargerGrille();
        if (hashi != null && hashi.getHypothese()) {
            onHypothesisClick();
        }
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
            Label title = createTitle("Félicitations !");
            Text msg = new Text("Vous avez terminé ce tutoriel !");
            msg.setWrappingWidth(180);
            updateSidePanel(title, new Separator(), msg);
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
        hashi.jouer(pont, true);
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
        if (onCheck) {
            onCheck = false;
            sidePanel.getChildren().clear();
        }
        // Vérifier la progression du tutoriel
        if (!etapesTutoriel.isEmpty()) {
            verifierEtapeTutoriel();
        }
    }

    private void dessinerIle(Hashi hashi, double size) {
        for (Ile island : hashi.getIles()) {
            ileGraphique ig = new ileGraphique(island);
            gamePane.getChildren().add(ig.draw(size));
        }
    }

    // --- Tutoriel guidé ---

    /** Affiche l'étape courante dans le panneau latéral */
    private void afficherEtapeTutoriel() {
        if (etapesTutoriel.isEmpty() || etapeCourante >= etapesTutoriel.size()) return;

        EtapeTutoriel etape = etapesTutoriel.get(etapeCourante);

        Label title = createTitle(etape.getTitre());
        Separator sep = new Separator();
        Label compteur = new Label("Étape " + (etapeCourante + 1) + " / " + etapesTutoriel.size());

        Text texte = new Text(etape.getTexte());
        texte.setWrappingWidth(180);

        Button btnSuivant = new Button("Suivant →");
        btnSuivant.setMaxWidth(Double.MAX_VALUE);
        btnSuivant.setDisable(etape.aUneCondition() && !etape.estValidee(hashi));

        btnSuivant.setOnAction(e -> {
            etapeCourante++;
            afficherEtapeTutoriel();
        });

        updateSidePanel(title, sep, compteur, texte, btnSuivant);
    }

    /** Vérifie si la condition de l'étape courante est remplie et débloque le bouton Suivant */
    private void verifierEtapeTutoriel() {
        if (etapesTutoriel.isEmpty() || etapeCourante >= etapesTutoriel.size()) return;

        EtapeTutoriel etape = etapesTutoriel.get(etapeCourante);
        if (etape.aUneCondition() && etape.estValidee(hashi)) {
            afficherEtapeTutoriel();
        }
    }

    // --- Boutons ---

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
        // Réafficher l'étape courante
        if (!etapesTutoriel.isEmpty()) {
            afficherEtapeTutoriel();
        }
    }

    @FXML
    protected void onHypothesisClick() {
        onCheck = false;
        Label title = createTitle("Mode Hypothèse");
        hashi.setModeHypothese(true);

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
            hashi.validerHypothese();
            General.getDb().validerHypothese();
            drawGrid(hashi, gamePane.getWidth());
            if (checkButton != null) checkButton.setDisable(false);
            if (hypothesisButton != null) hypothesisButton.setDisable(false);
            if (!etapesTutoriel.isEmpty()) afficherEtapeTutoriel();
        });

        btnCancel.setOnAction(e -> {
            hashi.annulerHypothese();
            General.getDb().annulerHypothese();
            drawGrid(hashi, gamePane.getWidth());
            if (checkButton != null) checkButton.setDisable(false);
            if (hypothesisButton != null) hypothesisButton.setDisable(false);
            if (!etapesTutoriel.isEmpty()) afficherEtapeTutoriel();
        });

        HBox buttonBox = new HBox(10, btnValidate, btnCancel);
        updateSidePanel(title, desc, buttonBox);
    }

    @FXML
    protected void onCheckClick() {
        if (!onCheck) {
            onCheck = true;
            Label title = createTitle("Vérification");
            Label status = new Label("Il y a " + hashi.getNbErreur() + " erreurs sur la grille.");
            status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            Text question = new Text("Voulez-vous revenir au dernier état sans erreur ?");
            question.setWrappingWidth(180);
            Button btnYes = new Button("Oui");
            Button btnNo = new Button("Non");
            btnYes.setPrefWidth(80);
            btnNo.setPrefWidth(80);

            btnYes.setOnAction(e -> {
                hashi.retourEtatCorrect();
                General.getDb().retourEtatCorrect();
                drawGrid(hashi, gamePane.getWidth());
                onCheck = false;
                if (!etapesTutoriel.isEmpty()) afficherEtapeTutoriel();
            });

            btnNo.setOnAction(e -> {
                onCheck = false;
                if (!etapesTutoriel.isEmpty()) afficherEtapeTutoriel();
                else sidePanel.getChildren().clear();
            });

            HBox actionBox = new HBox(10, btnYes, btnNo);
            updateSidePanel(title, status, new Separator(), question, actionBox);
        } else {
            onCheck = false;
            if (!etapesTutoriel.isEmpty()) afficherEtapeTutoriel();
            else sidePanel.getChildren().clear();
        }
    }

    // --- Utilitaires ---

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
        if (konamiActivated) return;
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

    @FXML
    @Override
    public void changeScene(ActionEvent event) {
        General.getHashi().setModeHypothese(false);
        if (checkButton != null) checkButton.setDisable(false);
        if (hypothesisButton != null) hypothesisButton.setDisable(false);
        super.changeScene(event);
    }
}