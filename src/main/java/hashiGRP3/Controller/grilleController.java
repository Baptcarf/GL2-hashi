package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class grilleController extends ManageController {

    @FXML
    private VBox sidePanel; 
    @FXML
    private Pane gamePane;  
    
    private static final int CELL_SIZE = 55; // Distance between islands
    private static final int MARGIN_X = 30;  // Left offset
    private static final int MARGIN_Y = 30;  // Top offset
    private static final int RADIUS = 20;    // Size of the circles

    // --- DATA MODEL (Inner Class) ---
    private static class Island {
        int x, y, value;
        public Island(int x, int y, int value) {
            this.x = x; this.y = y; this.value = value;
        }
    }

    @FXML
    public void initialize() {
        System.out.println("GrilleController initialized.");
        List<Island> islands = load_grille();
        drawGrid(islands);
    }

    // --- GRID DRAWING LOGIC ---
    private void drawGrid(List<Island> islands) {
        if (gamePane == null) {
            System.err.println("ERREUR");
            return;
        }

        gamePane.getChildren().clear();

        for (Island island : islands) {
            double pixelX = (island.y - 1) * CELL_SIZE + MARGIN_X;
            double pixelY = (island.x - 1) * CELL_SIZE + MARGIN_Y;

            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            Label label = new Label(String.valueOf(island.value));
            label.setFont(Font.font("System", FontWeight.BOLD, 18));

            StackPane group = new StackPane(circle, label);
            group.setLayoutX(pixelX);
            group.setLayoutY(pixelY);

            group.setOnMouseEntered(e -> {
                circle.setFill(Color.LIGHTYELLOW);
                gamePane.getScene().setCursor(javafx.scene.Cursor.HAND);
            });
            group.setOnMouseExited(e -> {
                circle.setFill(Color.WHITE);
                gamePane.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
            });
            gamePane.getChildren().add(group);
        }
    }

    private List<Island> load_grille() {
                List<Island> list = new ArrayList<>();
                list.add(new Island(1, 1, 4));
                list.add(new Island(1, 5, 3));
                list.add(new Island(1, 7, 2)); 
                list.add(new Island(3, 1, 5)); 
                list.add(new Island(3, 4, 4));
                list.add(new Island(5, 1, 5));
                list.add(new Island(5, 5, 2)); 
                list.add(new Island(6, 2, 1)); 
                list.add(new Island(6, 6, 3)); 
                list.add(new Island(7, 1, 2)); 
                list.add(new Island(7, 4, 1)); 
                list.add(new Island(7, 7, 2));
                return list;
    }


    // --- SIDE PANEL LOGIC ---

    @FXML
    protected void onHypothesisClick() {
        Label title = createTitle("Mode Hypothèse");
        
        Text desc = new Text("lorem ipsum dolor sit amet, consectetur adipiscing");
        desc.setWrappingWidth(180);

        Button btnValidate = new Button("Valider");
        Button btnCancel = new Button("Annuler");

        btnValidate.setStyle(" -fx-text-fill: #005500;");
        btnCancel.setStyle(" -fx-text-fill: #8b0000;");
        
        btnValidate.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnValidate, Priority.ALWAYS);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, btnValidate, btnCancel);

        updateSidePanel(title, desc, buttonBox);
    }

    @FXML
    protected void onCheckClick() {
        Label title = createTitle("Vérification");
        
        Label status = new Label("Il y a X erreurs sur la grille.");
        status.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Text question = new Text("Voulez-vous revenir au dernier état sans erreur ?");
        question.setWrappingWidth(180);

        Button btnYes = new Button("Oui");
        Button btnNo = new Button("Non");
        
        btnYes.setStyle("-fx-base: #f0f0f0;");
        btnNo.setStyle("-fx-base: #f0f0f0;");
        btnYes.setPrefWidth(80);
        btnNo.setPrefWidth(80);

        HBox actionBox = new HBox(10, btnYes, btnNo);

        updateSidePanel(title, status, new Separator(), question, actionBox);
    }

    @FXML
    protected void onHintClick() {
        Label title = createTitle("Indice");

        Text hintText = new Text("lorem ipsum dolor sit amet, consectetur adipiscing elit...");
        hintText.setWrappingWidth(180);

        updateSidePanel(title, new Separator(), hintText);
    }


    private void updateSidePanel(javafx.scene.Node... nodes) {
        sidePanel.getChildren().clear(); 
        sidePanel.getChildren().addAll(nodes); 
    }

    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        label.setStyle("-fx-text-fill: #333333;");
        return label;
    }
}