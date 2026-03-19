//Attribut au packet
package hashiGRP3.Controller;

//Imports
import javafx.event.ActionEvent;
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

/**
 * Contrôleur pour la scène de sélection de grille. Permet à l'utilisateur de
 * choisir une grille à jouer,
 * d'afficher les détails de la grille sélectionnée, et de lancer le jeu avec la
 * grille choisie.
 * Permet également d'afficher le leaderboard pour chaque grille.
 * Hérite de ManageController pour bénéficier des fonctionnalités de navigation
 * entre les scènes
 */
public class SelectGrilleController extends ManageController {

    /**
     * Gestionnaire de base de données pour accéder aux informations sur les
     * grilles.
     */
    private DatabaseManager databaseManager = new DatabaseManager();

    /* ===================== LABELS ===================== */

    /** Label affichant la grille sélectionnée. */
    @FXML
    private Label labelGrilleSelected;

    /** Image affichée à côté du label de la grille sélectionnée. */
    @FXML
    private ImageView imageGrilleSelected;

    /** Label affichant le nombre d'îles dans la grille sélectionnée. */
    @FXML
    private Label labelNombreIle;

    /** Label affichant le temps personnel pour la grille sélectionnée. */
    @FXML
    private Label labelTempsPerso;

    /**
     * Label affichant le score 1 dans le leaderboard pour la grille sélectionnée.
     */
    @FXML
    private Label labelScore1;

    /**
     * Label affichant le score 2 dans le leaderboard pour la grille sélectionnée.
     */
    @FXML
    private Label labelScore2;

    /**
     * Label affichant le score 3 dans le leaderboard pour la grille sélectionnée.
     */
    @FXML
    private Label labelScore3;

    /**
     * Label affichant le score 4 dans le leaderboard pour la grille sélectionnée.
     */
    @FXML
    private Label labelScore4;

    /**
     * Label affichant le score 5 dans le leaderboard pour la grille sélectionnée.
     */
    @FXML
    private Label labelScore5;

    /* ================================================================ */

    /** Bouton pour lancer la grille sélectionnée. */
    @FXML
    private Button boutonJouer;

    /* ===================== SECTION DES GRILLES ===================== */

    /** GridPane contenant les grilles faciles. */
    @FXML
    private GridPane grilleFacile;

    /** GridPane contenant les grilles moyennes. */
    @FXML
    private GridPane grilleMoyen;

    /** GridPane contenant les grilles difficiles. */
    @FXML
    private GridPane grilleDifficile;

    /** Nombre de colonnes dans les GridPane de grilles. */
    private static int COLONNES = 5;

    /** Initialise les éléments de la scène */
    @FXML
    public void initialize() {
        chargerLeaderboardVide();
        boutonJouer.setDisable(true);
    }

    /**
     * Rafraîchit les grilles affichées.
     * Permet de mettre à jour les informations affichées (score personnel,
     * leaderboard)
     * après que l'utilisateur ait complété une grille ou se soit connecté.
     */
    @Override
    public void refreshGrilles() {
        grilleFacile.getChildren().clear();
        grilleMoyen.getChildren().clear();
        grilleDifficile.getChildren().clear();

        creerGrilles(grilleFacile, 1, 5, "sectionFacile");
        creerGrilles(grilleMoyen, 6, 10, "sectionMoyen");
        creerGrilles(grilleDifficile, 11, 15, "sectionDifficile");

        // Déplacé ici depuis initialize() — l'utilisateur est connu à ce stade
        General.setId_grille(1);
        afficherGrilleSelectionnee(General.getId_grille());
        boutonJouer.setDisable(true);
    }

    /**
     * Crée les cartes de grille et les ajoute au conteneur spécifié.
     * Chaque carte de grille contient un titre, une image cliquable pour
     * sélectionner la grille,
     * et un label pour afficher le score si la grille est complétée.
     * 
     * @param container  : le GridPane dans lequel ajouter les cartes de grille
     * @param debut      : le numéro de la première grille à créer
     * @param fin        : le numéro de la dernière grille à créer
     * @param styleClass : la classe CSS à appliquer aux boutons de grille pour le
     *                   style
     */
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

    /**
     * Affiche les détails de la grille sélectionnée : le nombre d'îles et le score
     * personnel,
     * 
     * @param numeroGrille : le numéro de la grille sélectionnée
     */
    private void afficherGrilleSelectionnee(int numeroGrille) {

        labelGrilleSelected.setText("Grille " + numeroGrille);
        imageGrilleSelected.setVisible(true);

        labelNombreIle.setText(
                "Nombre d'île : " + databaseManager.obtenirNombreIle(numeroGrille));

        labelTempsPerso.setText(
                "Score : " + formatScore(databaseManager.obtenirScore(numeroGrille, getUtilisateur())));

        chargerLeaderboard(numeroGrille);
    }

    /**
     * Crée une carte de grille avec un titre, une image cliquable pour sélectionner
     * la grille,
     * et un label pour afficher le score si la grille est complétée.
     * 
     * @param numeroGrille : le numéro de la grille à représenter
     * @param styleClass   : la classe CSS à appliquer au bouton de la grille pour
     *                     le style
     * @return une VBox représentant la carte de la grille
     */
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
            General.setNum_grille(numeroGrille);
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
                    formatScore(databaseManager.obtenirScore(numeroGrille, getUtilisateur())));
            score.setVisible(true);
            score.setManaged(true);
        }

        // Utiliser un StackPane pour superposer le score sur le bouton sans affecter la
        // hauteur
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(bouton);
        stackPane.getChildren().add(score);
        StackPane.setAlignment(score, Pos.BOTTOM_CENTER);
        StackPane.setMargin(score, new Insets(0, 0, -15, 0));

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
                String[] parts = top5.get(i).split(" ");
                String pseudo = parts[0];
                int score = Integer.parseInt(parts[1].replace("s", ""));
                labels[i].setText((i + 1) + ". " + pseudo + " " + formatScore(score));
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
        int idPartie = General.getDb().creerPartie(General.getIdUtilisateur(), General.getNum_grille());
        General.setId_partie(idPartie);
        this.changeScene(event);
    }

    private String formatScore(int score) {
        int minutes = score / 60;
        int secondes = score % 60;
        return String.format("%02d:%02d", minutes, secondes);

    }
}
