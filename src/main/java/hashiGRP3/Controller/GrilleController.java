//Attribut au packet
package hashiGRP3.Controller;



//Imports
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Pont;
import hashiGRP3.ObjectGraphique.ileGraphique;
import hashiGRP3.ObjectGraphique.pontGraphique;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



/* Code de class */
public class GrilleController extends ManageController {
    private Hashi hashi;
    AnimationTimer animationTimer;
    double startup;

    //FXML
    @FXML
    private VBox sidePanel; 
    
    @FXML
    private Pane gamePane; 

    @FXML
    private Label timer;

    private static final List<KeyCode> KONAMI_CODE = List.of(
        KeyCode.Z, KeyCode.Z, KeyCode.S, KeyCode.S,
        KeyCode.Q, KeyCode.D, KeyCode.Q, KeyCode.D,
        KeyCode.B, KeyCode.A
    );
    private int konamiIndex = 0;
    private boolean konamiActivated = false;

    @FXML
    public void initialize() {
	    //On charge une grille
        URL url = getClass().getResource("/hashiGRP3/7x7/hashi1.txt");
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

            // Quand on change la taille on redessine
            gamePane.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
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
            newScene.setOnKeyPressed(
                e -> {handleKonamiKey(e.getCode());
                if (isKonamiCodeEntered()) {
                    drawGrid(hashi, gamePane.getWidth());
                }}
            );
        });
    }

    @Override
    protected void changeScene(ActionEvent event) {
        stop_timer();
        super.changeScene(event);
    }

    // Démarre le timer
    public void start_timer() {
        startup = System.nanoTime();
        animationTimer.start();
    }

    // Stop le timer
    public void stop_timer() {
        animationTimer.stop();
    }

    // Dessin de grille
    private void drawGrid(Hashi hashi, double paneWidth) {
        int nbColonnes = hashi.getTaille().x;
        int nbLignes = hashi.getTaille().y;
        
        // Calcul d'une taille de cellule proportionnelle pour couvrir le pane
        double cellSize = paneWidth / (nbColonnes + 1);

        gamePane.getChildren().clear();

        dessinerGrille(nbColonnes, nbLignes, cellSize);
        dessinerPonts(hashi, cellSize);
        dessinerIle(hashi, cellSize);
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

    //Fonctions de ponts
    private void dessinerPonts(Hashi hashi, double size) {
        for (var pont : hashi.getPonts()) {
            pontGraphique pg = new pontGraphique(pont);
            gamePane.getChildren().add(pg.draw(size, this::onPontClicked, isKonamiCodeEntered()));
        }
    }

    private void onPontClicked(Pont pont) {
        hashi.jouer(pont);
        drawGrid(hashi, gamePane.getWidth());
    }

    private void dessinerIle(Hashi hashi, double size) {
        for (Ile island : hashi.getIles()) {
            ileGraphique ig = new ileGraphique(island);
            gamePane.getChildren().add(ig.draw(size));
        }
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

        Text hintText = new Text("lorem ipsum dolor sit amet, consectetur adipiscing elit...");
        hintText.setWrappingWidth(180);

        updateSidePanel(title, new Separator(), hintText);
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
