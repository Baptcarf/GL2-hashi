package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import hashiGRP3.SceneManager;

public class MenuController {

        private SceneManager sceneManager;

        public void setSceneManager(SceneManager sm) {
                this.sceneManager = sm;
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
        private void quitApp() {
                System.exit(0);
        }
}
