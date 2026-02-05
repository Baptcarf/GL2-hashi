//Attribut au packet
package hashiGRP3;

/* Libs */
import java.net.URL;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

import hashiGRP3.Controller.*;

/**
 * Classe de gestion des fenêtres.
 */
public class SceneManager {

        private List<Composante> allScene;
        private DatabaseManager db;
        private Stage stage;
        private boolean boolFull;

        /**
         * Création du sceneManager
         * 
         * @param stage : le stage
         * @param db    : la base de donnée
         */
        SceneManager(Stage stage, DatabaseManager db) {
                allScene = new ArrayList<>();
                this.stage = stage;
                this.boolFull = false;
                this.db = db;
        }

        /**
         * Ajoiut d'une scène dans le scène controller
         * 
         * @param name : le nom du fichier source de la scène (.fxml)
         */
        public void addScene(String name) {
                try {
                        // On récupère le fichier FXML
                        final URL url = getClass().getResource("/hashiGRP3/views/" + name + ".fxml");
                        if (url == null) {
                                System.out.println("Fichier FXML non trouvé : " + name);
                                return;
                        }

                        // On le charge
                        final FXMLLoader fxmlLoader = new FXMLLoader(url);
                        final Parent root = fxmlLoader.load();

                        // On attribut le controller correspondant
                        Object controller = fxmlLoader.getController();
                        if (controller instanceof ManageController manageController) {
                                manageController.setSceneManager(this);
                        }

                        final Scene s = new Scene(root, 1600, 900);
                        allScene.add(new Composante(s, name));

                } catch (IOException ex) {
                        System.err.println("Erreur au chargement: " + ex);
                }
        }

        /**
         * trouver une scène par son nom
         * 
         * @param name le nom de la scène à trouver
         * @return la scène correspondante si trouver sinon null
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
         * Changer de scènne
         * 
         * @param name la nouvelle scène principal
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
         * Seteur du pleine écran
         * 
         * @param value : la nouvelle valeur
         */
        public void setFullScreen(boolean value) {
                boolFull = value;
        }

        /**
         * geter de la base de donnée
         * 
         * @return la base de donnée
         */
        public DatabaseManager getBD() {
                return db;
        }

        /**
         * classe représentant une scène
         */
        private class Composante {

                // la scène en elle même
                private Scene scene;

                // le nom donnée à la scène
                private String nom;

                /**
                 * Créeation du composant scène
                 * 
                 * @param scene
                 * @param nom
                 */
                Composante(Scene scene, String nom) {
                        this.scene = scene;
                        this.nom = nom;
                }

                /**
                 * getter de la scène
                 * 
                 * @return la scène
                 */
                public Scene getScene() {
                        return scene;
                }

                /**
                 * getter du nom de la scène
                 * 
                 * @return le nom de la scène
                 */
                public String getNom() {
                        return nom;
                }

        }

}
