package hashiGRP3.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class grilleController extends ManageController {

    @FXML
    private VBox sidePanel; 

    

    @FXML
    protected void onHypothesisClick() {
        // 1. Create Title
        Label title = createTitle("Mode Hypothèse");
        
        // 2. Create Description
        Text desc = new Text("lorem ipsum dolor sit amet, consectetur adipiscing");
        desc.setWrappingWidth(180);

        // 3. Create Buttons (Validate / Cancel)
        Button btnValidate = new Button("Valider");
        Button btnCancel = new Button("Annuler");

        // Style the buttons
        btnValidate.setStyle(" -fx-text-fill: #005500;");
        btnCancel.setStyle(" -fx-text-fill: #8b0000;");
        
        btnValidate.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnValidate, Priority.ALWAYS);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        HBox buttonBox = new HBox(10, btnValidate, btnCancel);

        // 4. Update Panel
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

       
        Text hintText = new Text("lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue. Nam tincidunt congue enim, ut porta lorem lacinia consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet, ");
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
