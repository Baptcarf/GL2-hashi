//Attribut au paquet
package hashiGRP3.Logic.Aide.Techniques;



/* Imports */
import java.util.ArrayList;
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
 * Technique T2 Isolation des iles
 *
 * Condition : nbPontsRequis == 1 ou 2
 * si une ile à 1 ou 2 pont avec un seul voisin
 * 
 * Retourne le premier pont de l'île qui n'est pas encore DOUBLE
 */
public class TechniqueIsolation extends AbstractTechnique {

    @Override
    public String getNom() {
        return "ile isolé";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.FACILE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet())
                continue;

            ArrayList<Pont> voisin = getVoisin(ile);
            int nbVoisins = 0;

            for (Pont p : voisin) {
                if (p.pontEstPossible() || !p.estCorrect()) {
                    nbVoisins++;
                }
            }

            if (nbVoisins != 1)
                continue;

            // L'ile est isolé si elle a besoin de au plus 2 ponts pour son seul voisin
            if (ile.getNbPontsRequis() <= 2) {
                for (Direction dir : Direction.values()) {
                    Pont pont = ile.getPont(dir);
                    if (pont != null && pont.getEtatActuel() == EtatDuPont.VIDE) {
                        String explication = String.format(
                                "Il y a une ile sur la grille qui a 1 voisin et qui a besoin de %d pont(s). ",
                                ile.getNbPontsRequis());
                        return Optional.of(new IndiceResultat(
                                getNom(),
                                explication,
                                getNiveauDifficulte(),
                                Optional.of(pont),
                                Optional.of(EtatDuPont.DOUBLE),
                                false,
                                List.of(ile),
                                List.of(pont)));
                    }
                }
            }
        }
        return Optional.empty();
    }
}
