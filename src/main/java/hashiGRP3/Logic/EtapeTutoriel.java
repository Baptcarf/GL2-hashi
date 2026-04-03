//Attribut au paquet
package hashiGRP3.Logic;

import java.util.function.Predicate;

/**
 * Représente une étape d'un tutoriel.
 * Chaque étape a un titre, un texte explicatif, et une condition optionnelle.
 * Si la condition est null, l'étape est libre (bouton Suivant toujours cliquable).
 * Si la condition est définie, le bouton Suivant est bloqué tant qu'elle n'est pas remplie.
 */
public class EtapeTutoriel {

    /** Titre de l'étape affiché en haut du panneau */
    private final String titre;

    /** Texte explicatif de l'étape */
    private final String texte;

    /**
     * Condition de validation de l'étape.
     * null = étape libre, le joueur peut passer quand il veut.
     * non-null = le joueur doit satisfaire la condition pour passer.
     */
    private final Predicate<Hashi> condition;

    /**
     * Constructeur d'une étape avec condition.
     *
     * @param titre     le titre de l'étape
     * @param texte     le texte explicatif
     * @param condition la condition à remplir, ou null si libre
     */
    public EtapeTutoriel(String titre, String texte, Predicate<Hashi> condition) {
        this.titre = titre;
        this.texte = texte;
        this.condition = condition;
    }

    /**
     * Constructeur d'une étape libre (sans condition).
     *
     * @param titre le titre de l'étape
     * @param texte le texte explicatif
     */
    public EtapeTutoriel(String titre, String texte) {
        this(titre, texte, null);
    }

    /** Getter sur le titre */
    public String getTitre() {
        return titre;
    }

    /** Getter sur le texte */
    public String getTexte() {
        return texte;
    }

    /**
     * Vérifie si la condition de l'étape est remplie.
     * Si pas de condition, retourne toujours true.
     *
     * @param hashi l'état actuel du jeu
     * @return true si l'étape peut être validée
     */
    public boolean estValidee(Hashi hashi) {
        if (condition == null) return true;
        return condition.test(hashi);
    }

    /**
     * Indique si cette étape a une condition de validation.
     *
     * @return true si l'étape a une condition
     */
    public boolean aUneCondition() {
        return condition != null;
    }
}