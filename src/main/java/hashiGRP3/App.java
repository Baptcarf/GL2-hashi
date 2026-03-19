//Attribut au packet
package hashiGRP3;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

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
