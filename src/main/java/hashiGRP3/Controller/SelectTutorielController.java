//Attribut au paquet
package hashiGRP3.Controller;

import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import hashiGRP3.Logic.General;

/**
 * Contrôleur pour la scène de sélection du tutoriel.
 */
public class SelectTutorielController extends ManageController {

    private enum LevelState {
        LOCKED,
        UNLOCKED,
        COMPLETED
    }

    @FXML
    private ScrollPane scrollPane;

    private final List<HBox> levels = new ArrayList<>();
    private final List<LevelState> states = new ArrayList<>();

    private Image imgLocked;
    private Image imgLockOpen;
    private Image imgCompleted;

    @FXML
    public void initialize() {
        loadImages();
        collectLevels();
    }

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

    private void collectLevels() {
        VBox content = (VBox) scrollPane.getContent();
        content.lookupAll(".level-line").forEach(node -> {
            if (node instanceof HBox hbox) {
                levels.add(hbox);
            }
        });
    }

    /** Initialise tous les niveaux à LOCKED sauf le premier (COMPLETED) */
    private void initLevels() {
        states.clear();
        for (int i = 0; i < levels.size(); i++) {
            states.add(LevelState.LOCKED);
            applyState(levels.get(i), LevelState.LOCKED);
        }
        // Les deux premiers (Règles du jeu + Jouer) toujours accessibles
        states.set(0, LevelState.COMPLETED);
        applyState(levels.get(0), LevelState.COMPLETED);
        states.set(1, LevelState.COMPLETED);
        applyState(levels.get(1), LevelState.COMPLETED);
    }

    /** Charge la progression depuis la base de données */
    private void loadProgressionFromDatabase() {
        // S'assurer que l'avancement minimum est 2 (les deux premiers toujours débloqués)
        while (General.getDb().obtenirAvancementTutoriel() < 2) {
            General.getDb().incrementerAvancementTutoriel(General.getDb().obtenirAvancementTutoriel() + 1);
        }

        int avancement = General.getDb().obtenirAvancementTutoriel();

        // Marquer tous les niveaux jusqu'à l'avancement comme complétés
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

    /** Lancer les règles du jeu (hashi0.txt) */
    @FXML
    public void lancerRegles(ActionEvent event) {
        refreshGrilles(); // s'assure que states est initialisé
        General.setNum_grille(0);
        getSceneManager().changeScene("tutodujeu");
    }

    /** Lancer un tutoriel selon son index (userData du bouton) */
    @FXML
    public void chooseLevelTuto(ActionEvent event) {
        refreshGrilles(); // s'assure que states est initialisé
        Button btn = (Button) event.getSource();
        int index = Integer.parseInt(btn.getUserData().toString());

        if (index < states.size() && states.get(index) == LevelState.LOCKED) return;

        General.setNum_grille(index); // hashi1.txt, hashi2.txt, etc.
        getSceneManager().changeScene("tutodujeu");
    }

    /** Marquer un niveau comme complété et débloquer le suivant */
    private void completeLevel(int index) {
        if (index < 0 || index >= states.size()) return;
        if (states.get(index) == LevelState.COMPLETED) return;

        states.set(index, LevelState.COMPLETED);
        applyState(levels.get(index), LevelState.COMPLETED);

        General.getDb().incrementerAvancementTutoriel(index + 1);

        if (index + 1 < states.size() && states.get(index + 1) == LevelState.LOCKED) {
            states.set(index + 1, LevelState.UNLOCKED);
            applyState(levels.get(index + 1), LevelState.UNLOCKED);
        }
    }

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
                btn.setDisable(false);
                btn.setStyle("-fx-text-fill: black;");
                img.setImage(imgCompleted);
            }
        }
    }

    private Button getButton(HBox level) {
        return (Button) level.getChildren().get(0);
    }

    private ImageView getImage(HBox level) {
        return (ImageView) level.getChildren().get(1);
    }

    @Override
    public void refreshGrilles() {
        initLevels();
        loadProgressionFromDatabase();
    }
}