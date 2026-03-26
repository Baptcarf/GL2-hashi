//Attribut au packet
package hashiGRP3;

//Imports
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import hashiGRP3.Controller.*;
import hashiGRP3.Logic.General;

/**
 * Classe de gestion des fenêtres.
 */
public class SceneManager {

    private List<Composante> allScene = new ArrayList<>();
    private Stage stage;
    private Stack<Composante> history = new Stack<>();
    private Composante currentScene = null;
    private boolean boolFull = false;

    //Un seul conteneur racine et une seule scène pour éviter le bug fullscreen sur macOS
    private final StackPane rootContainer = new StackPane();
    private final Scene mainScene = new Scene(rootContainer, 1600, 900);

    /**
     * Création du sceneManager
     * 
     * @param stage : Le stage (la scène principale).
     */
    SceneManager(Stage stage) {
        this.stage = stage;

        //On set la scène unique une seule fois ici, et on ajoute le CSS global
        mainScene.getStylesheets().add(getClass().getResource("/hashiGRP3/style/style.css").toExternalForm());
        stage.setScene(mainScene);
    }

    /**
     * Ajout d'une scène dans le gestionnaire de scène.
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

            // On stocke le Parent (contenu FXML) directement, plus besoin de créer une scene
            allScene.add(new Composante(root, name, (ManageController) controller));

        } catch (IOException ex) {
            System.err.println("Erreur au chargement: " + ex);
        }
    }

    /**
     * Trouver un composant par son nom.
     * 
     * @param name Le nom du composant à trouver
     * @return Le composant correspondant si trouver, sinon null.
     */
    public Composante findComposant(String name) {
        for (Composante c : allScene) {
            if (name.equals(c.getNom())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Retourne la scène principale unique.
     * Remplace findScene() — tous les nœuds partagent désormais la même scène.
     * 
     * @return mainScene
     */
    public Scene getMainScene() {
        return mainScene;
    }

    /**
     * Change la scène avec celle donnée.
     * 
     * @param name La nouvelle scène principale.
     */
    public void changeScene(String name) {
        Composante c;
        if (name.equals("techniqueWithChrono")) {
            c = findComposant("technique");
        } else {
            c = findComposant(name);
        }

        if (c == null) {
            System.out.println("Scène introuvable : " + name);
            return;
        }

        if (currentScene != null) {
            history.push(currentScene);
        }
        currentScene = c;

        if (name.equals("selectGrille")) {
            Composante comp = findComposant("selectGrille");
            comp.getController().refreshGrilles();
        }

        if (name.equals("selectTutoriel")) {
            Composante comp = findComposant("selectTutoriel");
            comp.getController().refreshGrilles();
        }

        if (name.equals("techniqueWithChrono")) {
            Composante comp = findComposant("technique");
            comp.getController().startChrono();
        }

        if (name.equals("grilledujeu")) {
            Composante comp = findComposant("grilledujeu");
            comp.getController().refreshGrilles();
        }

        // On swap uniquement le contenu dans le StackPane racine
        // Le fullscreen n'est jamais interrompu
        rootContainer.getChildren().setAll(c.getRoot());

        stage.show();
    }

    /**
     * Setter du pleine écran.
     * 
     * @param value : la nouvelle valeur
     */
    public void setFullScreen(boolean value) {
        boolFull = value;
        stage.setFullScreen(value);
    }

    /**
     * Permet de retourner à la scène d'avant.
     */
    public void retourArriere() {
        if (history.isEmpty()) {
            System.out.println("Aucun retour possible.");
            return;
        }

        // On pop la scène précédente sans la re-push dans l'historique
        Composante previous = history.pop();
        currentScene = null;
        changeScene(previous.getNom());
    }

    /**
     * Classe représentant une scène.
     */
    private class Composante {

        // Le contenu FXML de la vue
        private Parent root;

        // Le nom donnée à la scène
        private String nom;

        // le controller de la scène
        private ManageController controller;

        /**
         * Création du composant scène
         * 
         * @param root
         * @param nom
         * @param controller
         */
        Composante(Parent root, String nom, ManageController controller) {
            this.root = root;
            this.nom = nom;
            this.controller = controller;
        }

        /**
         * Getter du contenu FXML
         * @return le Parent racine du FXML
         */
        public Parent getRoot() {
            return root;
        }

        /**
         * Getter du nom de la scène
         * @return le nom de la scène
         */
        public String getNom() {
            return nom;
        }

        public ManageController getController() {
            return controller;
        }
    }
}
