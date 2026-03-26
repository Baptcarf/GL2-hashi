//Attribut au paquet
package hashiGRP3.Logic.Aide;

/* Imports */
import java.util.Optional;

import hashiGRP3.Logic.Hashi;



/** Interface des fonctionnalités d'un indice.*/
public interface TechniqueIndice {	
	String getNom();                                    // Nom de la technique
	NiveauDifficulte getNiveauDifficulte();             // Niveau de la difficulté
	Optional<IndiceResultat> evaluer(Hashi hashi);      // Retourne un objet indice/Ou Non, que l'on va donné a l'ui pour l'affichage
}
