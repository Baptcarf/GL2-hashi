package hashiGRP3.Logic.Aide.Techniques;

import java.util.Optional;
import java.util.Set;

import hashiGRP3.Logic.Aide.AbstractTechnique;
import hashiGRP3.Logic.Aide.GraphUtils;
import hashiGRP3.Logic.Aide.IndiceResultat;
import hashiGRP3.Logic.Aide.NiveauDifficulte;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Technique T10 - Isolement quand un segment se connecte à un autre segment.
 *
 * Principe : si un pont est mis à l'état DOUBLE et que cela fusionne deux segments
 * en une composante sans aucune sortie possible vers l'extérieur, ce DOUBLE est interdit.
 */
public class TechniqueIsolementSegment extends AbstractTechnique {

    @Override
    public String getNom() {
        return "Isolement segment vers segment";
    }

    @Override
    public NiveauDifficulte getNiveauDifficulte() {
        return NiveauDifficulte.INTERMEDIAIRE;
    }

    @Override
    protected Optional<IndiceResultat> detecter(Hashi hashi) {
        for (Pont pontCandidat : hashi.getPonts()) {
            if (pontCandidat.getEtatActuel() == EtatDuPont.VIDE && !pontCandidat.pontEstPossible()) {
                continue;
            }

            if (isolationSiDouble(hashi, pontCandidat)) {
                Ile ileA = pontCandidat.getileA();
                Ile ileB = pontCandidat.getileB();

                String explication = String.format(
                        "Relier les segments via le pont entre (%d, %d) et (%d, %d) en DOUBLE "
                        + "isolerait une composante sans sortie externe. "
                        + "Ce pont ne doit donc pas être doublé.",
                        ileA.getCoordonnees().x,
                        ileA.getCoordonnees().y,
                        ileB.getCoordonnees().x,
                        ileB.getCoordonnees().y);

                return Optional.of(new IndiceResultat(
                        getNom(),
                        explication,
                        getNiveauDifficulte(),
                        Optional.of(pontCandidat),
                        Optional.of(EtatDuPont.DOUBLE),
                    true));
            }
        }

        return Optional.empty();
    }

    private boolean isolationSiDouble(Hashi hashi, Pont pontCandidat) {
        Hashi hashiSimule = GraphUtils.clonerHashi(hashi);

        Ile ileAClonee = hashiSimule.getIle(pontCandidat.getileA().getCoordonnees().x, pontCandidat.getileA().getCoordonnees().y);
        Ile ileBClonee = hashiSimule.getIle(pontCandidat.getileB().getCoordonnees().x, pontCandidat.getileB().getCoordonnees().y);
        Pont pontSimule = hashiSimule.getPont(ileAClonee, ileBClonee);
        pontSimule.setEtatActuel(EtatDuPont.DOUBLE);

        Set<Ile> composanteFusionnee = GraphUtils.composanteConnexe(pontSimule.getileA(), hashiSimule);

        if (composanteFusionnee.size() == hashiSimule.getIles().size()) {
            return false;
        }

        return !aSortieExternePossible(composanteFusionnee);
    }

    private boolean aSortieExternePossible(Set<Ile> composante) {
        for (Ile ile : composante) {
            for (Direction direction : Direction.values()) {
                Pont pont = ile.getPont(direction);
                if (pont == null) {
                    continue;
                }

                Ile autreIle = (pont.getileA() == ile) ? pont.getileB() : pont.getileA();
                if (composante.contains(autreIle)) {
                    continue;
                }

                if (pont.getEtatActuel() == EtatDuPont.DOUBLE) {
                    continue;
                }
                if (!pont.pontEstPossible()) {
                    continue;
                }
                if (getPontsRestantsIle(ile) <= 0 || getPontsRestantsIle(autreIle) <= 0) {
                    continue;
                }

                return true;
            }
        }

        return false;
    }
}