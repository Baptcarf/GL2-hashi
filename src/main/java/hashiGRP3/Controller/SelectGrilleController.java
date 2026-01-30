package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Contrôleur qui hérite de MenuController et ajoute la sélection de grille
 */
public class SelectGrilleController extends ManageController {

    // ====== Leaderboard dynamique ======
    @FXML private Label labelGrilleSelected;
    @FXML private ImageView imageGrilleSelected;
    @FXML private Label labelScoreSelected;
    @FXML private Label labelNombreIle;
    @FXML private Label labelTempsPerso;

    // Boutons de toutes les grilles
    @FXML private Button sectionGrille1;
    @FXML private Button sectionGrille2;
    @FXML private Button sectionGrille3;
    @FXML private Button sectionGrille4;
    @FXML private Button sectionGrille5;
    @FXML private Button sectionGrille6;
    @FXML private Button sectionGrille7;
    @FXML private Button sectionGrille8;
    @FXML private Button sectionGrille9;
    @FXML private Button sectionGrille10;
    @FXML private Button sectionGrille11;
    @FXML private Button sectionGrille12;

    @FXML
    public void initialize() {
        // Initialisation du leaderboard (aucune grille sélectionnée)
        labelGrilleSelected.setText("");
        imageGrilleSelected.setVisible(false);
        labelScoreSelected.setText("");
        labelNombreIle.setText("Nombre d'île : None");
        labelTempsPerso.setText("Temps perso : None");

        // Liste de tous les boutons
        List<Button> toutesLesGrilles = List.of(
                sectionGrille1, sectionGrille2, sectionGrille3, sectionGrille4,
                sectionGrille5, sectionGrille6, sectionGrille7, sectionGrille8,
                sectionGrille9, sectionGrille10, sectionGrille11, sectionGrille12
        );

        // Associer chaque bouton à son numéro de grille
        for (int i = 0; i < toutesLesGrilles.size(); i++) {
            final int index = i + 1;
            Button b = toutesLesGrilles.get(i);
            b.setOnAction(event -> afficherGrilleSelectionnee(index));
        }
    }

    private void afficherGrilleSelectionnee(int numeroGrille) {
        labelGrilleSelected.setText("Grille " + numeroGrille);
        imageGrilleSelected.setVisible(true);
        labelScoreSelected.setText("Score : " + obtenirScore(numeroGrille));
        labelNombreIle.setText("Nombre d'île : " + obtenirNombreIle(numeroGrille));
        labelTempsPerso.setText("Temps perso : " + obtenirTempsPerso(numeroGrille));
    }

    // ====== Méthodes fictives à remplacer par ta logique ======
    private int obtenirScore(int numeroGrille) {
        return 0; // TODO: récupérer le score réel
    }

    private int obtenirNombreIle(int numeroGrille) {
        return 5; // TODO: récupérer le nombre réel d'îles
    }

    private String obtenirTempsPerso(int numeroGrille) {
        return "00:00"; // TODO: récupérer le temps réel
    }
}
