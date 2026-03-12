//Attribut au packet
package hashiGRP3.Controller;



//Imports
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.InputStream;



/**
 * Controlleur de la page des techniques
 * Affiche les différentes techniques de résolution du jeu, avec des explications détaillées et des images d'exemple.
 * Hérite de ManageController pour bénéficier des fonctionnalités de navigation entre les scènes
 */
public class TechniqueController extends ManageController {

    /** Container pour les instructions. */
    @FXML
    private VBox instructionBox; 

    /** Container pour les images. */
    @FXML
    private HBox imageContainer;

    /** Titre de la page. */
    @FXML
    private Label titleLabel; 

    /**
     * Gérer le clic sur une technique : charger le contenu correspondant à la technique sélectionnée
     * @param event : l'événement de clic sur un bouton de technique
     */
    @FXML
    private void handleTechniqueClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String techniqueID = (String) clickedButton.getUserData();

        loadTechniqueContent(techniqueID);
    }

    /**
     * Charger le contenu (texte et images) correspondant à la technique sélectionnée
     * @param id : l'identifiant de la technique sélectionnée, utilisé pour déterminer quel contenu afficher
     */
    private void loadTechniqueContent(String id) {
        instructionBox.getChildren().clear();
        imageContainer.getChildren().clear();

        switch (id) {
            case "1":
                titleLabel.setText("LES COUPS FORCÉS : 4, 6, 8");
                addSectionTitle("Saturation totale des ponts :");
                addBodyText("Certaines îles exigent le maximum absolu de connexions possibles. C'est mathématique :\n\n" +
                    "1. Le '4' dans un coin (2 voisins) : Il doit faire 2 + 2.\n" +
                    "2. Le '6' sur un bord (3 voisins) : Il doit faire 2 + 2 + 2.\n" +
                    "3. Le '8' central (4 voisins) : Il doit faire 2 + 2 + 2 + 2.");
                addActionBox("Action : Ces îles ne laissent aucun choix. Tracez des doubles ponts vers TOUS leurs voisins et marquez-les comme terminées (X).");
                loadImages("/hashiGRP3/images/1312.gif", "/hashiGRP3/images/1313.gif");
                break;

            case "2":
                titleLabel.setText("Îles avec un seul voisin");
                addSectionTitle("Une seule direction possible :");
                addBodyText("Observez l'île '1' dans la rangée du bas : elle n'a qu'un seul voisin à sa droite. Elle est donc obligée de s'y connecter.\n\n" +
                    "Même logique pour le '2' en haut à droite : avec un seul voisin en dessous, il doit envoyer ses deux ponts vers lui pour être complet.\n\n" +
                    "Rappel : Une île avec un seul voisin ne peut jamais être un '3' ou plus (max 2 ponts par ligne).");
                addActionBox("Action : Tracez les ponts pour ces îles, puis marquez-les d'une croix (X) car elles sont terminées.");
                loadImages("/hashiGRP3/images/1314.gif", "/hashiGRP3/images/1315.gif");
                break;

            case "3":
                titleLabel.setText("LE 3 DANS UN COIN");
                addSectionTitle("Distribution minimale garantie :");
                addBodyText("Un '3' situé dans un coin a deux voisins. Est-il possible qu'il n'envoie aucun pont vers l'un d'eux ?\n\n" +
                            "Non ! Car même si l'autre voisin prenait le maximum de ponts (2), il manquerait encore un pont pour atteindre 3.");
                addActionBox("Action : Par sécurité, vous pouvez tracer au moins 1 pont vers chacun des deux voisins. Le troisième pont sera déterminé plus tard.");
                loadImages("/hashiGRP3/images/1316.gif", "/hashiGRP3/images/1317.gif");
                break;

            case "4":
                titleLabel.setText("LE 3 VOISIN D'UN 1");
                addSectionTitle("Déduction par élimination :");
                addBodyText("Reprenons notre '3' dans le coin. Cette fois, observez ses voisins. L'un d'eux est un '1'.\n\n" +
                            "Ce voisin '1' ne peut accepter qu'un seul pont, pas plus. Le '3' doit donc trouver une autre solution pour écouler ses ponts restants.");
                addActionBox("Action : Le '3' est forcé d'envoyer un double pont vers son autre voisin.");
                loadImages("/hashiGRP3/images/1318.gif", "/hashiGRP3/images/1319.gif");
                break;

            case "5":
                titleLabel.setText("LE 4 SUR UN BORD");
                addSectionTitle("Distribution de la charge :");
                addBodyText("Un '4' situé sur un bord du plateau n'a que 3 directions possibles.\n\n" +
                            "Si nous essayions de ne connecter que deux voisins, nous aurions besoin de deux doubles ponts (2+2). Mais souvent, la configuration des voisins bloque cette possibilité.");
                addActionBox("Action : Dans cette configuration précise, il est sûr de tracer au moins 1 pont vers chacun des trois voisins.");
                loadImages("/hashiGRP3/images/1320.gif", "/hashiGRP3/images/1321.gif");
                break;

            case "6":
                titleLabel.setText("LE 6 CENTRAL");
                addSectionTitle("Une île à forte capacité :");
                addBodyText("Le '6' est une île très puissante. Elle a généralement 4 voisins.\n\n" +
                            "Même si l'un des voisins ne prend qu'un seul pont, le '6' doit encore distribuer 5 ponts sur les 3 autres voisins. Impossible de laisser un voisin vide !");
                addActionBox("Action : Un '6' doit obligatoirement être relié à ses 4 voisins. Tracez au moins 1 pont vers chacun d'eux.");
                loadImages("/hashiGRP3/images/1322.gif", "/hashiGRP3/images/1323.gif");
                break;

            case "7":
                titleLabel.setText("Isolement d'un segment de deux îles");
                addSectionTitle("Danger de fermeture prématurée !");
                addBodyText("Le but du jeu est de relier TOUTES les îles en un seul réseau unique.\n\n" +
                            "Si vous reliez deux '1' directement entre eux, ils forment un couple satisfait (1-1) mais totalement coupé du monde. C'est une erreur fatale.");
                addActionBox("Action : Il est INTERDIT de relier ces deux '1'. Vous devez obligatoirement les relier à leurs autres voisins respectifs.");
                loadImages("/hashiGRP3/images/1324.gif", "/hashiGRP3/images/1325.gif");
                break;

            case "8":
                titleLabel.setText("Isolement d'un segment de trois îles");
                addSectionTitle("Éviter les mini-groupes fermés :");
                addBodyText("Imaginez trois îles alignées (par exemple 1-2-1). Si vous les reliez toutes entre elles, le '2' central sera satisfait, et les '1' aussi.\n\n" +
                            "Problème : Ce trio forme un îlot autonome isolé du reste du puzzle. C'est impossible.");
                addActionBox("Action : Vous devez 'casser' ce groupe fermé. Le '2' doit obligatoirement chercher une connexion vers l'extérieur.");
                loadImages("/hashiGRP3/images/1326.gif", "/hashiGRP3/images/1327.gif");
                break;

            case "9":
                titleLabel.setText("Isolement chaîne (connexion île)");
                addSectionTitle("La règle du circuit unique :");
                addBodyText("Vous avez construit une longue chaîne d'îles qui serpente sur le plateau ? Attention aux extrémités !\n\n" +
                            "Si vous reliez les deux bouts de cette chaîne ensemble, vous créez une boucle fermée. Plus aucune île ne pourra rejoindre ce groupe.");
                addActionBox("Action : Ne fermez jamais une longue boucle si d'autres îles doivent encore être rattachées. Laissez une ouverture.");
                loadImages("/hashiGRP3/images/1328.gif", "/hashiGRP3/images/1329.gif");
                break;

            case "10":
                titleLabel.setText("Isolement (connexion segment)");
                addSectionTitle("Unification des réseaux :");
                addBodyText("À un stade avancé du jeu, vous aurez souvent deux gros groupes d'îles séparés.\n\n" +
                            "Vous ne pouvez pas valider une connexion qui fermerait l'un des groupes sur lui-même. Vous devez chercher le pont qui permettra de FUSIONNER ces deux groupes.");
                addActionBox("Action : Privilégiez les ponts qui agissent comme des 'connecteurs' entre deux zones distinctes du plateau.");
                loadImages("/hashiGRP3/images/1330.gif", "/hashiGRP3/images/1331.gif");
                break;

            // --- TECHNIQUES AVANCÉES ---
            case "11":
                titleLabel.setText("Bloquer un pont");
                addSectionTitle("Raisonnement par l'absurde :");
                addBodyText("Regardez l'espace entre ces deux îles. Imaginez que vous traciez un pont ici (ligne rouge).\n\n" +
                            "Si ce pont coupe le chemin et empêche une autre île de se connecter au reste du jeu, alors ce pont est impossible. Il créerait une isolation.");
                addActionBox("Action : Puisque le pont est impossible, l'île doit obligatoirement se connecter dans l'autre direction disponible.");
                loadImages("/hashiGRP3/images/1332.gif", "/hashiGRP3/images/1333.gif", "/hashiGRP3/images/1334.gif");
                break;

            case "12":
                titleLabel.setText("Ajouter un pont forcé");
                addSectionTitle("Sauver le groupe :");
                addBodyText("Analysez la structure du réseau. Parfois, un groupe d'îles ne possède qu'une seule 'porte de sortie' vers le reste du plateau.\n\n" +
                            "Si vous ne mettez pas de pont à cet endroit précis, ce groupe mourra isolé.");
                addActionBox("Action : Ce pont est critique et obligatoire. Tracez-le pour garantir la connectivité globale.");
                loadImages("/hashiGRP3/images/1335.gif", "/hashiGRP3/images/1336.gif", "/hashiGRP3/images/1337.gif");
                break;

            case "13":
                titleLabel.setText("Isoler une île avec des ponts");
                addSectionTitle("Anticipation à un coup :");
                addBodyText("Observez le '1'. S'il ne se connecte pas au '2' voisin, le '2' sera forcé de se connecter ailleurs.\n\n" +
                            "Mais si le '2' se connecte ailleurs et se sature, le '1' se retrouvera tout seul sans voisin disponible !");
                addActionBox("Action : Pour ne pas 'tuer' le 1, vous êtes obligé de le relier au 2 dès maintenant.");
                loadImages("/hashiGRP3/images/1338.gif", "/hashiGRP3/images/1339.gif", "/hashiGRP3/images/1340.gif");
                break;

            case "14":
                titleLabel.setText("Conflits de connexion");
                addSectionTitle("Gestion des incompatibilités :");
                addBodyText("Faisons une hypothèse : Si je connecte l'île A vers la droite, cela force l'île B à aller vers le bas.\n\n" +
                            "Cependant, si cela laisse une troisième île C avec un nombre de ponts impossible à satisfaire, alors l'hypothèse de départ était fausse.");
                addActionBox("Action : L'hypothèse A vers la droite mène à une erreur. Il faut donc faire l'inverse (A ne va pas à droite).");
                loadImages("/hashiGRP3/images/1341.gif", "/hashiGRP3/images/1342.gif", "/hashiGRP3/images/1343.gif");
                break;
        }
    }

    /**
     * Ajouter un titre de section dans la boîte d'instructions
     * @param text : le texte du titre à afficher
     */
    private void addSectionTitle(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 16));
        lbl.setStyle("-fx-text-fill: #2c3e50; -fx-padding: 10 0 5 0;"); 
        instructionBox.getChildren().add(lbl);
    }

    /**
     * Ajouter un paragraphe de texte dans la boîte d'instructions
     * @param text : le texte du paragraphe à afficher
     */
    private void addBodyText(String text) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setFont(Font.font("System", 14));
        lbl.setStyle("-fx-text-fill: black;");
        instructionBox.getChildren().add(lbl);
    }

    /**
     * Ajouter une boîte d'action avec un style distinctif pour mettre en avant les conseils pratiques
     * @param text : le texte de l'action à afficher
     */
    private void addActionBox(String text) {
        Label lbl = new Label(text);
        lbl.setWrapText(true);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        lbl.setStyle("-fx-background-color: #e8f8f5; -fx-text-fill: #27ae60; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #27ae60; -fx-border-radius: 5;");
        VBox.setMargin(lbl, new javafx.geometry.Insets(10, 0, 0, 0));
        instructionBox.getChildren().add(lbl);
    }

    /**
     * Charger les images d'exemple pour la technique sélectionnée
     * @param paths : les chemins des images à charger depuis les ressources
     */
    private void loadImages(String... paths) {
        for (String path : paths) {
            try {
                InputStream is = getClass().getResourceAsStream(path);
                if (is != null) {
                    ImageView iv = new ImageView(new Image(is));
                    iv.setFitHeight(200);
                    iv.setPreserveRatio(true);
                    imageContainer.getChildren().add(iv);
                } else {
                    System.out.println("Image not found: "+ path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
}
