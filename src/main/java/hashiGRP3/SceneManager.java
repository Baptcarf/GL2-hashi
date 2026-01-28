package hashiGRP3;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class SceneManager {

        private List<Composante> allScene;
        private Stage stage;

        SceneManager(Stage stage) {
                allScene = new ArrayList<>();
                this.stage = stage;
        }

        public void addScene(String name) {
                try {
                        final URL url = getClass().getResource("/hashiGRP3/views/" + name + ".fxml");
                        final FXMLLoader fxmlLoader = new FXMLLoader(url);
                        final Parent root = fxmlLoader.load();
                        final Scene s = new Scene(root, 300, 250);
                        allScene.add(new Composante(s, name));
                } catch (IOException ex) {
                        System.err.println("Erreur au chargement: " + ex);
                }
        }

        public Scene findScene(String name) {
                for (Composante c : allScene) {
                        if (name.equals(c.getNom())) {
                                return c.scene;
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
                stage.setScene(s);
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
