//Attribut au packet
package hashiGRP3.Controller;

// Imports
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import hashiGRP3.Logic.General;

/** Contrôleur pour la scène de sélection du tutoriel.
 * Permet à l'utilisateur de voir  l'état des niveaux (bloqué, débloqué, complété),
 * et de les sélectionner pour les jouer.
 * Hérite de ManageController pour bénéficier des fonctionnalités de navigation entre les scènes
 */
public class SelectTutorielController extends ManageController {
    
    /** États possibles pour chaque niveau. 
     * LOCKED : niveau bloqué, non accessible
     * UNLOCKED : niveau débloqué, accessible mais pas encore complété
     * COMPLETED : niveau complété, accessible et marqué comme terminé
    */
    private enum LevelState {
        LOCKED,       
        UNLOCKED,     
        COMPLETED      
    }


    /** Pane défilant contenant les niveaux. */
    @FXML
    private ScrollPane scrollPane;

    /** Liste des niveaux. */
    private final List<HBox> levels = new ArrayList<>();

    /** Liste des états des niveaux. */
    private final List<LevelState> states = new ArrayList<>();

    /** Images pour les différents états des niveaux. 
     * imgLocked : image pour les niveaux bloqués
     * imgLockOpen : image pour les niveaux débloqués
     * imgCompleted : image pour les niveaux complétés
    */
    private Image imgLocked;
    private Image imgLockOpen;
    private Image imgCompleted;

    /** Initialiser le contrôleur. */
    @FXML
    public void initialize() {
        loadImages();
        collectLevels();
        // Ne pas charger la progression ici - ce sera fait dans refreshGrilles()
        // quand on arrivera vraiment sur la scène avec un utilisateur connecté
    }

    /** Charger les images pour les différents états des niveaux. */
    private void loadImages() {
        imgLocked = new Image(
            SelectTutorielController.class
                .getResource("/hashiGRP3/images/lock.png")
                .toExternalForm()
        );

        imgLockOpen = new Image(
            SelectTutorielController.class
                .getResource("/hashiGRP3/images/lockOpen.png")
                .toExternalForm()
        );

        imgCompleted = new Image(
            SelectTutorielController.class
                .getResource("/hashiGRP3/images/valideVert.png")
                .toExternalForm()
        );
    }

    /** Récupérer les HBox représentant les niveaux à partir du ScrollPane. */
    private void collectLevels() {
        VBox content = (VBox) scrollPane.getContent();

        content.lookupAll(".level-line").forEach(node -> {
            if (node instanceof HBox hbox) {
                levels.add(hbox);
            }
        });
    }

    /** Initialiser les états des niveaux. */
    private void initLevels() {
        states.clear();

        states.add(LevelState.UNLOCKED); // Premier niveau débloqué
        applyState(levels.get(0), LevelState.UNLOCKED);
        for (int i = 1; i < levels.size(); i++) {
            states.add(LevelState.LOCKED);
            applyState(levels.get(i), states.get(i));
        }
    }

    /**
     * Charge la progression depuis la base de données et met à jour l'état des niveaux.
     */
    private void loadProgressionFromDatabase() {
        // Vérifier que l'utilisateur est défini
        String pseudo = getUtilisateur();
        if (pseudo == null || pseudo.isEmpty()) {
            return; // Utilisateur non défini, on initialise avec la valeur par défaut
        }

        int avancement = General.getDb().obtenirAvancementTutoriel(pseudo);

        // Marquer tous les tutoriels jusqu'à l'avancement comme complétés
        for (int i = 0; i < avancement && i < states.size(); i++) {
            states.set(i, LevelState.COMPLETED);
            applyState(levels.get(i), LevelState.COMPLETED);
        }

        // Débloquer le niveau suivant s'il existe
        if (avancement < states.size()) {
            states.set(avancement, LevelState.UNLOCKED);
            applyState(levels.get(avancement), LevelState.UNLOCKED);
        }
    }

    /** Configurer les actions des deux premier pour débloquer. */
    private void setupProgression() {
        for (int i = 0; i < 2; i++) {
            final int index = i;
            getButton(levels.get(i)).setOnAction(e -> onLevelClicked(index));
        }
    }


    /**
     * Gérer le clic sur un niveau : si débloqué, le marquer comme complété et débloquer le suivant
     * @param index : l'index du niveau cliqué dans la liste des niveaux
     */
    private void onLevelClicked(int index) {
        if (states.get(index) == LevelState.LOCKED) return;
        completeLevel(index);
    }

    /** Méthode lorsqu'on appuie sur le bouton de la technique de démarrage pour lancer la grille tutoriel */
    @FXML
    public void lancerT1(ActionEvent event) {
        int t = 16;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT2(ActionEvent event) {
        int t = 17;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT3(ActionEvent event) {
        int t = 18;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT4(ActionEvent event) {
        int t = 19;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT5(ActionEvent event) {
        int t = 20;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT6(ActionEvent event) {
        int t = 21;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    @FXML
    public void lancerT7(ActionEvent event) {
        int t = 22;
        General.setNum_grille(t);
        completeLevel(t - 13);
        this.changeScene(event);
    }

    /**
     * Marquer un niveau comme complété et débloquer le suivant si nécessaire
     * @param index : l'index du niveau à marquer comme complété
     */
    private void completeLevel(int index) {
        if (states.get(index) == LevelState.COMPLETED) return;

        states.set(index, LevelState.COMPLETED);
        applyState(levels.get(index), LevelState.COMPLETED);

        // Enregistrer l'avancement dans la BD
        General.getDb().incrementerAvancementTutoriel(getUtilisateur(), index + 1);

        if (index + 1 < states.size()
                && states.get(index + 1) == LevelState.LOCKED) {

            states.set(index + 1, LevelState.UNLOCKED);
            applyState(levels.get(index + 1), LevelState.UNLOCKED);
        }
    }

    /**
     * Appliquer le style et l'image appropriés à un niveau en fonction de son état
     * @param level : la HBox représentant le niveau à mettre à jour
     * @param state : l'état à appliquer au niveau (LOCKED, UNLOCKED, COMPLETED)
     */
    private void applyState(HBox level, LevelState state) {
        Button btn = getButton(level);
        ImageView img = getImage(level);

        switch (state) {
            case LOCKED -> {
                btn.setDisable(true);
                btn.setStyle("-fx-text-fill: gray;");
                img.setImage(imgLocked);
            }
            case UNLOCKED -> {
                btn.setDisable(false);
                btn.setStyle("-fx-text-fill: black;");
                img.setImage(imgLockOpen);
            }
            case COMPLETED -> {
                btn.setDisable(false); // toujours cliquable
                btn.setStyle("-fx-text-fill: black;");
                img.setImage(imgCompleted);
            }
        }
    }

    /**
     * getter du bouton d'une HBox de niveau
     * @param level : la HBox représentant le niveau dont on veut récupérer le bouton
     * @return le Button contenu dans la HBox du niveau
     */
    private Button getButton(HBox level) {
        return (Button) level.getChildren().get(0);
    }

    /**
     * getter de l'image d'une HBox de niveau
     * @param level : la HBox représentant le niveau dont on veut récupérer l'image
     * @return l'ImageView contenu dans la HBox du niveau
     */
    private ImageView getImage(HBox level) {
        return (ImageView) level.getChildren().get(1);
    }

    /**
     * Rafraîchit la progression au moment de l'arrivée sur la scène.
     */
    @Override
    public void refreshGrilles() {
        initLevels();
        loadProgressionFromDatabase();
        setupProgression();
    }
}
