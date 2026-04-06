//Attribut au paquet
package hashiGRP3.Controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.MoteurIndice;
import hashiGRP3.Logic.Aide.Techniques.TechniqueConflitComptage;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolation;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolationDeuxIles;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolationIle;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolationIleVoisine;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolationSegmentIle;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolationTroisIles;
import hashiGRP3.Logic.Aide.Techniques.TechniqueIsolementSegment;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturation;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationCapaciteMax;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsDeux;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsUn;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturationMoinsUnSpe;
import hashiGRP3.Logic.Aide.Techniques.TechniquesBloquePont;
import hashiGRP3.Logic.EtapeTutoriel;
import hashiGRP3.Logic.General;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Pont;
import hashiGRP3.Logic.ScenarioTutoriel;
import hashiGRP3.ObjectGraphique.ileGraphique;
import hashiGRP3.ObjectGraphique.pontGraphique;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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

    // --- Tutoriel guidé ---
    private List<EtapeTutoriel> etapesTutoriel = new ArrayList<>();
    private int etapeCourante = 0;

    double elapsedBefore = 0;
    @FXML
    private VBox sidePanel;
    @FXML
    private Pane gamePane;
    @FXML
    private Label timer;
    @FXML
    private Label labelModeTutoriel;
    @FXML
    private ImageView chronoImage;
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
    private java.util.ArrayList<IndiceResultat> indicesDisponibles = new java.util.ArrayList<>();
    private int indiceAffiche = 0;
    private boolean onAide = false;
    private boolean tuto = false;

    private static final List<KeyCode> KONAMI_CODE = List.of(
            KeyCode.Z, KeyCode.Z, KeyCode.S, KeyCode.S,
            KeyCode.Q, KeyCode.D, KeyCode.Q, KeyCode.D,
            KeyCode.B, KeyCode.A);
    private int konamiIndex = 0;
    private boolean konamiActivated = false;

    /** Constructor-like ?? */
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

    /** Charge la grille avec les niveaux */
    private void chargerGrille(boolean tuto) {
        int grid_num = General.getNum_grille();

        if (labelTitreGrille != null) {
            if (tuto) {
                labelTitreGrille.setText("Grille tutoriel " + (grid_num));
                this.tuto = true;
            }
        } else
            labelTitreGrille.setText("Grille numéro " + grid_num);

        // Si grille non tutoriel alors calculer index
        String resourcePath = "-1";
        if (tuto) {
            if (grid_num == 0) {
                // Règles du jeu : hashi0.txt dans Grille_Tutoriel
                resourcePath = "/hashiGRP3/Grille_Tutoriel/hashi0.txt";
            }

            else {
                int numGrille = General.getNum_grille();
                resourcePath = "/hashiGRP3/Grille_Tutoriel/hashi" + numGrille + ".txt";
            }
        } else {
            int folderIndex = (grid_num - 1) / 5;
            String[] folders = { "7x7", "10x10", "12x12" };
            String folder = folders[folderIndex];
            int fileNumber = ((grid_num - 1) % 5) + 1;
            resourcePath = "/hashiGRP3/" + folder + "/hashi" + fileNumber + ".txt";
        }

        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Fichier " + resourcePath + " non trouvé !");
                return;
            }

            hashi = Import.chargerFichierDepuisStream(is, resourcePath);
            hashi.initialisationToutLesPonts();
            hashi.initialisationToutLesConflits();
            moteurIndice = new MoteurIndice(List.of(
                    new TechniqueSaturation(),
                    new TechniqueIsolation(),
                    new TechniqueSaturationMoinsDeux(),
                    new TechniqueSaturationMoinsUn(),
                    new TechniqueSaturationMoinsUnSpe(),
                    new TechniqueSaturationCapaciteMax(),
                    new TechniqueIsolationDeuxIles(),
                    new TechniqueIsolationTroisIles(),
                    new TechniqueIsolementSegment(),
                    new TechniquesBloquePont(),
                    new TechniqueIsolationIle(),
                    new TechniqueIsolationSegmentIle(),
                    new TechniqueIsolationIleVoisine(),
                    new TechniqueConflitComptage()));

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

            if (!tuto) {
                timer.setVisible(true);
                checkButton.setVisible(true);
                hintButton.setVisible(true);
                hypothesisButton.setVisible(true);
                chronoImage.setVisible(true);
                labelModeTutoriel.setVisible(false);
                labelModeTutoriel.setManaged(false);
                double savedScore = General.getDb().checkScorePartie();
                General.resetTimer();
                General.setElapsedTime(savedScore);
                this.start_timer();
            } else {
                timer.setVisible(false);
                labelModeTutoriel.setVisible(true);
                labelModeTutoriel.setManaged(true);
                chronoImage.setVisible(false);
                if (grid_num == 0) {

                    checkButton.setVisible(true);
                    hintButton.setVisible(true);
                    hypothesisButton.setVisible(true);

                } else {

                    checkButton.setVisible(false);
                    hintButton.setVisible(false);
                    hypothesisButton.setVisible(false);

                }

            }

            drawGrid(hashi, parent.getWidth());

            // Charger le scénario tutoriel si on est en mode tuto
            if (tuto) {
                int numScenario = grid_num;
                etapesTutoriel = ScenarioTutoriel.getEtapes(numScenario);
                etapeCourante = 0;
                afficherEtapeTutoriel();
            }

        } catch (java.io.IOException ex) {
            System.err.println("Erreur au chargement : " + ex.getMessage());
        }
    }

    /** Rafraichie la grille du hashi */
    @Override
    public void refreshGrilles(boolean tuto) {
        sidePanel.getChildren().clear();
        chargerGrille(tuto);
        if (General.getHashi().getHypothese()) {
            onHypothesisClick();
        }

    }

    /** Démarre le timer */
    public void start_timer() {
        General.startTimer();
        animationTimer.start();
    }

    /** Arrêt du timer */
    public double stop_timer() {
        animationTimer.stop();
        return General.stopTimer();
    }

    /** Dessine le hashi */
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
            General.getDb().changeStatutPartie(2);
            if (!tuto)
                win.setVisible(true);
            showWin();
        }
    }

    /** Dessine les indices */
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
        if (!tuto) {
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
                    retourArriere(event);
                }
            });

        }

    }

    /** Dessine le cadrillage */
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

    /** Dessine les ponts */
    private void dessinerPonts(Hashi hashi, double size) {
        for (var pont : hashi.getPonts()) {
            pontGraphique pg = new pontGraphique(pont);
            gamePane.getChildren().add(pg.draw(size, this::onPontClicked));
        }
    }

    /** Méthode activer lors d'un clique sur un pont */
    private void onPontClicked(Pont pont) {
        hashi.jouer(pont, tuto);
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
        if (onCheck) {
            onCheck = false;
        }
        if (onAide) {
            onAide = false;
            sidePanel.getChildren().clear();
        }
        // Vérifier la progression du tutoriel
        if (tuto && !etapesTutoriel.isEmpty()) {
            verifierEtapeTutoriel();
        }
    }

    /** Dessine les iles */
    private void dessinerIle(Hashi hashi, double size) {
        for (Ile island : hashi.getIles()) {
            ileGraphique ig = new ileGraphique(island);
            gamePane.getChildren().add(ig.draw(size));
        }
    }

    /** Méthode activer lors d'un clique sur le bouton UNDO */
    @FXML
    private void onUndoClick() {
        hashi.undo();
        drawGrid(hashi, gamePane.getWidth());
        undoButton.setDisable(hashi.isUndoEmpty());
        redoButton.setDisable(hashi.isRedoEmpty());
    }

    /** Méthode activer lors d'un clique sur le bouton REDO */
    @FXML
    private void onRedoClick() {
        hashi.redo();
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
    }

    /** Méthode activer lors d'un clique sur le bouton reset */
    @FXML
    private void onResetClick() {
        hashi.Reset();
        hashi.setModeHypothese(false);
        animationTimer.stop();
        General.resetTimer();
        General.getDb().updateScorePartie(0.0);
        start_timer();

        drawGrid(hashi, gamePane.getWidth());

        if (hintButton != null)
            hintButton.setDisable(false);
        if (checkButton != null)
            checkButton.setDisable(false);
        if (hypothesisButton != null)
            hypothesisButton.setDisable(false);

        undoButton.setDisable(true);
        redoButton.setDisable(true);
        sidePanel.getChildren().clear();
    }

    /** Méthode activer lors d'un clique sur le bouton hypothèse */
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

    /** Méthode appeler lors d'un clique sur le bouton check */
    @FXML
    protected void onCheckClick() {
        if (onCheck == false) {
            onCheck = true;
            General.addElapsedTime(60.0);
            Label title = createTitle("Vérification");
            Label status = new Label("Il y a " + hashi.getNbErreur() + " erreurs sur la grille.");
            status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            Text question = new Text("Voulez-vous revenir au dernier état sans erreur ?");
            question.setWrappingWidth(180);
            Button btnYes = new Button("Oui");
            Button btnNo = new Button("Non");

            btnYes.setOnAction(e -> {
                General.getHashi().retourEtatCorrect();
                drawGrid(hashi, gamePane.getWidth());
                sidePanel.getChildren().clear();

                onCheck = false;
            });

            btnNo.setOnAction(e -> {
                sidePanel.getChildren().clear();
                onCheck = false;
            });

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

    /** Méthode appeler lors d'un clique sur le bouton indice */

    @FXML
    protected void onHintClick() {
        java.util.List<IndiceResultat> nouveauxIndices = moteurIndice.proposerTousLesIndices(hashi);
        indicesDisponibles.clear();
        indicesDisponibles.addAll(nouveauxIndices);
        indiceAffiche = 0;
        afficherIndiceCourant();
    }

    private void afficherIndiceCourant() {
        if (!onAide) {
            onAide = true;
            General.addElapsedTime(10.0);
        }
        Label title = createTitle("Indice");
        if (indicesDisponibles.isEmpty()) {
            Text msg = new Text("Aucun indice disponible pour le moment.");
            updateSidePanel(title, new Separator(), msg);
            return;
        }

        if (indiceAffiche < 0 || indiceAffiche >= indicesDisponibles.size()) {
            indiceAffiche = 0;
        }

        IndiceResultat indice = indicesDisponibles.get(indiceAffiche);
        Label compteur = new Label((indiceAffiche + 1) + " / " + indicesDisponibles.size());
        Label difficulte = new Label("Difficulté : " + indice.getDifficulte());
        Label techniqueName = new Label(indice.getNomTechnique());
        techniqueName.setWrapText(true);
        Text explication = new Text(indice.getExplication());
        explication.setWrappingWidth(180);

        Button prevButton = new Button("<-");
        Button nextButton = new Button("->");

        prevButton.setOnAction(e -> {
            if (!indicesDisponibles.isEmpty()) {
                indiceAffiche = (indiceAffiche - 1 + indicesDisponibles.size()) % indicesDisponibles.size();
                General.addElapsedTime(10.0);
                afficherIndiceCourant();
            }
        });

        nextButton.setOnAction(e -> {
            if (!indicesDisponibles.isEmpty()) {
                indiceAffiche = (indiceAffiche + 1) % indicesDisponibles.size();
                General.addElapsedTime(10.0);
                afficherIndiceCourant();
            }
        });

        HBox navigation = new HBox(10, prevButton, compteur, nextButton);
        navigation.setAlignment(Pos.CENTER);

        updateSidePanel(title, new Separator(), navigation, difficulte, techniqueName, explication);
    }

    /** ??? */
    private void updateSidePanel(javafx.scene.Node... nodes) {
        sidePanel.getChildren().clear();
        sidePanel.getChildren().addAll(nodes);
    }

    /** ??? */
    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        label.setStyle("-fx-text-fill: #333333;");
        return label;
    }

    /** Implementation du Konami Code */
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

    /** Check si le Konami Code est entré */
    public boolean isKonamiCodeEntered() {
        return konamiActivated;
    }

    /**
     * Réactive les bouttons quand l'utilisateur quiet la grille (quand le mode
     * hypothèse est actif)
     */
    @FXML
    @Override
    public void changeScene(ActionEvent event) {
        if (tuto) {
            tuto = false;
            retourArriere(event);
        } else {
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

    /**
     * Affiche l'étape courante du tutoriel dans le panneau latéral.
     */
    private void afficherEtapeTutoriel() {
        if (etapesTutoriel.isEmpty())
            return;
        if (etapeCourante >= etapesTutoriel.size())
            return;

        EtapeTutoriel etape = etapesTutoriel.get(etapeCourante);

        Label title = createTitle(etape.getTitre());
        Separator sep = new Separator();

        Label compteur = new Label("Étape " + (etapeCourante + 1) + " / " + etapesTutoriel.size());

        Text texte = new Text(etape.getTexte());
        texte.setWrappingWidth(180);

        Button btnSuivant = new Button("Suivant →");
        btnSuivant.setMaxWidth(Double.MAX_VALUE);

        // Bloquer le bouton si une condition est définie et pas encore remplie
        boolean conditionRemplie = etape.estValidee(hashi);
        btnSuivant.setDisable(etape.aUneCondition() && !conditionRemplie);

        btnSuivant.setOnAction(e -> {
            etapeCourante++;
            afficherEtapeTutoriel();
        });

        updateSidePanel(title, sep, compteur, texte, btnSuivant);
    }

    /**
     * Vérifie si la condition de l'étape courante est remplie
     * et débloque le bouton Suivant si c'est le cas.
     * Appelée après chaque coup joué.
     */
    private void verifierEtapeTutoriel() {
        if (etapesTutoriel.isEmpty())
            return;
        if (etapeCourante >= etapesTutoriel.size())
            return;

        EtapeTutoriel etape = etapesTutoriel.get(etapeCourante);
        if (etape.aUneCondition() && etape.estValidee(hashi)) {
            // Rafraîchir le panneau pour débloquer le bouton
            afficherEtapeTutoriel();
        }
    }
}
