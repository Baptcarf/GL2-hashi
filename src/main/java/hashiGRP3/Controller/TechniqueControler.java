package hashiGRP3.Controller;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import hashiGRP3.SceneManager;

public class TechniqueControler {
       

    private SceneManager sceneManager;
    @FXML private TextArea mainContentArea;
    @FXML private HBox imageContainer;

    @FXML
    private void handleTechniqueClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String techniqueName = (String) clickedButton.getUserData(); 
        
        loadTechniqueContent(techniqueName);
    }

    private void loadTechniqueContent(String techniqueName) {
        
        imageContainer.getChildren().clear();

        String text = "";
        String imagePath1 = "";
        String imagePath2 = "";
        String imagePath3 = "";

        
        switch (techniqueName) {
            case "1":
                text = "Iles avec 4 dans le coin, 6 sur le côté et 8 au milieu:\n\n"
                     + "Une île dans un coin ne peut pas avoir plus de deux voisins et le nombre de ponts vers chaque voisin ne peut pas être supérieur à 2. L'île dans le coin avec l'indice 4 doit donc avoir deux ponts reliés à chacun de ses deux voisins.  "
                     + "De même, l'île du côté du puzzle avec l'indice 6 doit avoir deux ponts reliés à chacun de ses trois voisins et l'île au milieu du puzzle avec l'indice 8 doit avoir deux ponts reliés à chacun de ses quatre voisins."
                     + "Dans tous les cas, le nombre total de ponts est connecté, ce qui signifie que les îles peuvent être marquées d'un X.";
                imagePath1 = "/hashiGRP3/images/1312.gif";
                imagePath2 = "/hashiGRP3/images/1313.gif";
                break;

            case "2":
                text = "Îles avec un seul voisin:\n\n"
                     + "L'île contenant 1 dans la rangée du bas n'a qu'un seul voisin à sa droite, ce qui signifie que nous devons relier un seul pont entre ces îles."
                     + "De même, l'île contenant 2 en haut à droite n'a qu'un seul voisin en dessous, ce qui signifie que nous devons relier deux ponts entre ces îles. Dans les deux cas, l'île contenant 1 et l'île contenant 2 sont terminées, ce qui signifie qu'elles peuvent être marquées d'un X."
                     + "Notez qu'une île avec un seul voisin ne peut jamais contenir 3 ou plus car cela violerait les règles de Hashi.";
                imagePath1 = "/hashiGRP3/images/1314.gif";
                imagePath2 = "/hashiGRP3/images/1315.gif";
                break;
                
            case "3":
                text = "Iles avec 3 dans le coin, 5 sur le côté et 7 au milieu\n\n"
                + "Une île dans un coin avec l'indice 3 doit avoir deux voisins, avec un pont relié à un voisin et deux ponts reliés à l'autre voisin."
                + "Bien que nous ne sachions pas quel voisin possède deux ponts, nous pouvons être certains qu'il y a au moins un pont dans chaque direction comme on le voit avec l'île dans le coin inférieur gauche de cet exemple."
                + "De même, une île sur le côté du puzzle avec l'indice 5 doit avoir au moins un pont relié à chacun de ses trois voisins et une île au milieu du puzzle avec l'indice 7 doit avoir au moins un pont relié à chacun de ses quatre voisins.";
                imagePath1 = "/hashiGRP3/images/1316.gif";
                imagePath2 = "/hashiGRP3/images/1317.gif";
                break;
            case "4":
                text = "Cas particuliers de 3 dans le coin, 5 sur le côté et 7 au milieu\n\n"
                + "Si une île dans un coin a un indice 3 et que l'une de ses voisines est une île avec l'indice 1, alors toutes les conditions sont remplies et trois ponts peuvent être tracés."
                + "La même logique peut être appliquée si une île sur le côté du puzzle avec l'indice 5 ou si une île au milieu du puzzle avec l'indice 7 a une île voisine avec l'indice 1.";                
                imagePath1 = "/hashiGRP3/images/1318.gif";
                imagePath2 = "/hashiGRP3/images/1319.gif";
                break;
            case "5":
                text = "Cas particulier de 4 sur le côté\n\n"
                + "Dans cet exemple, nous pouvons voir une île avec un indice 4 dans la deuxième rangée en partant du bas. Bien que le 4 ne soit pas du côté du puzzle, d'un point de vue logique on peut le traiter comme tel car il n'a de voisins que sur trois côtés."
                + "Comme il n'est pas permis de construire plus de deux ponts dans la même direction, toutes les conditions sont remplies et quatre ponts peuvent être tracés : deux ponts vers l'île située au-dessus et un pont vers chacune des îles situées sur les côtés.";               
                imagePath1 = "/hashiGRP3/images/1320.gif";
                imagePath2 = "/hashiGRP3/images/1321.gif";
                break;
            case "6":
                text = "Cas particulier de 6 au milieu\n\n"
                + "Supposons que l’île avec l’indice 6 soit connectée à l’île avec l’indice 1. Il reste donc cinq ponts, ce qui signifie qu'il doit y avoir au moins un pont relié aux îles A, B et C."
                + "Supposons maintenant que l'île avec l'indice 6 ne soit pas reliée à l'île avec l'indice 1. Dans ce cas, exactement deux ponts doivent être reliés aux îles A, B et C. Ainsi, que l'île avec l'indice 6 soit reliée ou non à l'île avec l'indice 1, il doit y avoir au moins un pont relié aux îles A, B et C.";               
                imagePath1 = "/hashiGRP3/images/1322.gif";
                imagePath2 = "/hashiGRP3/images/1323.gif";
                break;
            case "7":
                text = "Cas particulier de 6 au milieu\n\n"
                + "Regardons l’île du bas avec l’indice 1 dans la colonne de droite. Si nous le relions à l'autre île avec l'indice 1 comme indiqué dans le diagramme de gauche, alors les deux îles deviendront un segment isolé qui n'est pas autorisé selon les règles de Hashi. La seule autre possibilité est donc de le relier à l’île A. Pour la même raison, l'île avec l'indice 2 dans le coin inférieur gauche ne peut pas être reliée aux deux ponts menant à l'île de sa droite comme le montre le schéma de gauche. Cela signifie qu'au moins un pont doit être relié de l'île située dans le coin inférieur gauche à l'île B.";
                imagePath1 = "/hashiGRP3/images/1324.gif";
                imagePath2 = "/hashiGRP3/images/1325.gif";
                break;
            case "8":
                text = "Isolement d'un segment de trois îles\n\n"
                + "La technique ci-dessus peut être étendue à des segments comportant trois îles. L'île avec l'indice 2 dans la rangée du bas ne peut pas être reliée à deux ponts comme indiqué dans le diagramme de gauche car elle deviendrait alors un segment 1-2-1 isolé. Cela signifie qu'il doit y avoir au moins un pont vers l'île A. De même, l'île avec l'indice 3 dans la colonne de gauche ne peut pas être reliée à trois ponts comme indiqué dans le diagramme de gauche car elle deviendrait alors un segment 1-3-2 isolé. Cela signifie qu'il doit y avoir au moins un pont vers l'île B.";
                imagePath1 = "/hashiGRP3/images/1326.gif";
                imagePath2 = "/hashiGRP3/images/1327.gif";
                break;
            case "9":
                text = "Isolement lorsqu'un segment se connecte à une île\n\n"
                + "Parfois, des segments d’îles beaucoup plus longs peuvent devenir isolés, créant des situations logiques plus difficiles à trouver et plus intéressantes à résoudre. Regardons l’exemple dans le diagramme de gauche. Ce que nous voyons est un segment composé de sept îles, où six des îles sont terminées et seule l'île avec l'indice 3 dans la rangée supérieure a un pont manquant. Si nous relions ce pont à l'île dans le coin supérieur gauche selon la ligne rouge, nous nous retrouverons alors avec un segment isolé. Ce pont doit donc être relié à l'île par l'indice 5 comme indiqué sur le schéma de droite.";
                imagePath1 = "/hashiGRP3/images/1328.gif";
                imagePath2 = "/hashiGRP3/images/1329.gif";
                break;
            case "10":
                text = "Isolement lorsqu'un segment se connecte à un autre segment\n\n"
                + "Dans le diagramme de gauche, nous avons deux segments, l'un composé de quatre îles où toutes les îles sont complétées sauf l'île avec l'indice 6, et l'autre composé de huit îles où toutes les îles sont également complétées sauf l'île avec l'indice 3. Dans les deux segments, il manque exactement deux ponts aux îles inachevées. Maintenant, si nous devions relier deux ponts entre ces deux segments selon les lignes rouges, nous nous retrouverions avec un long segment isolé. On peut donc en déduire qu'au moins un pont doit être relié à l'îlot supérieur gauche comme le montre le schéma de droite.";
                imagePath1 = "/hashiGRP3/images/1330.gif";
                imagePath2 = "/hashiGRP3/images/1331.gif";
                break;
            case "11":
                text = "Isoler un segment en bloquant un pont\n\n"
                + "Si nous supposons qu'aucun pont n'est connecté dans la direction du X rouge dans le diagramme de gauche, alors les cinq îles doivent être connectées selon le diagramme central, créant ainsi un segment isolé. Il doit donc y avoir au moins un pont montant comme indiqué sur le schéma de droite.";
                imagePath1 = "/hashiGRP3/images/1332.gif";
                imagePath2 = "/hashiGRP3/images/1333.gif";
                imagePath3 = "/hashiGRP3/images/1334.gif";
                break;
            case "12":
                text = "Isoler un segment en ajoutant un pont\n\n"
                + "Regardons la rangée supérieure du diagramme de gauche montrant une étape d'une solution de puzzle Hashi. Si nous supposons que le deuxième pont de l'île avec l'indice 2 est connecté à l'île sur sa droite, alors le segment de six îles sera isolé comme indiqué dans le diagramme central. Cette île doit donc être connectée comme indiqué sur le schéma de droite. De même, l’île située dans le coin inférieur gauche doit être reliée à l’île située au-dessus.";
                imagePath1 = "/hashiGRP3/images/1335.gif";
                imagePath2 = "/hashiGRP3/images/1336.gif";
                imagePath3 = "/hashiGRP3/images/1337.gif";
                break;
            case "13":
                text = "Isoler une île avec des ponts\n\n"
                + "Regardons l’île avec l’indice 2 dans le diagramme de gauche. Si nous supposons qu'il n'y a pas de pont dans la direction du X rouge, alors les deux ponts doivent être connectés comme indiqué dans le diagramme central. Cependant, cela entraînera l'isolement de l'île avec l'indice 1 car la seule autre île à laquelle elle peut se connecter est déjà terminée. Il doit donc y avoir au moins un pont descendant comme le montre le schéma de droite.";
                imagePath1 = "/hashiGRP3/images/1338.gif";
                imagePath2 = "/hashiGRP3/images/1339.gif";
                imagePath3 = "/hashiGRP3/images/1340.gif";
                break;
            case "14":
                text = "Créer des conflits de connexion entre les ponts \n\n"
                + "L'île avec l'indice 1 dans la deuxième rangée peut être reliée dans deux directions. Supposons qu'il soit relié à l'île à l'extrême droite comme le montre le schéma de gauche. Cela conduit à un segment de quatre îles représenté sur le diagramme central. Cependant, l'île avec l'indice 2 possède toujours un pont non connecté créant un conflit ! Par conséquent, l'île avec l'indice 1 dans la deuxième rangée doit être reliée à l'île située en dessous, comme le montre le schéma de droite.";
                imagePath1 = "/hashiGRP3/images/1341.gif";
                imagePath2 = "/hashiGRP3/images/1342.gif";
                imagePath3 = "/hashiGRP3/images/1343.gif";
                break;
        
        }

        
        mainContentArea.setText(text);

        
        addImageToContainer(imagePath1);
        addImageToContainer(imagePath2);
        addImageToContainer(imagePath3);
    }

    
    private void addImageToContainer(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                System.out.println("Could not find image: " + path);
                return;
            }
            Image img = new Image(is);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(200); 
            imageView.setPreserveRatio(true);
            
            imageContainer.getChildren().add(imageView);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public void setSceneManager(SceneManager sm) {
                this.sceneManager = sm;
        }

        @FXML
        private void changeScene(ActionEvent event) {
                Button btn = (Button) event.getSource();
                String sceneName = (String) btn.getUserData();
                if (sceneManager != null && sceneName != null) {
                        sceneManager.changeScene(sceneName);
                }
        }

        @FXML
        private void quitApp() {
                System.exit(0);
        }


}
