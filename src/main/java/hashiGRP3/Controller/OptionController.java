//Attribut au packet
package hashiGRP3.Controller;



/* Libs */
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import hashiGRP3.SceneManager;



/**
 * Classe de controlleur du fichier 'option.fxml'
 */
public class OptionController {

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
	private void on_fullscreen_button_pressed(ActionEvent event) {
		CheckBox btn = (CheckBox) event.getSource();
		if(btn.isSelected())
			sceneManager.setFullScreen(true);	
		else
			sceneManager.setFullScreen(false);
	}


        @FXML
        private void quitApp() {
                System.exit(0);
        }
}
