package hashiGRP3.Logic.Aide.Techniques;

import java.util.ArrayList;
import java.util.Optional;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T7 - Isolation d'un segment à deux îles
 *
 * Si connecter deux îles au maximum épuise leurs deux indices et les isole
 * du reste, alors cette connexion maximale est interdite.
 *
 * Cas 1 : deg(A)==0 ET deg(B)==0 → impossible (pas de solution, ignoré ici)
 * Cas 2 : deg(A)==0 → A DOIT se connecter uniquement à B (pont obligatoire vers B)
 * Cas 3 : deg(A)>0 ET deg(B)>0 → connexion max interdite, au moins 1 pont ailleurs
 */
public class TechniqueIsolationDeuxIles extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isolation d'un segment à deux îles";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.INTERMEDIAIRE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Ile ile : hashi.getIles()) {
            if (ile.estComplet()) continue;

            ArrayList<Pont> voisinsIle = getVoisin(ile);
            if (voisinsIle.isEmpty()) continue;

            for (Pont pont : voisinsIle) {
                Ile voisin = pont.getileA().equals(ile) ? pont.getileB() : pont.getileA();
                if (voisin.estComplet()) continue;

                // Calcul du nombre max de ponts possible entre ile et voisin
                int pontsMaxEntreEux = Math.min(2,
                        Math.min(ile.getNbPontsRequis(), voisin.getNbPontsRequis()));

                // Condition de saturation mutuelle :
                // relier au max épuise les deux indices simultanément
                if (pontsMaxEntreEux != ile.getNbPontsRequis()) continue;
                if (pontsMaxEntreEux != voisin.getNbPontsRequis()) continue;

                ArrayList<Pont> voisinsVoisin = getVoisin(voisin);

                // Nombre d'autres voisins (hors le pont entre ile et voisin)
                int autresVoisinsIle = voisinsIle.size() - 1;
                int autresVoisinsVoisin = voisinsVoisin.size() - 1;

                // Cas 1 : les deux n'ont aucun autre voisin → impossible, on ignore
                if (autresVoisinsIle == 0 && autresVoisinsVoisin == 0) continue;

                // Cas 2 : ile n'a aucun autre voisin → elle DOIT se connecter à voisin
                // On suggère le pont obligatoire entre ile et voisin
                if (autresVoisinsIle == 0) {
                    EtatDuPont etatCible = (pontsMaxEntreEux == 2)
                            ? EtatDuPont.DOUBLE : EtatDuPont.SIMPLE;
                    if (pont.getEtatActuel() != etatCible) {
                        String explication = String.format(
                            "L'île en (%d, %d) n'a qu'un seul voisin possible :  " +
                            "l'île en (%d, %d). Elle doit obligatoirement lui envoyer  " +
                            "tous ses ponts.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y,
                            voisin.getCoordonnees().x, voisin.getCoordonnees().y
                        );
                        return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(pont),
                            Optional.of(etatCible),
                            false
                        ));
                    }
                    continue;
                }

                // Cas 3 : les deux ont d'autres voisins → connexion max interdite
                // Au moins 1 pont doit aller ailleurs depuis ile
                // On cherche un autre pont VIDE depuis ile vers un autre voisin
                for (Pont autrePont : voisinsIle) {
                    if (autrePont.equals(pont)) continue;
                    if (autrePont.getEtatActuel() == EtatDuPont.VIDE) {
                        String explication = String.format(
                            "Relier l'île en (%d, %d) à son voisin en (%d, %d) au maximum " +
                            "formerait un segment isolé à deux îles. " +
                            "Au moins un pont doit aller vers une autre île.",
                            ile.getCoordonnees().x, ile.getCoordonnees().y,
                            voisin.getCoordonnees().x, voisin.getCoordonnees().y
                        );
                        return Optional.of(new IndiceResultat(
                            getNom(),
                            explication,
                            getNiveauDifficulte(),
                            Optional.of(autrePont),
                            Optional.of(EtatDuPont.SIMPLE),
                            false
                        ));
                    }
                }
            }
        }
        return Optional.empty();
    }
}