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
		sn.addScene("selectTutoriel");
		sn.addScene("grilledujeu");

		sn.changeScene("connexion");

	}

	public static void main(String[] args) throws IOException {
		//Menu
		launch(args);
		
		//Logic
		Path chemin = Path.of("src/main/java/hashiGRP3/Ressources/7x7/hashi2.txt");

		Hashi hashi = Import.chargerFichier(chemin);
		hashi.initialisationToutLesConflits();

		hashi.toString();

		var pont = hashi.getIle(1, 1).getPont(Direction.DROITE);

		System.out.println("\nJouer: Pose un pont simple");
		hashi.jouer(pont);
		System.out.println(hashi.toString());

		System.out.println("\n Jouer: Pose un pont double ");
		hashi.jouer(pont);
		System.out.println(hashi.toString());

		System.out.println("\n UNDO Retour au pont simple ");
		hashi.undo();
		System.out.println(hashi.toString());

		System.out.println("\n UNDO Retour à l'état vide ");
		hashi.undo();
		System.out.println(hashi.toString());

		System.out.println("\n REDO Rétablit le pont simple ");
		hashi.redo();
		System.out.println(hashi.toString());
	}
}
