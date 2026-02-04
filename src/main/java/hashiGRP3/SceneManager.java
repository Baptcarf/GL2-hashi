//Attribut au packet
package hashiGRP3;



//Imports
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import hashiGRP3.Controller.*;



/**
 * Classe de gestion des fenêtres.
 */
public class SceneManager {

        private List<Composante> allScene;
        private Stage stage;
        private boolean boolFull;

        SceneManager(Stage stage) {
                allScene = new ArrayList<>();
                this.stage = stage;
                this.boolFull = false;
        }

	/**
	 * Ajoute une scène dans le SceneManager.
	 */
        public void addScene(String name) {
                try {
                        //On récupère le fichier FXML correspondant au nom donnée.
                        final URL url = getClass().getResource("/hashiGRP3/views/" + name + ".fxml");
                        if (url == null) {
                                System.out.println("Fichier FXML non trouvé : " + name);
                                return;
                        }

                        //On le charge
                        final FXMLLoader fxmlLoader = new FXMLLoader(url);
                        final Parent root = fxmlLoader.load();

                        //On attribut le controller correspondant
                        Object controller = fxmlLoader.getController();
                        if (controller instanceof ManageController manageController) {
                                manageController.setSceneManager(this);
                        } else if (controller instanceof OptionController optionController) {
                                optionController.setSceneManager(this);
                        } else if (controller instanceof ConnexionController connexionController) {
                                connexionController.setSceneManager(this);
                        } else if (controller instanceof TechniqueControler techniqueController) {
                                techniqueController.setSceneManager(this);
                        }

			//On creer une scène avec
                        final Scene s = new Scene(root, 1600, 900);
                        allScene.add(new Composante(s, name));

                } catch (IOException ex) {
                        System.err.println("Erreur au chargement: " + ex);
                }
        }

        /**
         * Fonction pour trouver une scène
         * 
         * @param name le nom de la scène
         * @return la scène ou null si pas trouvée
         */
        public Scene findScene(String name) {
                for (Composante c : allScene) {
                        if (name.equals(c.getNom())) {
                                return c.getScene();
                        }
                }
                return null;
        }

        /**
         * Fonction pour changer de scène
         * 
         * @param name nom de la scène
         */
        public void changeScene(String name) {
                Scene s = findScene(name);
                if (s == null) {
                        System.out.println("Scène introuvable : " + name);
                        return;
                }
                s.getStylesheets().add(getClass().getResource("/hashiGRP3/style/style.css").toExternalForm());

                stage.setScene(s);

                if (this.boolFull && stage.isFullScreen() == false)
                        stage.setFullScreen(true);

                stage.show();
        }

        /**
         * Seteur du mode pleine écran
         * 
         * @param value
         */
        public void setFullScreen(boolean value) {
                boolFull = value;
        }

        /**
         * Classe représenant une scène dans le SceneManager.
         */
        private class Composante {

		/** La scène en elle-même */
                private Scene scene;

		/** Le nom attribué à la scène */
                private String nom;

                Composante(Scene scene, String nom) {
                        this.scene = scene;
                        this.nom = nom;
                }

                public Scene getScene() {
                        return scene;
                }

                public String getNom() {
                        return nom;
                }

        }

}
