package hashiGRP3.Logic.Aide.Techniques;

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
 * Technique T6 - Saturation exacte par capacité maximale des voisins
 *
 * Condition : la somme des capacités RESTANTES de chaque voisin
 *             est exactement égale au nombre de ponts restants à poser.
 *
 * Capacité restante d'un pont = min(2, indice_voisin) - ponts déjà posés sur ce pont
 *
 * Dans ce cas, la seule façon d'atteindre l'indice est de mettre
 * le maximum de ponts possible vers chaque voisin.
 */
public class TechniqueSaturationCapaciteMax extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Saturation exacte par capacité maximale des voisins";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.FACILE;
    }

    private int getPontsActuels(Pont pont) {
        return switch (pont.getEtatActuel()) {
            case SIMPLE -> 1;
            case DOUBLE -> 2;
            default -> 0;
        };
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet()) continue;

            int nbVoisins = getNbVoisinsTotal(ile);
            if (nbVoisins == 0) continue;

            // Calcul des ponts déjà posés et de la capacité restante par voisin
            int pontsDejaPos = 0;
            int sommeCapacitesRestantes = 0;

            for (Direction dir : Direction.values()) {
                Pont pont = ile.getPont(dir);
                if (pont == null) continue;

                Ile voisin = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();
                int maxVersCeVoisin = Math.min(2, voisin.getNbPontsRequis());
                int dejaPosSurCePont = getPontsActuels(pont);

                pontsDejaPos += dejaPosSurCePont;
                // Capacité restante = ce qu'on peut encore poser sur ce pont
                sommeCapacitesRestantes += Math.max(0, maxVersCeVoisin - dejaPosSurCePont);
            }

            int pontsRestants = ile.getNbPontsRequis() - pontsDejaPos;

            // Condition : ce qui reste à poser == capacité restante totale
            if (pontsRestants <= 0) continue;
            if (sommeCapacitesRestantes != pontsRestants) continue;

            // Chaque voisin doit recevoir le maximum possible
            // On retourne le premier pont qui n'a pas encore atteint son max
            for (Direction dir : Direction.values()) {
                Pont pont = ile.getPont(dir);
                if (pont == null) continue;

                Ile voisin = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();
                int maxVersCeVoisin = Math.min(2, voisin.getNbPontsRequis());
                EtatDuPont etatCible = (maxVersCeVoisin == 2) ? EtatDuPont.DOUBLE : EtatDuPont.SIMPLE;

                if (pont.getEtatActuel() != etatCible) {
                    String explication = String.format(
                        "L'île en (%d, %d) a %d ponts restants à poser,  " +
                        "égal exactement à la somme des capacités restantes de ses voisins.  " +
                        "Chaque voisin doit recevoir le maximum de ponts possible.",
                        ile.getCoordonnees().x, ile.getCoordonnees().y,
                        pontsRestants
                    );
                    return Optional.of(new IndiceResultat(
                        getNom(),
                        explication,
                        getNiveauDifficulte(),
                        Optional.of(pont),
                        Optional.of(etatCible),
                        false,
                        List.of(ile),
                        List.of(pont)
                    ));
                }
            }
        }
        return Optional.empty();
    }
}