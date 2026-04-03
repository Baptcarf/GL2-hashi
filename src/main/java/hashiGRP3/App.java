//Attribut au packet
package hashiGRP3;

//Imports
import javafx.application.Application;
import javafx.stage.Stage;

/** Point de démarrage de l'application */
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
		sn.addScene("tutodujeu");


		sn.changeScene("connexion");
	}

	/** Démarrage de l'application */
	public static void main(String[] args) {
		launch(args);
	}
}
