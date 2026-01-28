//Attribute to package
package hashiGRP3;

//Libs
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

//Driver program
public class App extends Application {

	@Override
	public void start(final Stage primaryStage) {
		SceneManager sn = new SceneManager(primaryStage);

		sn.addScene("menu");
		sn.changeScene("menu");

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
