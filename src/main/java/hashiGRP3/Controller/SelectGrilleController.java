//Attribut au packet
package hashiGRP3.Controller;

import javafx.event.ActionEvent;
//Imports
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hashiGRP3.DatabaseManager;

public class SelectGrilleController extends ManageController {

    /* ===================== DATABASE ===================== */

    private DatabaseManager databaseManager = new DatabaseManager();

    /* ===================== LEADERBOARD ===================== */

    @FXML
    private Label labelGrilleSelected;
    @FXML
    private ImageView imageGrilleSelected;
    @FXML
    private Label labelNombreIle;
    @FXML
    private Label labelTempsPerso;

    /* ===================== SECTION DES GRILLES ===================== */

    @FXML
    private GridPane grilleFacile;
    @FXML
    private GridPane grilleMoyen;
    @FXML
    private GridPane grilleDifficile;

    private static final int COLONNES = 4;

    @FXML
    public void initialize() {
        labelGrilleSelected.setText("");
        imageGrilleSelected.setVisible(false);
        labelNombreIle.setText("Nombre d'île : None");
        labelTempsPerso.setText("Temps perso : None");

        creerGrilles(grilleFacile, 1, 4, "sectionFacile");
        creerGrilles(grilleMoyen, 5, 8, "sectionMoyen");
        creerGrilles(grilleDifficile, 9, 12, "sectionDifficile");
    }


    /* ===================== GRILLES ===================== */

    @Override
    public void refreshGrilles() {
        grilleFacile.getChildren().clear();
        grilleMoyen.getChildren().clear();
        grilleDifficile.getChildren().clear();
        creerGrilles(grilleFacile, 1, 4, "sectionFacile");
        creerGrilles(grilleMoyen, 5, 8, "sectionMoyen");
        creerGrilles(grilleDifficile, 9, 12, "sectionDifficile");
    }

    private void creerGrilles(GridPane container,
            int debut,
            int fin,
            String styleClass) {

        // Var
        int col = 0;
        int row = 0;

        for (int i = debut; i <= fin; i++) {
            VBox carte = creerCarteGrille(i, styleClass);
            container.add(carte, col, row);

            col++;
            if (col == COLONNES) {
                col = 0;
                row++;
            }
        }
    }


    private void afficherGrilleSelectionnee(int numeroGrille) {

        labelGrilleSelected.setText("Grille " + numeroGrille);
        imageGrilleSelected.setVisible(true);

        labelNombreIle.setText(
                "Nombre d'île : " + databaseManager.obtenirNombreIle(numeroGrille));

        labelTempsPerso.setText(
                "Temps perso : " + databaseManager.obtenirScore(numeroGrille, getUtilisateur()));
    }

    private VBox creerCarteGrille(int numeroGrille, String styleClass) {

        VBox box = new VBox(10);
        box.setPrefWidth(155);
        box.setPadding(new Insets(12));

        Label titre = new Label("Grille " + numeroGrille);
        titre.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        titre.setAlignment(Pos.CENTER);
        titre.setMaxWidth(Double.MAX_VALUE);

        ImageView image = new ImageView(
                new Image(getClass().getResourceAsStream("/hashiGRP3/images/pointHashi.png")));
        image.setFitWidth(90);
        image.setFitHeight(90);
        image.setPreserveRatio(true);

        Button bouton = new Button();
        bouton.setGraphic(image);
        bouton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        bouton.getStyleClass().add(styleClass);
        bouton.setOnAction(e -> afficherGrilleSelectionnee(numeroGrille));

        Label score = new Label();
        score.setStyle("-fx-font-size: 12px;");
        score.setAlignment(Pos.CENTER);
        score.setMaxWidth(Double.MAX_VALUE);

        score.setVisible(false);
        score.setManaged(false);
        if (databaseManager.grilleCompletee(numeroGrille, getUtilisateur())) {
            score.setText("Score : " +
                    databaseManager.obtenirScore(numeroGrille, getUtilisateur()) + " sec");
            score.setVisible(true);
            score.setManaged(true);
        }

        box.getChildren().addAll(titre, bouton, score);

        return box;
    }

}
