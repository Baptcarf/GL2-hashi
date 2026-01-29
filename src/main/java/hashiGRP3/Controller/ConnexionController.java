package hashiGRP3.Controller;

/* Libs */
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import hashiGRP3.SceneManager;

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

        public void setSceneManager(SceneManager sm) {
                super.setSceneManager(sm);
                nbCount = 0;
                Tooltip.install(creer, new Tooltip("Créer un compte"));
        }

        @FXML
        private void changeScene(ActionEvent event) {
                Button btn = (Button) event.getSource();
                String sceneName = (String) btn.getUserData();
                if (getSceneManager() != null && sceneName != null) {
                        getSceneManager().changeScene(sceneName);
                }
        }

        @FXML
        private void addCount() {
                if (nbCount < 5) {
                        sup = false;
                        appliqueCouleur(Color.WHITE);
                        Circle circle = new Circle();
                        circle.setRadius(100); // même taille que rightCircle
                        circle.setFill(Color.web("#eaf5ff"));
                        circle.setStroke(Color.BLACK);
                        circle.setStrokeWidth(2);

                        circle.setOnMouseClicked(event -> {
                                if (sup) {
                                        hbox.getChildren().remove(circle);
                                        nbCount--;
                                        sup = false;
                                        appliqueCouleur(Color.WHITE);
                                } else {
                                        getSceneManager().changeScene("accueil");
                                }

                        });

                        hbox.getChildren().add(0, circle);

                        nbCount += 1;
                        if (nbCount == 5) {
                                creer.setFill(Color.web("#DFDFDFDF"));
                        } else {
                                this.creerUtilisateur(circle);
                        }
                }

        }

        private void appliqueCouleur(Color c) {
                String couleurCSS = String.format("#%02X%02X%02X",
                                (int) (c.getRed() * 255),
                                (int) (c.getGreen() * 255),
                                (int) (c.getBlue() * 255));
                String value = String.format("-fx-background-color:%s;", couleurCSS);
                getSceneManager().findScene("connexion").getRoot()
                                .setStyle(value);

        }

        @FXML
        private void supCompte() {
                appliqueCouleur(Color.web("#E57373"));
                sup = true;
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
                Stage s = new Stage();
                s.setTitle("Creer un compte");

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setAlignment(Pos.CENTER);

                s.setOnCloseRequest(ev -> {
                        hbox.getChildren().remove(c);
                        nbCount -= 1;
                });

                TextField pseudofield = new TextField();
                ColorPicker cp = new ColorPicker(Color.WHITE);
                CheckBox cb = new CheckBox("Je n'ai jamais joué au Hashi");
                Button b = new Button("Commencé l'aventure");

                cb.setSelected(true);
                Label messageLabel = new Label();
                messageLabel.setTextFill(Color.RED);

                grid.addRow(0, new Label("Pseudo:"), pseudofield);
                grid.add(cp, 0, 1, 2, 1);
                grid.add(cb, 0, 2, 2, 1);
                grid.add(b, 0, 3, 2, 1);
                grid.add(messageLabel, 0, 4, 2, 1);

                b.setOnAction(ev -> {
                        try {
                                String pseudo = pseudofield.getText();
                                c.setFill(cp.getValue());

                                if (!pseudo.equals("")) {
                                        s.close();
                                        if (cb.isSelected()) {
                                                getSceneManager().changeScene("tutorielle");
                                        } else {
                                                getSceneManager().changeScene("accueil");
                                        }
                                }
                                messageLabel.setTextFill(Color.RED);
                                messageLabel.setText("Erreur : Pseudo non renseigné");

                        } catch (NumberFormatException ex) {
                                messageLabel.setTextFill(Color.RED);
                                messageLabel.setText("Erreur : saisie invalide pour les champs numériques !");
                        }
                });
                Scene sc = new Scene(grid, 400, 400);
                s.setScene(sc);
                s.show();

        }

}
