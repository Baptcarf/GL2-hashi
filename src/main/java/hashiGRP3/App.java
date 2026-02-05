package hashiGRP3;

import java.io.IOException;
import java.nio.file.Path;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Pont;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException  {
        Path chemin = Path.of("src/main/java/hashiGRP3/Ressources/7x7/hashi1.txt");

        Hashi hashi = Import.chargerFichier(chemin);
        hashi.initialisationToutLesConflits();

        for (var pont : hashi.getPonts()) {
            System.out.println("Pont : " + pont);
            System.out.println("Conflits : " + pont.getConflits());
            System.out.println();
        }
        System.out.println(hashi.getIle(2, 5).getPont(Direction.HAUT).getConflits());
        hashi.afficherPlateau();

        Ile ile = new Ile(new Coordonnees(2, 5), 2);
        Ile ile2 = new Ile(new Coordonnees(2, 0), 2);
        Pont pont = new Pont(ile, ile2, EtatDuPont.VIDE);
        System.out.println(pont);
    }

}
