package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class SelectGrilleController extends ManageController {

    /* ===================== LEADERBOARD ===================== */

    @FXML private Label labelGrilleSelected;
    @FXML private ImageView imageGrilleSelected;
    @FXML private Label labelNombreIle;
    @FXML private Label labelTempsPerso;

    /* ===================== SECTION DES GRILLES ===================== */

    @FXML private GridPane grilleFacile;
    @FXML private GridPane grilleMoyen;
    @FXML private GridPane grilleDifficile;


    private static final int COLONNES = 4;


    @FXML
    public void initialize() {
        // État initial du panneau de droite
        labelGrilleSelected.setText("");
        imageGrilleSelected.setVisible(false);
        labelNombreIle.setText("Nombre d'île : None");
        labelTempsPerso.setText("Temps perso : None");

        // Création dynamique des boutons
        creerGrilles(grilleFacile, 1, 4, "sectionFacile");
        creerGrilles(grilleMoyen, 5, 8, "sectionMoyen");
        creerGrilles(grilleDifficile, 9, 12, "sectionDifficile");
    }

    
    // Créer les cartes de grille dans un conteneur donné
    private void creerGrilles(GridPane container,
                          int debut,
                          int fin,
                          String styleClass) {

        int col = 0;
        int row = 0;

        for (int i = debut; i <= fin; i++) {
            VBox carte = creerCarteGrille(i, styleClass);

            container.add(carte, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
    }


    //Affiche la grille selectionnée dans le leaderboard
    private void afficherGrilleSelectionnee(int numeroGrille) {
        labelGrilleSelected.setText("Grille " + numeroGrille);
        imageGrilleSelected.setVisible(true);

        labelNombreIle.setText("Nombre d'île : " + obtenirNombreIle(numeroGrille));
        labelTempsPerso.setText("Temps perso : " + obtenirScore(numeroGrille));
    }

    //Genere les grilles dans les 3 sections (facile, moyen, difficile)
    private VBox creerCarteGrille(int numeroGrille, String styleClass) {

        VBox box = new VBox(10);
        box.setPrefWidth(155);
        box.setPadding(new Insets(12));

        // Titre
        Label titre = new Label("Grille " + numeroGrille);
        titre.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        titre.setAlignment(Pos.CENTER);
        titre.setMaxWidth(Double.MAX_VALUE);

        // Bouton avec image
        ImageView image = new ImageView(
                new Image(getClass().getResourceAsStream("/hashiGRP3/images/pointHashi.png"))
        );
        image.setFitWidth(90);
        image.setFitHeight(90);
        image.setPreserveRatio(true);

        Button bouton = new Button();
        bouton.setGraphic(image);
        bouton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        bouton.getStyleClass().add(styleClass);
        bouton.setOnAction(e -> afficherGrilleSelectionnee(numeroGrille));

        // Score
        Label score = new Label();
        score.setStyle("-fx-font-size: 12px;");
        score.setAlignment(Pos.CENTER);
        score.setMaxWidth(Double.MAX_VALUE);

        score.setVisible(false);
        score.setManaged(false);

        if (grilleCompletee(numeroGrille)) {
            score.setText("Score : " + obtenirScore(numeroGrille));
            score.setVisible(true);
            score.setManaged(true);
        }

        box.getChildren().addAll(titre, bouton, score);

        return box;
    }


    

    /* ===================== MÉTHODES TEMPORAIRES ===================== */

    private int obtenirScore(int numeroGrille) {
       /* String sql = "SELECT id_
        return -1;*/
        return -1;
    }

    private int obtenirNombreIle(int numeroGrille) {
        // TODO : valeur réelle
        return -1;
    }

    private boolean grilleCompletee(int numeroGrille) {
        // TODO : vérifier dans la base de données
        return false;
    }

}
