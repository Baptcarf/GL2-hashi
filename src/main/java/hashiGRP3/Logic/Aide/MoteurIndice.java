package hashiGRP3.Logic.Aide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class MoteurIndice {
    private final List<TechniqueIndice> techniques;

    /**
     * Instancie le moteur avec la liste ordonnée de techniques.
     * @param techniques Liste des techniques à utiliser (ordre croissant de difficulté)
     */
    public MoteurIndice(List<TechniqueIndice> techniques) {
        // Tri par difficulté croissante
        this.techniques = new ArrayList<>(techniques);
        Collections.sort(this.techniques, Comparator.comparing(TechniqueIndice::getNiveauDifficulte));
    }

    /**
     * Propose le premier indice applicable sur la grille.
     * @param hashi Grille de jeu
     * @return Une option de IndiceResultat: premier indice trouvé ou empty si aucun
     */
    public Optional<IndiceResultat> proposerProchainIndice(Hashi hashi) {
        for (TechniqueIndice technique : techniques) {
            Optional<IndiceResultat> res = technique.evaluer(hashi);
            if (res.isPresent()) {
                IndiceResultat ir = res.get();
                if (shouldAccept(ir, hashi)) {
                    return res;
                } else {
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Décide d'accepter ou non un indice négatif
     * Un indice négatif (estInterdit == true) ne doit être proposé que si le pont concerné
     * est actuellement dans l'état interdit (état suggéré). Cela centralise la règle :
     * ne proposer un indice négatif que lorsque l'état interdit est effectivement présent
     *
     * @param ir    L'IndiceResultat à évaluer (potentiellement négatif)
     * @param hashi La grille courante pour vérifier l'état réel du pont
     * @return true si l'indice négatif doit être proposé, false sinon
     */
    private boolean shouldAccept(IndiceResultat ir, Hashi hashi) {
        if (!ir.isEstInterdit()) {
            return true;
        }

        if (ir.getPontSuggere().isEmpty() || ir.getEtatSuggere().isEmpty()) {
            return true;
        }

        Pont pontSug = ir.getPontSuggere().get();
        EtatDuPont etatSug = ir.getEtatSuggere().get();

        Ile a = hashi.getIle(pontSug.getileA().getCoordonnees().x, pontSug.getileA().getCoordonnees().y);
        Ile b = hashi.getIle(pontSug.getileB().getCoordonnees().x, pontSug.getileB().getCoordonnees().y);
        if (a == null || b == null) {
            return false;
        }

        Pont current = hashi.getPont(a, b);
        if (current == null) {
            return false;
        }

        return current.getEtatActuel() == etatSug;
    }
}
