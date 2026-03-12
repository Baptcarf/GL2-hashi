//Attribut au packet
package hashiGRP3.Controller;



//Imports
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.MoteurIndice;
import hashiGRP3.Logic.Aide.Techniques.TechniqueSaturation;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Pont;
import hashiGRP3.ObjectGraphique.ileGraphique;
import hashiGRP3.ObjectGraphique.pontGraphique;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
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

	//Var
	private Hashi hashi;
	private MoteurIndice moteurIndice;

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

    private static final List<KeyCode> KONAMI_CODE = List.of(
        KeyCode.Z, KeyCode.Z, KeyCode.S, KeyCode.S,
        KeyCode.Q, KeyCode.D, KeyCode.Q, KeyCode.D,
        KeyCode.B, KeyCode.A
    );
    private int konamiIndex = 0;
    private boolean konamiActivated = false;

    @FXML
    private Button redoButton;

    /** Initialisation de la grille */
    @FXML
    public void initialize() {
	    //On charge une grille
        URL url = getClass().getResource("/hashiGRP3/10x10/hashi3.txt");
        if (url == null) {
            System.err.println("Fichier hashi2.txt non trouvé dans les ressources !");
            return;
        }
        
	    //On l'importe
        Path chemin;
        try {
            chemin = Path.of(url.toURI());
            hashi = Import.chargerFichier(chemin);
            hashi.initialisationToutLesConflits();
            moteurIndice = new MoteurIndice(List.of(new TechniqueSaturation()));
            undoButton.setDisable(true);
            redoButton.setDisable(true);
            // Quand on change la taille on redessine
            StackPane parent = (StackPane) gamePane.getParent();
            parent.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
                drawGrid(hashi, newBounds.getWidth());
            });

        } catch (URISyntaxException | java.io.IOException ex) {
            System.err.println("Erreur au chargement de la grille : " + ex.getMessage());
        }

        //On init un timer
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                double t = (currentTime - startup) / 1000000000;
                timer.setText("Chrono : " + (int)t);
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

    /*@Override
    protected void changeScene(ActionEvent event) {
        stop_timer();
        super.changeScene(event);
    }*/

    /** Démarre le timer */
    public void start_timer() {
        startup = System.nanoTime();
        animationTimer.start();
    }

    /** Stop le timer */
    public void stop_timer() {
        animationTimer.stop();
    }

    /**
     * Dessine la grille.
     * @param hashi La grille à dessiner.
     * @param paneWidth La taille du pane où on dessine la grille.
     */
    private void drawGrid(Hashi hashi, double paneWidth) {
        int nbColonnes = hashi.getTaille().x;
        int nbLignes = hashi.getTaille().y;

        StackPane parent = (StackPane) gamePane.getParent();
        double cellSize = Math.min(
            (parent.getWidth()  - 40) / (nbColonnes + 1),
            (parent.getHeight() - 40) / (nbLignes + 1)
        );

        double gridWidth  = cellSize * (nbColonnes + 1);
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

    /** Dessine le grillage d'arrière plan */
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

    /** Trigger function */
    private void onPontClicked(Pont pont) {
        hashi.jouer(pont);
        drawGrid(hashi, gamePane.getWidth());
        redoButton.setDisable(hashi.isRedoEmpty());
        undoButton.setDisable(hashi.isUndoEmpty());
    }

    /** Dessine les ilots */
    private void dessinerIle(Hashi hashi, double size) {
        for (Ile island : hashi.getIles()) {
            ileGraphique ig = new ileGraphique(island);
            gamePane.getChildren().add(ig.draw(size));
        }
    }
    @FXML
    private void onUndoClick(){
        hashi.undo();
        drawGrid(hashi, gamePane.getWidth());
        undoButton.setDisable(hashi.isUndoEmpty());
        redoButton.setDisable(hashi.isRedoEmpty());
    }

    @FXML
    private void onRedoClick(){
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
    /* Panel Hypothese */
    @FXML
    protected void onHypothesisClick() {
        Label title = createTitle("Mode Hypothèse");
        
        Text desc = new Text("lorem ipsum dolor sit amet, consectetur adipiscing");
        desc.setWrappingWidth(180);

        Button btnValidate = new Button("Valider");
        Button btnCancel = new Button("Annuler");

        btnValidate.setStyle(" -fx-text-fill: #005500;");
        btnCancel.setStyle(" -fx-text-fill: #8b0000;");
        
        btnValidate.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnValidate, Priority.ALWAYS);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, btnValidate, btnCancel);

        //Update Panel
        updateSidePanel(title, desc, buttonBox);
    }

    @FXML
    protected void onCheckClick() {

        Label title = createTitle("Vérification");
        
        Label status = new Label("Il y a X erreurs sur la grille.");
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
    }

    @FXML
    protected void onHintClick() {
        Label title = createTitle("Indice");

        Optional<IndiceResultat> resultat = moteurIndice.proposerProchainIndice(hashi);

        System.out.println(resultat);

        if (resultat.isEmpty()) {
            Text msg = new Text("Aucun indice disponible pour le moment.");
            updateSidePanel(title, new Separator(), msg);
            return;
        }

        IndiceResultat indice = resultat.get();

        Label techniqueName = new Label(indice.getNomTechnique());
        techniqueName.setWrapText(true);
        Text explication = new Text(indice.getExplication());

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
}
