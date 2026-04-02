//Attribut au paquet
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
 * Technique T2 Isolation des iles
 *
 * Condition : nbPontsRequis == 1 ou 2
 * si une ile à 1 ou 2 pont avec un seul voisin
 * 
 * Retourne le premier pont de l'île qui n'est pas encore DOUBLE
 */
public class TechniqueIsolationIle extends AbstractTechnique {

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
            System.out.println(ile);
            System.out.println(getVoisin(ile));
            for (Pont pont : getVoisin(ile)) {
                if (pont.getEtatActuel() != EtatDuPont.VIDE)
                    continue;

                if (pont.getEtatCorrect() == EtatDuPont.VIDE) {
                    continue;
                }

                Hashi simu = GraphUtils.clonerHashi(hashi);
                Ile ileSimuA = simu.getIle(ile.getCoordonnees().x, ile.getCoordonnees().y);
                Ile ileSimuB = (pont.getileA() == ile ? pont.getileB() : pont.getileA());
                Pont pontSimu = simu.getPont(ileSimuA, simu.getIle(ileSimuB.getCoordonnees().x,
                        ileSimuB.getCoordonnees().y));

                pontSimu.setEtatActuel(EtatDuPont.INTERDIT);

                if (!propagerEtVerifier(simu)) {
                    String explication = String.format(
                            "L'île en (%d, %d) serait isolée sans ce pont. Il est obligatoire.",
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

    private boolean propagerEtVerifier(Hashi simu) {
        boolean changement = true;
        while (changement) {
            changement = false;
            for (Ile ile : simu.getIles()) {
                int restants = ile.getNbPontsRequis() - ile.getNbPontsActuels();
                if (restants <= 0)
                    continue;

                // Ponts encore disponibles pour cette île
                List<Pont> pontsDispos = getVoisin(ile).stream()
                        .filter(p -> p.getEtatActuel() == EtatDuPont.VIDE
                                || p.getEtatActuel() == EtatDuPont.SIMPLE)
                        .filter(p -> {
                            Ile autre = (p.getileA() == ile) ? p.getileB() : p.getileA();
                            int capaciteAutre = autre.getNbPontsRequis() - autre.getNbPontsActuels();
                            int capacitePont = (p.getEtatActuel() == EtatDuPont.SIMPLE) ? 1 : 2;
                            return capaciteAutre > 0 && capacitePont <= capaciteAutre;
                        })
                        .collect(Collectors.toList());

                if (pontsDispos.isEmpty()) {
                    return false; // île isolée
                }

                if (pontsDispos.size() == 1) {
                    Pont seulPont = pontsDispos.get(0);
                    int capacite = Math.min(restants, 2); // max double
                    if (capacite == 1 && seulPont.getEtatActuel() == EtatDuPont.VIDE) {
                        seulPont.setEtatActuel(EtatDuPont.SIMPLE);
                        changement = true;
                    } else if (capacite == 2 && seulPont.getEtatActuel() != EtatDuPont.DOUBLE) {
                        seulPont.setEtatActuel(EtatDuPont.DOUBLE);
                        changement = true;
                    }
                }
            }
        }
        return true;
    }
}
