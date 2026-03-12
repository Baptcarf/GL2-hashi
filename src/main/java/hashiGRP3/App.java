//Attribut au packet
package hashiGRP3;

//Imports
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Path;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.General;

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
		sn.addScene("selectGrille");
		sn.addScene("selectTutoriel");
		sn.addScene("grilledujeu");

		sn.changeScene("connexion");
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
