//Attribut au paquet
package hashiGRP3.compDB;



/** 
 * Classe d'utilisateur pour la base de donnée.
 */
public class Utilisateur {

        private String pseudo;
        private String color;

	/**
	 * Constructeur de classe.
	 * @param pseudo le pseudo de l'utilisateur.
	 * @param color la couleur de l'utilisateur.
	 */
        public Utilisateur(String pseudo, String color) {
                this.pseudo = pseudo;
                this.color = color;
        }

	/**
	 * Getter sur l'attribut pseudo.
	 * @return pseudo
         */
	 public String getPseudo() {
                return pseudo;
        }

	/**
	 * Getter sur l'attribut couleur.
	 * @return color
	 */
        public String getColor() {
                return color;
        }
}
