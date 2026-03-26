//Attribut au paquet
package hashiGRP3.Logic.Aide;



/* Imports */
import java.util.Optional;
import java.util.ArrayList;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;



/** Classe abstraite représentant une technique */
public abstract class AbstractTechnique implements TechniqueIndice {

    @Override
    public Optional<IndiceResultat> evaluer(Hashi hashi) {
        if (hashi.estGagne()) {
            return Optional.empty();
        }
        return detecter(hashi);
    }

    /**
     * Méthode à implémenter pour chaque technique :
     * @return un indice si la technique s'applique, sinon Optional.empty()
     */
    protected abstract Optional<IndiceResultat> detecter(Hashi hashi);

    /**
     * Nombre de ponts restants à placer sur une île.
     */
    protected int getPontsRestantsIle(Ile ile) {
        return ile.getNbPontsRequis() - ile.getNbPontsActuels();
    }

    /** Compte le nombre de voisins accessibles */
    protected int getNbVoisinsTotal(Ile ile) {
        return getVoisin(ile).size();
    }

    /** Renvoie les voisins d'une ile donnée.*/
    protected ArrayList<Pont> getVoisin(Ile ile) {
        ArrayList<Pont> voisin = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Pont p = ile.getPont(dir);
            if (p != null) {
                voisin.add(p);
            }
        }
        return voisin;
    }
}
