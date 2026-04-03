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
 * Technique avancée T3 : Isolation d'une île voisine par blocage de pont.
 *
 * Principe (avec anticipation) :
 * On bloque un pont vide candidat, puis on propage les contraintes forcées
 * (îles n'ayant plus qu'un seul pont disponible). Si, après propagation,
 * une île voisine se retrouve isolée (aucun pont disponible alors qu'elle
 * n'est pas encore satisfaite), le blocage est impossible : le pont doit
 * être posé.
 */
public class TechniqueIsolationIleVoisine extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isolation d'une île voisine par blocage de pont";
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

                // On ne teste que les ponts qui doivent exister dans la solution
                if (pont.getEtatCorrect() == EtatDuPont.VIDE)
                    continue;

                // --- Simulation : cloner la grille et bloquer ce pont ---
                Hashi simu = GraphUtils.clonerHashi(hashi);

                Ile ileSimuA = simu.getIle(
                        pont.getileA().getCoordonnees().x,
                        pont.getileA().getCoordonnees().y);
                Ile ileSimuB = simu.getIle(
                        pont.getileB().getCoordonnees().x,
                        pont.getileB().getCoordonnees().y);

                Pont pontSimu = simu.getPont(ileSimuA, ileSimuB);

                // Bloquer le pont dans la simulation
                pontSimu.setEtatActuel(EtatDuPont.DOUBLE);
                pontSimu.setEtatCorrect(EtatDuPont.VIDE);

                // --- Propagation des contraintes forcées ---
                if (!propagerContraintes(simu)) {
                    // Une île a été isolée pendant la propagation
                    String explication = "Si tu bloques ce pont et que tu laisses la logique se dérouler, "
                            + "une île voisine se retrouve sans connexion possible alors qu'elle "
                            + "n'est pas encore satisfaite. Ce pont est donc obligatoire.";

                    return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(pont),
                            Optional.of(EtatDuPont.SIMPLE),
                            false));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Propage les ponts forcés (îles avec un seul pont disponible) et vérifie
     * qu'aucune île ne se retrouve isolée (ponts insuffisants pour la satisfaire).
     *
     * @param simu la grille clonée sur laquelle travailler
     * @return false si une île est isolée (contradiction), true sinon
     */
    private boolean propagerContraintes(Hashi simu) {
        boolean changement = true;

        while (changement) {
            changement = false;

            for (Ile ile : simu.getIles()) {
                int restants = ile.getNbPontsRequis() - ile.getNbPontsActuels();
                if (restants <= 0)
                    continue; // île déjà satisfaite

                // Ponts encore utilisables pour cette île
                List<Pont> dispo = pontsDisponibles(ile);

                // Aucun pont disponible mais l'île n'est pas satisfaite → conflit
                if (dispo.isEmpty())
                    return false;

                // Capacité totale maximale encore mobilisable
                int capaciteMax = dispo.stream()
                        .mapToInt(p -> p.getEtatActuel() == EtatDuPont.SIMPLE ? 1 : 2)
                        .sum();

                // Pas assez de capacité pour satisfaire l'île → conflit
                if (capaciteMax < restants)
                    return false;

                // Un seul pont disponible : il doit absorber tous les ponts restants
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
