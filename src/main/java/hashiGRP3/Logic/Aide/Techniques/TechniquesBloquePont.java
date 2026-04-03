//Attribut au paquet
package hashiGRP3.Logic.Aide.Techniques;

// Imports
import java.util.Optional;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.GraphUtils;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T *
 * TODO
 */
public class TechniquesBloquePont extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isoler un segment en bloquant un pont";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.DIFFICILE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            for (Pont pont : getVoisin(ile)) {
                if (pont.estCorrect())
                    continue;

                if (!GraphUtils.estConnexe(hashi, pont)) {
                    String explication = String.format(
                            "L'île en (%d, %d) a forcément un pont obligatoire pour éviter l'isolation.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y);
                    return Optional.of(new IndiceResultat(
                            getNom(), explication, getNiveauDifficulte(),
                            Optional.of(pont), Optional.of(EtatDuPont.SIMPLE),
                            false));
                }
            }
        }
        return Optional.empty();
    }

}
