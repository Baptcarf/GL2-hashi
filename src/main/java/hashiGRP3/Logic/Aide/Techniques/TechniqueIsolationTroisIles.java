package hashiGRP3.Logic.Aide.Techniques;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T8 - Isolation d'un segment à trois îles
 *
 * Soient A, B, C trois îles en chaîne où B est au centre.
 * Le segment est isolé si :
 *   - ponts_AB = min(2, kA, kB) == kA  → A serait saturée
 *   - ponts_BC = min(2, kC, kB) == kC  → C serait saturée
 *   - ponts_AB + ponts_BC == kB        → B serait saturée
 *   - A et C n'ont aucun autre voisin externe
 *
 * Dans ce cas, au moins 1 pont de B doit aller vers un voisin externe.
 */
public class TechniqueIsolationTroisIles extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isolation d'un segment à trois îles";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.INTERMEDIAIRE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet()) continue;

            ArrayList<Pont> voisins = getVoisin(ile);
            // B doit avoir au moins 2 voisins (A et C) + au moins 1 externe
            if (voisins.size() < 3) continue;

            // ile joue le rôle de B (île centrale)
            for (int i = 0; i < voisins.size(); i++) {
                for (int j = i + 1; j < voisins.size(); j++) {
                    Ile ileA = voisins.get(i).getileA().equals(ile)
                            ? voisins.get(i).getileB() : voisins.get(i).getileA();
                    Ile ileC = voisins.get(j).getileA().equals(ile)
                            ? voisins.get(j).getileB() : voisins.get(j).getileA();

                    if (ileA.estComplet() || ileC.estComplet()) continue;

                    // deg(A) = 1 et deg(C) = 1 : aucun voisin externe
                    if (getVoisin(ileA).size() != 1) continue;
                    if (getVoisin(ileC).size() != 1) continue;

                    // Calcul du max de ponts possible entre A-B et B-C
                    int pontsAB = Math.min(2,
                            Math.min(ileA.getNbPontsRequis(), ile.getNbPontsRequis()));
                    int pontsBC = Math.min(2,
                            Math.min(ileC.getNbPontsRequis(), ile.getNbPontsRequis()));

                    // A serait saturée par A-B
                    if (pontsAB != ileA.getNbPontsRequis()) continue;
                    // C serait saturée par B-C
                    if (pontsBC != ileC.getNbPontsRequis()) continue;
                    // B serait saturée par A-B + B-C
                    if (pontsAB + pontsBC != ile.getNbPontsRequis()) continue;

                    // Segment isolé détecté → B doit envoyer au moins 1 pont externe
                    for (Pont autrePont : voisins) {
                        Ile autreVoisin = autrePont.getileA().equals(ile)
                                ? autrePont.getileB() : autrePont.getileA();
                        if (autreVoisin.equals(ileA) || autreVoisin.equals(ileC)) continue;
                        if (autrePont.getEtatActuel() == EtatDuPont.VIDE) {
                            String explication = String.format(
                                "Relier uniquement l'île en (%d, %d) à ses voisins  " +
                                "en (%d, %d) et (%d, %d) formerait un segment isolé  " +
                                "à trois îles. Au moins un pont doit aller vers  " +
                                "une île externe.",
                                ile.getCoordonnees().x, ile.getCoordonnees().y,
                                ileA.getCoordonnees().x, ileA.getCoordonnees().y,
                                ileC.getCoordonnees().x, ileC.getCoordonnees().y
                            );
                            return Optional.of(new IndiceResultat(
                                getNom(),
                                explication,
                                getNiveauDifficulte(),
                                Optional.of(autrePont),
                                Optional.of(EtatDuPont.SIMPLE),
                                false,
                                List.of(ile, ileA, ileC),
                                List.of(autrePont)
                            ));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}