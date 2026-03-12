//Attribut au packet
package hashiGRP3.Controller;



//Imports
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;



/**
 * Contrôleur pour la scène des options du jeu. Permet de gérer les paramètres de l'application,
 * tels que le mode plein écran.
 * Hérite de ManageController pour bénéficier des fonctionnalités de navigation entre les scènes
 */
public class OptionController extends ManageController {

        /**
         * Gestionnaire d'événement pour le bouton de mode plein écran. Permet à l'utilisateur de basculer
         * entre le mode plein écran et le mode fenêtré.
         * @param event : L'événement déclenché par l'action de l'utilisateur sur le bouton de mode plein écran.
         */
        @FXML
        private void on_fullscreen_button_pressed(ActionEvent event) {
                CheckBox btn = (CheckBox) event.getSource();
                if (btn.isSelected())
                        getSceneManager().setFullScreen(true);
                else
                        getSceneManager().setFullScreen(false);
        }

}
