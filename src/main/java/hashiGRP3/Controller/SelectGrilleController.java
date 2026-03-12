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
import javafx.scene.layout.StackPane;

import hashiGRP3.DatabaseManager;
import hashiGRP3.Logic.General;

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
    @FXML
    private Label labelScore1;
    @FXML
    private Label labelScore2;
    @FXML
    private Label labelScore3;
    @FXML
    private Label labelScore4;
    @FXML
    private Label labelScore5;

    /* ===================== BOUTTONS ===================== */

    @FXML
    private Button boutonJouer;

    /* ===================== SECTION DES GRILLES ===================== */

    @FXML
    private GridPane grilleFacile;
    @FXML
    private GridPane grilleMoyen;
    @FXML
    private GridPane grilleDifficile;

    private static final int COLONNES = 5;

    @FXML
    public void initialize() {
        General.setId_grille(1);
        // Plus d'appel à afficherGrilleSelectionnee ici
        // Plus de boutonJouer.setDisable ici (géré dans refreshGrilles)
        creerGrilles(grilleFacile, 1, 4, "sectionFacile");
        creerGrilles(grilleMoyen, 5, 8, "sectionMoyen");
        creerGrilles(grilleDifficile, 9, 12, "sectionDifficile");
        chargerLeaderboardVide();
        boutonJouer.setDisable(true);
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

        // Déplacé ici depuis initialize() — l'utilisateur est connu à ce stade
        afficherGrilleSelectionnee(General.getId_grille());
        boutonJouer.setDisable(true);
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
                "Score : " + databaseManager.obtenirScore(numeroGrille, getUtilisateur()) + "s");

        chargerLeaderboard(numeroGrille);
    }

    private VBox creerCarteGrille(int numeroGrille, String styleClass) {

        VBox box = new VBox(10);
        box.setPrefWidth(210);
        box.setPadding(new Insets(12));
        box.setAlignment(Pos.CENTER);

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
        bouton.setOnAction(e -> {
            General.setId_grille(numeroGrille);
            afficherGrilleSelectionnee(numeroGrille);
            boutonJouer.setDisable(false);
        });

        Label score = new Label();
        score.setStyle("-fx-font-size: 12px;");
        score.setAlignment(Pos.CENTER);
        score.setMaxWidth(Double.MAX_VALUE);

        score.setVisible(false);
        score.setManaged(false);
        if (databaseManager.grilleCompletee(numeroGrille, getUtilisateur())) {
            score.setText("Score : " +
                    databaseManager.obtenirScore(numeroGrille, getUtilisateur()) + "s");
            score.setVisible(true);
            score.setManaged(true);
        }

        // Utiliser un StackPane pour superposer le score sur le bouton sans affecter la
        // hauteur
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(bouton);
        stackPane.getChildren().add(score);
        StackPane.setAlignment(score, Pos.BOTTOM_CENTER);
        StackPane.setMargin(score, new Insets(0, 0, -15, 0)); // Descendre le score de 15 pixels

        box.getChildren().addAll(titre, stackPane);

        return box;
    }

    /**
     * Charge le leaderboard vide pour la page d'accueil (aucune grille
     * sélectionnée)
     */
    private void chargerLeaderboardVide() {
        Label[] labels = { labelScore1, labelScore2, labelScore3, labelScore4, labelScore5 };

        for (int i = 0; i < 5; i++) {
            labels[i].setText((i + 1) + ".");
        }
    }

    /**
     * Charge le leaderboard avec les 5 meilleurs scores pour une grille spécifique
     */
    private void chargerLeaderboard(int numeroGrille) {
        java.util.List<String> top5 = databaseManager.obtenirTop5ScoresParGrille(numeroGrille);

        Label[] labels = { labelScore1, labelScore2, labelScore3, labelScore4, labelScore5 };

        for (int i = 0; i < 5; i++) {
            if (i < top5.size()) {
                labels[i].setText((i + 1) + ". " + top5.get(i));
            } else {
                labels[i].setText((i + 1) + ".");
            }
        }
    }

    /**
     * Changement de scène depuis un fichier fxml
     * 
     * @param event : Un événement reçu.
     */
    @FXML
    public void lancerGrille(ActionEvent event) {

        General.getHashi().remplirHistorique();

        this.changeScene(event);
	}

    private boolean grilleCompletee(int numeroGrille) {
        // TODO : vérifier dans la base de données
        return false;
    }
}
