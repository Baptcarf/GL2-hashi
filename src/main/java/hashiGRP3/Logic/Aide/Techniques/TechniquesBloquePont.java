//Attribut au paquet
package hashiGRP3.Logic.Aide.Techniques;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.GraphUtils;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.General;
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
        List<Ile> iles = hashi.getIles();

        for (Ile ile : iles) {
            for (Pont pont : getVoisin(ile)) {
                if (pont.getEtatActuel() != EtatDuPont.VIDE)
                    continue;

                // Cloner le plateau pour tester l'ajout
                Hashi simu = GraphUtils.clonerHashi(hashi);
                Ile ileSimuA = simu.getIle(ile.getCoordonnees().x, ile.getCoordonnees().y);
                Ile ileSimuB = (pont.getileA() == ile ? pont.getileB() : pont.getileA());
                Pont pontSimu = simu.getPont(ileSimuA, simu.getIle(ileSimuB.getCoordonnees().x,
                        ileSimuB.getCoordonnees().y));

                if (pontSimu.estCorrect()) {
                    continue;
                }

                // Simuler le pont
                pontSimu.setEtatActuel(EtatDuPont.INTERDIT);

                if (!GraphUtils.estConnexe(simu)) {
                    // pont obligatoire

                    // Ce pont isole des îles → donc il n'est pas possible
                    // On peut renvoyer un indice ou marquer les ponts forcés
                    String explication = String.format(
                            "L'île en (%d, %d) a forcment un pont obligatoire pour éviter l'isolation.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y);
                    return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(pont),
                            Optional.of(EtatDuPont.SIMPLE),
                            false,
                            List.of(ile),
                            List.of(pont)));
                }
            }
        }

        return Optional.empty();
    }
}
