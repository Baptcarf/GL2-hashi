//Attribut au packet
package hashiGRP3.Controller;

//Imports
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import hashiGRP3.SceneManager;
import hashiGRP3.DatabaseManager;
import hashiGRP3.compDB.Utilisateur;
import hashiGRP3.Scene.CreerUtilisateur;
import hashiGRP3.Logic.General;
import java.util.List;

/**
 * Contrôleur pour la scène de connexion des utilisateurs.
 * Gère l'affichage des comptes existants, la création de nouveaux comptes,
 * et la suppression de comptes utilisateurs.
 */
public class ConnexionController extends ManageController {

	/** Compteur du nombre de comptes utilisateurs affichés. */
    private int nbCount;
    
    /** Indique si le mode suppression est activé. */
    private boolean sup = false;


    /** Conteneur horizontal pour les cercles de sélection des utilisateurs. */
    @FXML
    private HBox hbox;

    /** Cercle pour créer un nouveau compte. */
    @FXML
    private Circle creer;

    /** Label pour afficher le message d'alerte de limite de comptes. */
    @FXML
    private Label labelCreer;

    /** Bouton pour passer en mode suppression de compte. */
    @FXML
    private Button supprimer;


    /**
     * Initialise le gestionnaire de scène et charge tous les comptes utilisateurs.
     * @param sm le gestionnaire de scène à utiliser
     */
    public void setSceneManager(SceneManager sm) {
        super.setSceneManager(sm);
        nbCount = 0;
        Tooltip.install(creer, new Tooltip("Créer un compte"));
        Tooltip.install(supprimer, new Tooltip("supprimer un compte"));

        List<Utilisateur> au = General.getDb().findAllUser();

        for (Utilisateur u : au) {
            Circle c = createCircle(u.getColor());
            createBoxUser(c, u.getPseudo());
            nbCount++;
        }
        if (nbCount == 5) {
            creer.setFill(Color.web("#DFDFDFDF"));
        }

    }

    /**
     * Crée une boîte contenant un cercle de couleur et un label avec le pseudo.
     * @param c le cercle de couleur du compte
     * @param val le pseudo de l'utilisateur
     */
    private void createBoxUser(Circle c, String val) {

        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(8);
        v.setMinHeight(150);
        v.setPrefHeight(150);
        Label l = new Label(val);
        l.setAlignment(Pos.CENTER);
        v.getChildren().addAll(c, l);

        hbox.getChildren().add(0, v);

    }

    /**
     * Crée un cercle de connexion avec les événements souris.
     * Si le mode suppression est activé, clique pour supprimer le compte.
     * Sinon, clique pour se connecter au compte.
     * @param color la couleur du cercle au format hexadécimal
     * @return le cercle créé avec les gestionnaires d'événements
     */
    private Circle createCircle(String color) {
        // Apparence
        Circle circle = new Circle();
        circle.setRadius(70); // même taille que rightCircle
        circle.setFill(Color.web(color));
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        // Logique
        circle.setOnMouseClicked(event -> {
            if (sup) {
                supprimerCompte(circle);
            } else {
                VBox parentVBox = (VBox) circle.getParent();
                Label lab = null;

                // Parcours les enfants du VBox
                for (Node node : parentVBox.getChildren()) {
                    if (node instanceof Label) {
                        lab = (Label) node;
                        break;
                    }
                }

                setUtilisateur(lab.getText());
                General.setIdUtilisateur(General.getDb().getIdUtilisateur(getUtilisateur()));
                getSceneManager().changeScene("accueil");
            }

        });

        // Retour
        return circle;
    }




    /**
     * Cache le label d'avertissement de limite de comptes.
     */
    @FXML
    private void enleverMessage() {
        labelCreer.setVisible(false);
    }

