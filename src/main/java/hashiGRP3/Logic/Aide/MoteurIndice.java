package hashiGRP3.Logic.Aide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import hashiGRP3.Logic.Hashi;

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
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Propose un indice en limitant la difficulté maximale.
     * @param hashi Grille de jeu
     * @param niveauMax Niveau de difficulté maximal
     * @return Une option IndiceResultat : premier indice trouvé ou empty si aucun
     */
    public Optional<IndiceResultat> proposerIndiceDifficulteMax(Hashi hashi, NiveauDifficulte niveauMax) {
        for (TechniqueIndice technique : techniques) {
            if (technique.getNiveauDifficulte().getValue() > niveauMax.getValue()) {
                continue;
            }
            
            Optional<IndiceResultat> res = technique.evaluer(hashi);
            if (res.isPresent()) {
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Retourne tous les indices applicables sur la grille.
     * @param hashi Grille de jeu
     * @return Liste des indices trouvés
     */
    public List<IndiceResultat> toutesLesTechniquesApplicables(Hashi hashi) {
        List<IndiceResultat> resultats = new ArrayList<>();
        for (TechniqueIndice technique : techniques) {
            Optional<IndiceResultat> res = technique.evaluer(hashi);
            res.ifPresent(resultats::add);
        }
        return resultats;
    }
}
