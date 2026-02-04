//Attribut au paquet
package hashiGRP3.compDB;



/** 
 * Classe d'utilisateur pour la base de donnée.
 */
public class Utilisateur {

        private String pseudo;
        private String color;

        public Utilisateur(String pseudo, String color) {
                this.pseudo = pseudo;
                this.color = color;
        }

        public String getPseudo() {
                return pseudo;
        }

        public String getColor() {
                return color;
        }
}
