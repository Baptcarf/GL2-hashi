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
 * Technique T3  Saturation moins deux - voisin contraint
 *
 * Condition : nbPontsRequis == nbVoisins * 2 - 2
 *             ET un des voisins a un indice de 1
 * Que le voisin contraint soit connecté ou non,
 * les ponts restants ne peuvent couvrir tous les autres voisins qu'avec
 * au moins un pont simple chacun.
 *
 * Retourne le premier pont de l'île vers un voisin non contraint qui n'est pas encore SIMPLE
 */
public class TechniqueSaturationMoinsDeux extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Saturation moins deux - voisin contraint";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.FACILE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet()) continue;

            int nbVoisins = getNbVoisinsTotal(ile);
            if (nbVoisins == 0) continue;

            // Condition 1 : saturation moins deux
            if (ile.getNbPontsRequis() != nbVoisins * 2 - 2) continue;

            // Condition 2 : un voisin est contraint à 1 pont
            for (Direction dir : Direction.values()) {
                Pont pont = ile.getPont(dir);
                if (pont == null) continue;

                Ile voisin = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();
                if (voisin.getNbPontsRequis() == 1) {
                    // Les autres voisins reçoivent obligatoirement au moins un pont simple
                    for (Direction dir2 : Direction.values()) {
                        Pont pont2 = ile.getPont(dir2);
                        if (pont2 != null
                            && !pont2.equals(pont)
                            && pont2.getEtatActuel() == EtatDuPont.VIDE) {

                            String explication = String.format(
                                "L'île en (%d, %d) a un voisin contraint à 1 pont. "
                                + "Indépendamment de sa connexion, les autres voisins "
                                + "reçoivent obligatoirement au moins un pont simple.",
                                ile.getCoordonnees().x, ile.getCoordonnees().y
                            );
                            return Optional.of(new IndiceResultat(
                                getNom(),
                                explication,
                                getNiveauDifficulte(),
                                Optional.of(pont2),
                                Optional.of(EtatDuPont.SIMPLE),
                                false,
                                List.of(ile),
                                List.of(pont2)
                            ));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}