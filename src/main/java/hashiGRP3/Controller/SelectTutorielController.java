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

    /* ==================== ÉTATS ==================== */

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

    private void initLevels() {
        states.clear();

        for (int i = 0; i < levels.size(); i++) {
            if (i == 0) {
                states.add(LevelState.UNLOCKED);
            } else {
                states.add(LevelState.LOCKED);
            }
            applyState(levels.get(i), states.get(i));
        }
    }

    private void setupProgression() {
        for (int i = 0; i < levels.size(); i++) {
            final int index = i;
            getButton(levels.get(i)).setOnAction(e -> onLevelClicked(index));
        }
    }


    private void onLevelClicked(int index) {
        if (states.get(index) == LevelState.LOCKED) return;

     

        completeLevel(index);
    }

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
