//Attribut au paquet
package hashiGRP3.Logic.Aide.Techniques;



//Imports
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T1  Saturation totale
 *
 * Condition : nbPontsRequis == nbVoisins * 2
 * Si une île a exactement autant de ponts requis que le double de son nombre de voisins
 * alors chaque voisin DOIT recevoir un pont double
 * 
 * Retourne le premier pont de l'île qui n'est pas encore DOUBLE
 */
public class TechniqueSaturation extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Saturation totale";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.FACILE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet()) continue;

            int nbVoisins = getNbVoisinsTotal(ile);
            if (nbVoisins == 0) continue;

            // L'ile est saturée si elle a besoin de exactement 2 ponts par voisin
            if (ile.getNbPontsRequis() == nbVoisins * 2) {
                for (Direction dir : Direction.values()) {
                    Pont pont = ile.getPont(dir);
                    if (pont != null && pont.getEtatActuel() != EtatDuPont.DOUBLE) {
                        String explication = String.format(
                            "L'île en (%d, %d) requiert %d pont(s) et a %d voisin(s).  "
                            + "Elle doit obligatoirement avoir un pont double vers chacun d'eux.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y,
                            ile.getNbPontsRequis(), nbVoisins
                        );
                        return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(pont),
                            Optional.of(EtatDuPont.DOUBLE),
                            false,
                            List.of(ile),
                            List.of(pont)
                        ));
                    }
                }
            }
        }
        return Optional.empty();
    }
}
