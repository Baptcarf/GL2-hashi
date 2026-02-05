package hashiGRP3;

import java.io.IOException;
import java.nio.file.Path;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.InOut.Import;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException  {
        Path chemin = Path.of("src/main/java/hashiGRP3/Ressources/7x7/hashi2.txt");

        Hashi hashi = Import.chargerFichier(chemin);
        hashi.initialisationToutLesConflits();

        hashi.afficherPlateau();

        var pont = hashi.getIle(1, 1).getPont(Direction.DROITE);

        System.out.println("\nJouer: Pose un pont simple");
        hashi.jouer(pont);
        hashi.afficherPlateau();

        System.out.println("\n Jouer: Pose un pont double ");
        hashi.jouer(pont);
        hashi.afficherPlateau();

        System.out.println("\n UNDO Retour au pont simple ");
        hashi.undo();
        hashi.afficherPlateau();

        System.out.println("\n UNDO Retour à l'état vide ");
        hashi.undo();
        hashi.afficherPlateau();

        System.out.println("\n REDO Rétablit le pont simple ");
        hashi.redo();
        hashi.afficherPlateau();
    }
}
