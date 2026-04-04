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

    /** Gestionnaire de scènes pour naviguer entre les différentes vues. */
    private SceneManager sceneManager;

    /** Pseudo de l'utilisateur actuellement connecté. */
    private static String utilisateur;

    /**
     * Ajouter un scène manager dans les controllers
     * 
     * @param sm : Le scène manager.
     */
    public void setSceneManager(SceneManager sm) {
        this.sceneManager = sm;
    }

    /**
     * Changement de scène depuis un fichier fxml
     * 
     * @param event : Un événement reçu.
     */
    @FXML
    public void changeScene(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String sceneName = (String) btn.getUserData();
        if (sceneManager != null && sceneName != null) {
            sceneManager.changeScene(sceneName);
        }
    }

    /**
     * Changement de scène depuis un fichier fxml, retour en arrière.
     * 
     * @param event : Un événement reçu.
     */
    @FXML
    public void retourArriere(ActionEvent event) {
        if (sceneManager == null) {
            System.out.println("SceneManager non injecté");
            return;
        }
        sceneManager.retourArriere();
    }

    /**
     * Getter du scène manager
     * 
     * @return le scène manager
     */
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    /**
     * Setter du pseudo de l'utilisateur connecté
     * 
     * @param pseudo : le pseudo de l'utilisateur connecté
     */
    public void setUtilisateur(String pseudo) {
        utilisateur = pseudo;
    }

    /**
     * Getter du pseudo de l'utilisateur connecté
     * 
     * @return le pseudo de l'utilisateur connecté
     */
    public String getUtilisateur() {
        return utilisateur;
    }

    /**
     * Handler pour quitter l'application
     */
    @FXML
    private void quitApp() {
        System.exit(0);
    }

    /**
     * Méthode pour rafraîchir les scores (ex: après avoir complété une grille, ou
     * après s'être connecté), abstraite ?.
     */
    public void refreshGrilles() {
    }

    public void refreshGrilles(boolean tuto) {
    }

    /**
     * Méthode pour démarer le chrono, abstraite ?.
     */
    public void startChrono() {
    }
}
