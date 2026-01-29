//Attribut au packet
package hashiGRP3;

/* Libs */
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Screen;
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
        private Stage stage;
        private boolean boolFull;

        SceneManager(Stage stage) {
                allScene = new ArrayList<>();
                this.stage = stage;
                this.boolFull = false;
        }

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
                        if (controller instanceof MenuController menuController) {
                                menuController.setSceneManager(this);
                        } else if (controller instanceof OptionController optionController) {
                                optionController.setSceneManager(this);
                        } else if (controller instanceof ConnexionController connexionController) {
                                connexionController.setSceneManager(this);
                        } else if (controller instanceof TechniqueControler techniqueController) {
                                techniqueController.setSceneManager(this);
                        }

                        final Scene s = new Scene(root, 1600, 900);
                        allScene.add(new Composante(s, name));

                } catch (IOException ex) {
                        System.err.println("Erreur au chargement: " + ex);
                }
        }

        public Scene findScene(String name) {
                for (Composante c : allScene) {
                        if (name.equals(c.getNom())) {
                                return c.getScene();
                        }
                }
                return null;
        }

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

        public void setFullScreen(boolean value) {
                boolFull = value;
        }

        private class Composante {

                private Scene scene;
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
