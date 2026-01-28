//Attribute to package
package hashiGRP3;


//Libs
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;


//Driver program
public class App extends Application {
	
	@Override
  	public void start(final Stage primaryStage) {
    		try {
      			//Localisation du fichier FXML.
      			final URL url = getClass().getResource("/views/menu.fxml");
      			//Création du loader.
      			final FXMLLoader fxmlLoader = new FXMLLoader(url);
      			//Chargement du FXML.
      			final AnchorPane root = (AnchorPane) fxmlLoader.load();
      			//Création de la scène.
      			final Scene scene = new Scene(root, 300, 250);
      			primaryStage.setScene(scene);
    		} catch (IOException ex) {
      			System.err.println("Erreur au chargement: " + ex);
    		}
    		
		primaryStage.setTitle("Test FXML");
    		primaryStage.show();
  	}

  	public static void main(String[] args) {
    		launch(args);
  	}
}
