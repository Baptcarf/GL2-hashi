package hashiGRP3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import hashiGRP3.Logic.InOut.Import;
import hashiGRP3.Logic.Hashi;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException  {
        Path chemin = Path.of(
            "C:\\Users\\ETU\\Desktop\\GL2-hashi\\src\\main\\java\\hashiGRP3\\Ressources\\7x7\\hashi1.txt"
        );

        Hashi hashi = Import.chargerFichier(chemin);
    }
}
