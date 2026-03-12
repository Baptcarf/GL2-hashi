package hashiGRP3.Logic.Historique;

/**
 * Définit les différents modes ou états contextuels associés à une action de jeu.
 * <p>
 * Ces modes permettent de qualifier la nature d'une modification de pont
 * (par exemple, si elle est définitive ou simplement indicative).
 * </p>
 */
public enum Mode {
    /**
     * Mode standard indiquant que l'action appartient à l'historique de jeu classique.
     * Utilisé pour les coups validés par l'utilisateur.
     */
    HISTORIQUE,

    /**
     * Mode indiquant que l'action est provisoire (ponts de test).
     * Les ponts seront supprimée ou changés en définitif en fonction du choix de l'utilisateur à la fin du mode hypothèse.
     */
    TEMPORAIRE,

    /**
     * Mode utilisé pour marquer une action ayant entraîné une violation des règles 
     * (ex: pont incorrect).
     */
    ERREUR;

    /**
     * Retourne une représentation textuelle du mode.
     * @return Le nom du mode sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        return switch (this) {
            case HISTORIQUE -> "HISTORIQUE";
            case TEMPORAIRE -> "TEMPORAIRE";
            case ERREUR -> "ERREUR";
        };
    }
}