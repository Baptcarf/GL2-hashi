//Attribut au packet
package hashiGRP3.Controller;

/* Libs */
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

/**
 * Classe de controlleur du fichier 'option.fxml'
 */
public class OptionController extends ManageController {

        @FXML
        private void on_fullscreen_button_pressed(ActionEvent event) {
                CheckBox btn = (CheckBox) event.getSource();
                if (btn.isSelected())
                        getSceneManager().setFullScreen(true);
                else
                        getSceneManager().setFullScreen(false);
        }

}
