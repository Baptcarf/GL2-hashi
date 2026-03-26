package hashiGRP3.Logic.Aide.Techniques;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T9 - Isolement lorsqu'un segment se connecte à une île
 *
 * Pour chaque île X non complète, on examine ses ponts VIDES possibles.
 * Pour chaque pont candidat X→Y, on simule son ajout et on teste si
 * la composante connexe résultante serait isolée du reste du jeu
 * (c'est-à-dire : toutes les îles du jeu ne sont pas dans cette composante,
 * ET la composante est "fermée" : sum(indices) == 2 * ponts_internes).
 *
 * Si poser X→Y crée un tel isolement → ce pont est INTERDIT.
 * Si parmi tous les ponts possibles de X, un seul n'est pas interdit → il est OBLIGATOIRE.
 */
public class TechniqueIsolationSegmentIle extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isolement lorsqu'un segment se connecte à une île";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.DIFFICILE;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Construit la composante connexe d'une île de départ en suivant
     * les ponts déjà posés. Si pontSupplementaire != null, on l'inclut
     * virtuellement dans le parcours (simulation sans modifier l'état).
     */
    private Set<Ile> construireComposante(Ile depart, Pont pontSupplementaire) {
        Set<Ile> composante = new HashSet<>();
        List<Ile> file = new ArrayList<>();
        file.add(depart);
        while (!file.isEmpty()) {
            Ile courante = file.remove(0);
            if (!composante.add(courante)) continue;
            for (Pont pont : getVoisin(courante)) {
                boolean estPose = pont.getEtatActuel() != EtatDuPont.VIDE;
                boolean estSimule = pont.equals(pontSupplementaire);
                if (!estPose && !estSimule) continue;
                Ile voisin = pont.getileA().equals(courante) ? pont.getileB() : pont.getileA();
                if (!composante.contains(voisin)) file.add(voisin);
            }
        }
        return composante;
    }

    /**
     * Calcule la somme des ponts posés (SIMPLE=1, DOUBLE=2) internes à un groupe,
     * en comptant optionnellement un pont supplémentaire virtuel (simulation).
     */
    private int nbPontsInternes(Set<Ile> groupe, Pont pontSupplementaire) {
        Set<Pont> dejaComptes = new HashSet<>();
        int total = 0;
        for (Ile ile : groupe) {
            for (Pont pont : getVoisin(ile)) {
                boolean estPose = pont.getEtatActuel() != EtatDuPont.VIDE;
                boolean estSimule = pont.equals(pontSupplementaire);
                if (!estPose && !estSimule) continue;
                Ile autre = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();
                if (groupe.contains(autre) && dejaComptes.add(pont)) {
                    if (estSimule) {
                        total += 1; // pont simulé compté comme SIMPLE
                    } else {
                        total += pont.getEtatActuel() == EtatDuPont.DOUBLE ? 2 : 1;
                    }
                }
            }
        }
        return total;
    }

    /**
     * Teste si poser le pont X→Y crée une composante isolée :
     *   1. On construit la composante connexe depuis X en incluant ce pont.
     *   2. Cette composante ne contient PAS toutes les îles du jeu.
     *   3. sum(indices de la composante) == 2 * ponts_internes(composante, avec ce pont).
     * Si ces 3 conditions sont vraies → le pont créerait un isolement → INTERDIT.
     */
    private boolean creeeIsolement(Pont pont, Ile ileX, Hashi hashi) {
        Set<Ile> composante = construireComposante(ileX, pont);

        // Si la composante contient toutes les îles → pas d'isolement possible
        if (composante.size() == hashi.getIles().size()) return false;

        // Vérifier si la composante est fermée : sum == 2 * internes
        int somme = composante.stream().mapToInt(Ile::getNbPontsRequis).sum();
        int internes = nbPontsInternes(composante, pont);
        return somme == 2 * internes;
    }

    // -------------------------------------------------------------------------
    // Détection principale
    // -------------------------------------------------------------------------

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        Set<Set<Ile>> groupesDejaTraites = new HashSet<>();

        for (Ile ileX : hashi.getIles()) {
            if (ileX.estComplet()) continue;

            // Groupe connexe actuel de ileX (ponts déjà posés)
            Set<Ile> groupe = construireComposante(ileX, null);
            if (!groupesDejaTraites.add(groupe)) continue;

            // Collecter tous les ponts VIDES depuis les îles non complètes du groupe
            List<Pont> tousLesPonts = new ArrayList<>();
            for (Ile ile : groupe) {
                if (ile.estComplet()) continue;
                for (Pont pont : getVoisin(ile)) {
                    if (pont.getEtatActuel() == EtatDuPont.VIDE) {
                        tousLesPonts.add(pont);
                    }
                }
            }

            // Éliminer les ponts qui créeraient un isolement
            List<Pont> pontsValides = new ArrayList<>();
            for (Pont pont : tousLesPonts) {
                Ile source = groupe.contains(pont.getileA()) ? pont.getileA() : pont.getileB();
                if (!creeeIsolement(pont, source, hashi)) {
                    pontsValides.add(pont);
                }
            }

            // Un seul pont valide → il est obligatoire
            if (pontsValides.size() == 1) {
                Pont pontObligatoire = pontsValides.get(0);
                Ile ileSource = groupe.contains(pontObligatoire.getileA())
                        ? pontObligatoire.getileA()
                        : pontObligatoire.getileB();

                String explication = String.format(
                    "Depuis le groupe d'îles connectées à (%d, %d), tous les ponts  " +
                    "possibles sauf un créeraient un groupe isolé du reste du puzzle.  " +
                    "Le pont depuis (%d, %d) est donc obligatoire.",
                    ileSource.getCoordonnees().x, ileSource.getCoordonnees().y,
                    ileSource.getCoordonnees().x, ileSource.getCoordonnees().y
                );

                return Optional.of(new IndiceResultat(
                    getNom(),
                    explication,
                    getNiveauDifficulte(),
                    Optional.of(pontObligatoire),
                    Optional.of(EtatDuPont.SIMPLE),
                    false,
                    new ArrayList<>(groupe),
                    List.of(pontObligatoire)
                ));
            }
        }

        return Optional.empty();
    }
}