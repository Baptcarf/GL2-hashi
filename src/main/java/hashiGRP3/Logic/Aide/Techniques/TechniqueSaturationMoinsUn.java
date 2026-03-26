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
 * Technique T4  Saturation moins un
 *
 * Condition : nbPontsRequis == nbVoisins * 2 - 1
 * Si une île a exactement un pont de moins que le double de son nombre de voisins
 * alors chaque voisin DOIT recevoir au moins un pont simple
 * (un voisin recevra un pont simple, les autres un pont double, mais on ne sait pas lequel)
 *
 * Retourne le premier pont de l'île qui n'est pas encore SIMPLE
 */
public class TechniqueSaturationMoinsUn extends AbstractTechnique{

    @Override
    public String getNom() {
        return "Saturation moins un";
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
            // L'île a exactement un pont de moins que la saturation totale (2 ponts par voisin)
            if (ile.getNbPontsRequis() == nbVoisins * 2 - 1) {
                for (Direction dir : Direction.values()) {
                    Pont pont = ile.getPont(dir);
                    if (pont != null && pont.getEtatActuel() == EtatDuPont.VIDE) {
                        String explication = String.format(
                            "L'île en (%d, %d) requiert %d pont(s) et a %d voisin(s). "
                            + "Elle doit obligatoirement avoir au moins un pont simple vers chacun d'eux.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y,
                            ile.getNbPontsRequis(), nbVoisins
                        );
                        return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(pont),
                            Optional.of(EtatDuPont.SIMPLE),
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
