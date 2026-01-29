package hashiGRP3;

import java.io.IOException;
import java.nio.file.Path;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.InOut.Import;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException  {
        Path chemin = Path.of("src/main/java/hashiGRP3/Ressources/7x7/hashi2.txt");

        Hashi hashi = Import.chargerFichier(chemin);
        hashi.initialisationToutLesPonts();
        hashi.conflictPont();

        System.out.println(hashi.getIle(1,1));
        
    }
}
