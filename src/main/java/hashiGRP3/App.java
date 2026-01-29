//Attribut au packet
package hashiGRP3;

/* Libs */
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

/* Point de démarrage de l'application */
public class App extends Application {

	private static SceneManager sn;

	@Override
	public void start(final Stage primaryStage) {
		sn = new SceneManager(primaryStage);

		sn.addScene("option");
		sn.addScene("accueil");
		sn.addScene("connexion");
		sn.addScene("technique");

		sn.changeScene("connexion");

	}

	public static void main(String[] args) {
		launch(args);
	}
}
