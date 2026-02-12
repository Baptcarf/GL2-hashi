//Attribut au packet
package hashiGRP3.Scene;



//Imports
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.Optional;

import hashiGRP3.DatabaseManager;



/* Class */
public class CreerUtilisateur {

    public record Result(String pseudo, Color color, boolean isNewPlayer) {
    }

    private final TextField pseudoField = new TextField();
    private final ColorPicker cpField = new ColorPicker(Color.WHITE);
    private final CheckBox cbField = new CheckBox();
    private final Label messageLabel = new Label();

    public Optional<Result> showAndWait(Window parent, DatabaseManager db) {

        // Creer la fenetre
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent);
        stage.setTitle("Créer un compte");

        // Init la grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Composant
        cbField.setSelected(true);
        messageLabel.setTextFill(Color.RED);
        Button btn = new Button("Commencer l'aventure");

        // Setup la grid
        grid.addRow(0, new Label("Pseudo:"), pseudoField);
        grid.addRow(1, new Label("Couleur du compte:"), cpField);
        grid.addRow(2, new Label("Je n'ai jamais joué au Hashi : "), cbField);
        grid.add(btn, 0, 3, 2, 1);
        grid.add(messageLabel, 0, 4, 2, 1);

        final Result[] resultHolder = new Result[1];

        // Handler
        btn.setOnAction(ev -> {
            String pseudo = pseudoField.getText();

            if (pseudo.isEmpty()) {
                messageLabel.setText("Erreur : Pseudo non renseigné");
                return;
            }

            if (db.userExist(pseudo)) {
                messageLabel.setText("Erreur : username déjà pris");
                return;

            }

            resultHolder[0] = new Result(
                    pseudo,
                    cpField.getValue(),
                    cbField.isSelected());

            stage.close();
        });

        //
        Scene sc = new Scene(grid, 400, 400);
        stage.setScene(sc);
        stage.showAndWait();

        return Optional.ofNullable(resultHolder[0]);
    }
}
