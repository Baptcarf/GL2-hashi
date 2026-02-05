//Attribut au packet
package hashiGRP3;



//Imports
import javafx.application.Application;
import javafx.stage.Stage;



/* Point de démarrage de l'application */
public class App extends Application {

	private static SceneManager sn;

	@Override
	public void start(final Stage primaryStage) {

		DatabaseManager db = new DatabaseManager();
		db.init();

		sn = new SceneManager(primaryStage, db);

		sn.addScene("option");
		sn.addScene("accueil");
		sn.addScene("connexion");
		sn.addScene("technique");
		sn.addScene("selectGrille");

		sn.changeScene("connexion");

	}

	public static void main(String[] args) {
		launch(args);
	}
}
