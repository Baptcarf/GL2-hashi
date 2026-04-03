//Attribut au paquet
package hashiGRP3.Logic.Aide;

/* Imports */
import java.util.Optional;

import hashiGRP3.Logic.Hashi;



/** Interface des fonctionnalités d'un indice.*/
public interface TechniqueIndice {	
	/** Getter sur le nom de la technique */
	String getNom();                         
	/** Getter sur le niveau de difficulté de la technique */
	NiveauDifficulte getNiveauDifficulte();             	
	/** Retourne un objet indice/Ou Non, que l'on va donné a l'ui pour l'affichage */
	Optional<IndiceResultat> evaluer(Hashi hashi); 
}