    /**
     * Affiche une boîte de dialogue pour créer un nouvel utilisateur.
     * @param c le cercle associé au nouveau compte
     */
    private void creerUtilisateur(Circle c) {
        CreerUtilisateur dialog = new CreerUtilisateur();

        dialog.showAndWait(hbox.getScene().getWindow(), General.getDb()).ifPresent(result -> {
            DatabaseManager db = General.getDb();

            db.insertUser(result.pseudo(), colorToString(result.color()));
            c.setFill(result.color());
            createBoxUser(c, result.pseudo());

            nbCount++;
            if (nbCount == 5) {
                creer.setFill(Color.web("#DFDFDFDF"));
            }


            if (result.isNewPlayer()) {
                getSceneManager().changeScene("selectTutoriel");
            } else {
                getSceneManager().changeScene("accueil");
            }
        });
    

    }

    /**
     * Ajoute un nouveau cercle de connexion.
     * Limite à 5 comptes maximum.
     */
    @FXML
    private void addCount() {
            if (nbCount < 6) {
                    endSupp();

                    Circle circle = createCircle("#eaf5ff");

                    this.creerUtilisateur(circle);

            }

    }

    /**
     * Convertit une couleur JavaFX en format hexadécimal CSS.
     * @param c la couleur à convertir
     * @return la couleur au format hexadécimal
     */
    public String colorToString(Color c) {

            String couleurCSS = String.format("#%02X%02X%02X",
                            (int) (c.getRed() * 255),
                            (int) (c.getGreen() * 255),
                            (int) (c.getBlue() * 255));

            return couleurCSS;

    }

    /**
     * Applique une couleur de fond à la scène.
     * @param c la couleur à appliquer
     */
    private void appliqueCouleur(Color c) {
        String couleurCSS = colorToString(c);

        String value = String.format("-fx-background-color:%s;", couleurCSS);
                    hbox.getScene().getRoot().setStyle(value); 
    }

    /**
     * Active le mode suppression de compte et change la couleur de fond.
     */
    @FXML
    private void supCompte() {
            appliqueCouleur(Color.web("#E57373"));
            sup = true;
    }

    /**
     * Désactive le mode suppression de compte et restaure la couleur de fond.
     */
    private void endSupp() {
            appliqueCouleur(Color.WHITE);
            sup = false;
    }

    /**
     * Affiche une boîte de dialogue de confirmation pour supprimer un compte.
     * @param circle le cercle du compte à supprimer
     */
    private void supprimerCompte(Circle circle) {
        Stage s = new Stage();

        s.setTitle("Supprimer un compte");

        s.setOnCloseRequest(ev -> {
                endSupp();
        });

        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setSpacing(15);

        HBox h = new HBox();
        h.setAlignment(Pos.CENTER);
        h.setSpacing(20);

        Label l = new Label("Voulez-vous vraiment supprimer ce compte ?");
        Button bn = new Button("Annuler");
        Button bo = new Button("Valider");

        h.getChildren().addAll(bn, bo);

        v.getChildren().addAll(l, h);

        bn.setOnAction(ev -> {
                s.close();
                endSupp();
        });

        bo.setOnAction(ev -> {
                DatabaseManager db = General.getDb();

                VBox parentVBox = (VBox) circle.getParent();
                Label lab = null;

                // Parcours les enfants du VBox
                for (Node node : parentVBox.getChildren()) {
                        if (node instanceof Label) {
                                lab = (Label) node;
                                break;
                        }
                }

                db.deleteUser(lab.getText());

                hbox.getChildren().remove(circle.getParent());
                nbCount--;
                s.close();
                endSupp();
                creer.setFill(Color.web("#eaf5ff"));
        });

        Scene sn = new Scene(v);
        s.setScene(sn);
        s.show();

    }

    /**
     * Affiche un message d'avertissement si le nombre maximum de comptes est atteint.
     */
    @FXML
    private void afficheMessage() {
        if (nbCount == 5) {
                labelCreer.setVisible(true);
        }
    }
}
