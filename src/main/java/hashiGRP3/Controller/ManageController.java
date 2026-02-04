//Attribut au packet
package hashiGRP3.Controller;


//Imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import hashiGRP3.SceneManager;



/**
 * Classe parent des controleurs. 
 */
public class ManageController {

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

        public SceneManager getSceneManager() {
                return sceneManager;
        }

        @FXML
        private void quitApp() {
                System.exit(0);
        }

}
