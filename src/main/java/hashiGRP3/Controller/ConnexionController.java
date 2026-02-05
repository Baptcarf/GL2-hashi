//Attribut au packet
package hashiGRP3.Controller;



//Imports
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import hashiGRP3.SceneManager;
import hashiGRP3.DatabaseManager;
import hashiGRP3.compDB.Utilisateur;
import hashiGRP3.Scene.CreerUtilisateur;

import java.sql.*;
import java.util.List;



/* Class */
public class ConnexionController extends ManageController {

        private int nbCount;

        private boolean sup = false;
        private boolean mod = false;
        @FXML
        private HBox hbox;

        @FXML
        private Circle creer;

        @FXML
        private Label labelCreer;

        @FXML
        private Button supprimer;

        public void setSceneManager(SceneManager sm) {
                super.setSceneManager(sm);
                nbCount = 0;
                Tooltip.install(creer, new Tooltip("Créer un compte"));
                Tooltip.install(supprimer, new Tooltip("supprimer un compte"));

                List<Utilisateur> au = sm.getBD().findAllUser();

                for (Utilisateur u : au) {
                        Circle c = createCircle(u.getColor());
                        createBoxUser(c, u.getPseudo());
                        nbCount++;
                }
                if (nbCount == 5) {
                        creer.setFill(Color.web("#DFDFDFDF"));
                }

        }

        @FXML
        private void changeScene(ActionEvent event) {
                Button btn = (Button) event.getSource();
                String sceneName = (String) btn.getUserData();
                if (getSceneManager() != null && sceneName != null) {
                        getSceneManager().changeScene(sceneName);
                }

        }

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
         * Creer un cercle de connexion à afficher.
         */
        private Circle createCircle(String color) {
                // Apparence
                Circle circle = new Circle();
                circle.setRadius(100); // même taille que rightCircle
                circle.setFill(Color.web(color));
                circle.setStroke(Color.BLACK);
                circle.setStrokeWidth(2);

                // Logique
                circle.setOnMouseClicked(event -> {
                        if (sup) {

                                supprimerCompte(circle);

                        } else {
                                getSceneManager().changeScene("accueil");
                        }

                });

                // Retour
                return circle;
        }

        /**
         * Ajoute un cercle de connexion à la page d'accueil.
         */
        @FXML
        private void addCount() {
                if (nbCount < 6) {
                        endSupp();

                        Circle circle = createCircle("#eaf5ff");

                        this.creerUtilisateur(circle);

                }

        }

        public String colorToString(Color c) {

                String couleurCSS = String.format("#%02X%02X%02X",
                                (int) (c.getRed() * 255),
                                (int) (c.getGreen() * 255),
                                (int) (c.getBlue() * 255));

                return couleurCSS;

        }

        private void appliqueCouleur(Color c) {
                String couleurCSS = colorToString(c);

                String value = String.format("-fx-background-color:%s;", couleurCSS);
                getSceneManager().findScene("connexion").getRoot()
                                .setStyle(value);

        }

        @FXML
        private void supCompte() {
                appliqueCouleur(Color.web("#E57373"));
                sup = true;
        }

        private void endSupp() {
                appliqueCouleur(Color.WHITE);
                sup = false;
        }

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
                        DatabaseManager db = getSceneManager().getBD();

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

        @FXML
        private void afficheMessage() {
                if (nbCount == 5) {
                        labelCreer.setVisible(true);
                }

        }

        @FXML
        private void enleverMessage() {
                labelCreer.setVisible(false);

        }


	private void creerUtilisateur(Circle c) {
		CreerUtilisateur dialog = new CreerUtilisateur();

		dialog.showAndWait(hbox.getScene().getWindow()).ifPresent(result -> {
			DatabaseManager db = getSceneManager().getBD();

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


}
