package hashiGRP3.Controller;

/* Libs */
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import hashiGRP3.SceneManager;

public class ConnexionController {

        private SceneManager sceneManager;
        private int nbCount;
        @FXML
        private HBox hbox;

        @FXML
        private Circle creer;

        @FXML
        private Label labelCreer;

        public void setSceneManager(SceneManager sm) {
                this.sceneManager = sm;
                nbCount = 0;
                Tooltip.install(creer, new Tooltip("Créer un compte"));
        }

        @FXML
        private void changeScene(ActionEvent event) {
                Button btn = (Button) event.getSource();
                String sceneName = (String) btn.getUserData();
                if (sceneManager != null && sceneName != null) {
                        sceneManager.changeScene(sceneName);
                }
        }

        @FXML
        private void addCount() {
                if (nbCount < 5) {
                        Circle leftCircle = new Circle();
                        leftCircle.setRadius(100); // même taille que rightCircle
                        leftCircle.setFill(Color.web("#eaf5ff"));
                        leftCircle.setStroke(Color.BLACK);
                        leftCircle.setStrokeWidth(2);

                        StackPane leftCircleStack = new StackPane(leftCircle);

                        hbox.getChildren().add(0, leftCircleStack);

                        nbCount += 1;
                        if (nbCount == 5) {
                                creer.setFill(Color.web("#DFDFDFDF"));
                        }
                }

        }

        @FXML
        private void afficheMessage() {
                if (nbCount == 5) {
                        labelCreer.setVisible(true);
                }

        }

        @FXML
        private void enleverMessage() {
                labelCreer.setVisible(false);

        }

}
