//Attribut au paquet
package hashiGRP3.Logic.Aide.Techniques;



//Imports
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
 * Technique T5  Saturation moins un - voisin contraint
 *
 * Condition : nbPontsRequis == nbVoisins * 2 - 1
 *             ET un des voisins a un indice de 1
 * Si une île a exactement un pont de moins que le double de son nombre de voisins
 * et que l'un de ses voisins est contraint à 1 pont,
 * alors tous les autres voisins DOIVENT recevoir un pont double
 *
 * Retourne le premier pont de l'île vers un voisin non contraint qui n'est pas encore DOUBLE
 */
public class TechniqueSaturationMoinsUnSpe extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Saturation moins un - voisin contraint";
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

            // Condition 1 : saturation moins un
            if (ile.getNbPontsRequis() != nbVoisins * 2 - 1) continue;

            // Condition 2 : un voisin est contraint à 1 pont
            for (Direction dir : Direction.values()) {
                Pont pont = ile.getPont(dir);
                if (pont == null) continue;

                Ile voisin = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();

                if (voisin.getNbPontsRequis() == 1) {
                    // Les autres voisins reçoivent obligatoirement un pont double
                    for (Direction dir2 : Direction.values()) {
                        Pont pont2 = ile.getPont(dir2);
                        if (pont2 != null
                            && !pont2.equals(pont)
                            && pont2.getEtatActuel() == EtatDuPont.VIDE) {

                            String explication = String.format(
                                "L'île en (%d, %d) a un voisin contraint à 1 pont.  "
                                + "Les autres voisins reçoivent obligatoirement un pont double.",
                                ile.getCoordonnees().x, ile.getCoordonnees().y
                            );
                            return Optional.of(new IndiceResultat(
                                getNom(),
                                explication,
                                getNiveauDifficulte(),
                                Optional.of(pont2),
                                Optional.of(EtatDuPont.DOUBLE),
                                false
                            ));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
}
