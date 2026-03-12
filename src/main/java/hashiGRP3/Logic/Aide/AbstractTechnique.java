
package hashiGRP3.Logic.Aide;

import java.util.Optional;

import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;

public abstract class AbstractTechnique implements TechniqueIndice {

	@Override
	public Optional<IndiceResultat> evaluer(Hashi hashi) {
		if (hashi.estGagne()) {
			return Optional.empty();
		}
		return detecter(hashi);
	}

	/**
	 * Méthode à implémenter par chaque technique :
	 * retourne un indice si la technique s'applique, sinon Optional.empty()
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
        int count = 0;
        for (Direction dir : Direction.values()) {
            if (ile.getPont(dir) != null) {
                count++;
            }
        }
        return count;
    }
}
