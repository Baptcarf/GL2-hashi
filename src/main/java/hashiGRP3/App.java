package hashiGRP3;

import java.io.IOException;
import java.nio.file.Path;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
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

        for (var pont : hashi.getPonts()) {
            System.out.println("Pont : " + pont);
            System.out.println("Conflits : " + pont.getConflits());
            System.out.println();
        }
        hashi.afficherPlateau();

        System.out.println("Pont est possible " + hashi.getIle(3,0).getPont(Direction.BAS).pontEstPossible());
        hashi.getIle(1,1).getPont(Direction.DROITE).setEtatActuel(EtatDuPont.SIMPLE);

        hashi.afficherPlateau();
        System.out.println("Pont est possible " + hashi.getIle(3,0).getPont(Direction.BAS).pontEstPossible());

    }

}
