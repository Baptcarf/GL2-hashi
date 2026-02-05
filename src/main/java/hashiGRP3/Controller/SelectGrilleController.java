package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SelectGrilleController extends ManageController {

    // Leaderboard dynamique
    @FXML private Label labelGrilleSelected;
    @FXML private ImageView imageGrilleSelected;
    @FXML private Label labelScoreSelected;
    @FXML private Label labelNombreIle;
    @FXML private Label labelTempsPerso;

    // Conteneur des boutons
    @FXML private GridPane grilleContainer;

    private static final int NOMBRE_GRILLES = 12;
    private static final int COLONNES = 4;

    @FXML
    public void initialize() {
        labelGrilleSelected.setText("");
        imageGrilleSelected.setVisible(false);
        labelScoreSelected.setText("");
        labelNombreIle.setText("Nombre d'île : None");
        labelTempsPerso.setText("Temps perso : None");

        creerBoutonsGrilles();
    }

    private void creerBoutonsGrilles() {
        grilleContainer.getChildren().clear();

        for (int i = 0; i < NOMBRE_GRILLES; i++) {
            int numeroGrille = i + 1;

            Button bouton = new Button("Grille " + numeroGrille);
            bouton.setPrefWidth(150);
            bouton.setOnAction(e -> afficherGrilleSelectionnee(numeroGrille));

            int colonne = i % COLONNES;
            int ligne = i / COLONNES;

            grilleContainer.add(bouton, colonne, ligne);
        }
    }

    private void afficherGrilleSelectionnee(int numeroGrille) {
        labelGrilleSelected.setText("Grille " + numeroGrille);
        imageGrilleSelected.setVisible(true);
        labelScoreSelected.setText("Score : " + obtenirScore(numeroGrille));
        labelNombreIle.setText("Nombre d'île : " + obtenirNombreIle(numeroGrille));
        labelTempsPerso.setText("Temps perso : " + obtenirTempsPerso(numeroGrille));
    }

    /* Méthodes temporaires */
    private int obtenirScore(int numeroGrille) {
        return 0;
    }

    private int obtenirNombreIle(int numeroGrille) {
        return 5;
    }

    private String obtenirTempsPerso(int numeroGrille) {
        return "00:00";
    }
}
