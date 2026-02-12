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
import java.util.Stack;
import java.util.ArrayList;

import hashiGRP3.Controller.*;

/**
 * Classe de gestion des fenêtres.
 */
public class SceneManager {

        private List<Composante> allScene;
        private DatabaseManager db;
        private Stage stage;
        private Stack<Composante> history = new Stack<>();
        private Composante currentScene;
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
                currentScene = null;
        }

        /**
         * Ajoiut d'une scène dans le scène controller
         * 
         * @param name : le nom du fichier source de la scène (.fxml)
         */
        public void addScene(String name) {
                try {
                        // On récupère le fichier FXML correspondant au nom donnée.
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
        public Composante findComposant(String name) {
                for (Composante c : allScene) {
                        if (name.equals(c.getNom())) {
                                return c;
                        }
                }
                return null;
        }

        public Scene findScene(String name) {
                return findComposant(name).getScene();

        }

        /**
         * Changer de scènne
         * 
         * @param name la nouvelle scène principal
         */
        public void changeScene(String name) {
                Composante c = findComposant(name);

                if (currentScene != null) {
                        history.push(currentScene);
                }
                currentScene = c;
                Scene s = c.getScene();
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

        public void retourArriere() {
                if (history.isEmpty()) {
                        System.out.println("Aucun retour possible.");
                        return;
                }

                currentScene = history.pop();
                changeScene(currentScene.nom);
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
