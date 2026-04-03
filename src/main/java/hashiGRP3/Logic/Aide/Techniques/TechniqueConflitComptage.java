package hashiGRP3.Logic.Aide.Techniques;

// Imports
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.GraphUtils;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique avancée T4 : Conflit de comptage par ajout de pont.
 *
 * Principe (avec anticipation) :
 * On force l'ajout d'un pont vide candidat (on le pose dans la simulation),
 * puis on propage les contraintes forcées. Si, après propagation, une île
 * se retrouve dans l'impossibilité de satisfaire son compteur (trop peu ou
 * trop de ponts disponibles), alors poser ce pont crée un conflit : il ne
 * doit PAS être posé dans cette direction.
 *
 */
public class TechniqueConflitComptage extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Conflit de comptage par ajout de pont";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.DIFFICILE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {

        for (Ile ile : hashi.getIles()) {
            for (Pont pont : getVoisin(ile)) {

                // On ne teste que les ponts non encore posés
                if (pont.getEtatActuel() != EtatDuPont.VIDE)
                    continue;

                // On ne teste que les ponts qui ne doivent PAS exister dans la solution
                // (on cherche à montrer qu'ajouter ce pont crée un conflit)
                if (pont.getEtatCorrect() != EtatDuPont.VIDE)
                    continue;

                // --- Simulation : cloner la grille et forcer ce pont ---
                Hashi simu = GraphUtils.clonerHashi(hashi);

                Ile ileSimuA = simu.getIle(
                        pont.getileA().getCoordonnees().x,
                        pont.getileA().getCoordonnees().y);
                Ile ileSimuB = simu.getIle(
                        pont.getileB().getCoordonnees().x,
                        pont.getileB().getCoordonnees().y);

                Pont pontSimu = simu.getPont(ileSimuA, ileSimuB);

                // Forcer l'ajout du pont dans la simulation
                pontSimu.setEtatActuel(EtatDuPont.SIMPLE);

                // --- Propagation des contraintes forcées ---
                // Si la propagation détecte un conflit de comptage → ce pont est interdit
                if (!propagerEtVerifierComptage(simu)) {

                    // On cherche un pont alternatif correct à suggérer pour l'île concernée
                    Optional<Pont> pontAlternatif = getVoisin(ile).stream()
                            .filter(p -> p != pont)
                            .filter(p -> p.getEtatActuel() == EtatDuPont.VIDE)
                            .filter(p -> p.getEtatCorrect() != EtatDuPont.VIDE)
                            .findFirst();

                    String explication = "Si tu poses ce pont et que tu laisses la logique se propager, "
                            + "une île voisine ne peut plus atteindre son compteur : trop peu "
                            + "de connexions restantes. Ce pont crée donc un conflit et ne doit "
                            + "pas être posé dans cette direction.";

                    return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            pontAlternatif.isPresent() ? pontAlternatif : Optional.of(pont),
                            Optional.of(EtatDuPont.SIMPLE),
                            false));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Propage les ponts forcés et vérifie l'absence de conflit de comptage.
     * Un conflit survient quand :
     * - une île a trop de ponts actifs par rapport à son compteur (dépassement), ou
     * - une île n'a plus assez de capacité disponible pour atteindre son compteur.
     *
     * @param simu la grille clonée sur laquelle travailler
     * @return false si un conflit de comptage est détecté, true sinon
     */
    private boolean propagerEtVerifierComptage(Hashi simu) {
        boolean changement = true;

        while (changement) {
            changement = false;

            for (Ile ile : simu.getIles()) {
                int actuel = ile.getNbPontsActuels();
                int requis = ile.getNbPontsRequis();
                int restants = requis - actuel;

                // Dépassement du compteur → conflit immédiat
                if (restants < 0)
                    return false;

                if (restants == 0)
                    continue; // île satisfaite, pas de problème

                // Ponts encore mobilisables
                List<Pont> dispo = pontsDisponibles(ile);

                // Capacité totale restante
                int capaciteMax = dispo.stream()
                        .mapToInt(p -> p.getEtatActuel() == EtatDuPont.SIMPLE ? 1 : 2)
                        .sum();

                // Pas assez de capacité pour satisfaire l'île → conflit
                if (capaciteMax < restants)
                    return false;

                // Un seul pont disponible : il est forcé
                if (dispo.size() == 1) {
                    Pont seul = dispo.get(0);
                    if (restants == 1 && seul.getEtatActuel() == EtatDuPont.VIDE) {
                        seul.setEtatActuel(EtatDuPont.SIMPLE);
                        changement = true;
                    } else if (restants >= 2 && seul.getEtatActuel() != EtatDuPont.DOUBLE) {
                        seul.setEtatActuel(EtatDuPont.DOUBLE);
                        changement = true;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Retourne les ponts encore utilisables pour une île donnée :
     * état VIDE ou SIMPLE, et voisin ayant encore de la capacité.
     */
    private List<Pont> pontsDisponibles(Ile ile) {
        return getVoisin(ile).stream()
                .filter(p -> p.getEtatActuel() == EtatDuPont.VIDE
                        || p.getEtatActuel() == EtatDuPont.SIMPLE)
                .filter(p -> {
                    Ile autre = (p.getileA() == ile) ? p.getileB() : p.getileA();
                    int capaciteAutre = autre.getNbPontsRequis() - autre.getNbPontsActuels();
                    int capacitePont = (p.getEtatActuel() == EtatDuPont.SIMPLE) ? 1 : 2;
                    return capaciteAutre > 0 && capacitePont <= capaciteAutre;
                })
                .collect(Collectors.toList());
    }
}
