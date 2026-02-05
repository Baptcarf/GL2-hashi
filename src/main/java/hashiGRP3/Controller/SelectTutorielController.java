package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SelectTutorielController extends ManageController {

    /* ==================== ÉTATS LVL ==================== */

    private enum LevelState {
        LOCKED,        // Niveau bloqué
        UNLOCKED,      // Niveau accessible mais pas encore fait
        COMPLETED      // Niveau terminé
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
        initLevels();
        setupProgression();
    }

    //Charger les images pour les différents états des niveaux
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

    // Récupérer les HBox représentant les niveaux à partir du ScrollPane
    private void collectLevels() {
        VBox content = (VBox) scrollPane.getContent();

        content.lookupAll(".level-line").forEach(node -> {
            if (node instanceof HBox hbox) {
                levels.add(hbox);
            }
        });
    }

    // Initialiser les états des niveaux (seul le premier est débloqué au départ)
    private void initLevels() {
        states.clear();

        states.add(LevelState.UNLOCKED); // Premier niveau débloqué
        applyState(levels.get(0), LevelState.UNLOCKED);
        for (int i = 1; i < levels.size(); i++) {
            states.add(LevelState.LOCKED);
            applyState(levels.get(i), states.get(i));
        }
    }

    // Configurer les actions des boutons pour gérer la progression
    private void setupProgression() {
        for (int i = 0; i < levels.size(); i++) {
            final int index = i;
            getButton(levels.get(i)).setOnAction(e -> onLevelClicked(index));
        }
    }


    // Gérer le clic sur un niveau : si débloqué, le marquer comme complété et débloquer le suivant
    private void onLevelClicked(int index) {
        if (states.get(index) == LevelState.LOCKED) return;

        completeLevel(index);
    }

    // Marquer un niveau comme complété et débloquer le suivant si nécessaire
    private void completeLevel(int index) {
        if (states.get(index) == LevelState.COMPLETED) return;

        states.set(index, LevelState.COMPLETED);
        applyState(levels.get(index), LevelState.COMPLETED);

        if (index + 1 < states.size()
                && states.get(index + 1) == LevelState.LOCKED) {

            states.set(index + 1, LevelState.UNLOCKED);
            applyState(levels.get(index + 1), LevelState.UNLOCKED);
        }
    }


    // Appliquer le style et l'image appropriés à un niveau en fonction de son état
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


    private Button getButton(HBox level) {
        return (Button) level.getChildren().get(0);
    }

    private ImageView getImage(HBox level) {
        return (ImageView) level.getChildren().get(1);
    }
}
