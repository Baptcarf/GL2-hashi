package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hashiGRP3.Logic.Historique.HistoriqueManager;

/**
 * Représente le jeu Hashi avec la grille, les îles et les ponts.
 * Gère la création et l'initialisation du plateau de jeu,
 * ainsi que la vérification des conditions de victoire.
 */
public class Hashi {
    /** Map associant les coordonnées aux îles du plateau */
    private final Map<Coordonnees, Ile> iles = new HashMap<>();
    /** Ensemble des ponts reliant les îles */
    private final Set<Pont> ponts = new HashSet<>();
    /** Taille maximale du plateau (largeur, hauteur) */
    private Coordonnees taille = new Coordonnees(0,0);
    /** Historique de la partie */
    private final HistoriqueManager historique = new HistoriqueManager();

    /**
     * Ajoute une île au plateau de jeu.
     * Met à jour la taille du plateau en fonction des coordonnées de l'île.
     * 
     * @param ile l'île à ajouter
     */
    public void ajouterIle(Ile ile) {
        iles.put(ile.getCoordonnees(), ile);

        int newX = Math.max(taille.x, ile.getCoordonnees().x);
        int newY = Math.max(taille.y, ile.getCoordonnees().y);
        taille = new Coordonnees(newX, newY);
    }
    

    /**
     * Trouve l'île voisine d'une île donnée dans une direction spécifiée.
     * 
     * @param ile l'île de départ
     * @param direction la direction dans laquelle chercher
     * @return l'île voisine trouvée, ou null si aucune île n'existe dans cette direction
     */
    private Ile trouverVoisin(Ile ile, Direction direction) {
        Coordonnees deplacementTheorique = ile.getCoordonnees().additionner(direction.getDelta());

        while (estDansLaGrille(deplacementTheorique)) {
            if (iles.containsKey(deplacementTheorique)) {
                return iles.get(deplacementTheorique);
            }
            deplacementTheorique = deplacementTheorique.additionner(direction.getDelta());
        }
        return null;
    }

    /**
     * Récupère le pont existant entre deux îles, ou le crée s'il n'existe pas.
     * 
     * @param ileA la première île
     * @param ileB la deuxième île
     * @return le pont existant ou nouvellement créé
     */
    private Pont obtenirOuCreerPont(Ile ileA, Ile ileB) {
        Pont pont = getPont(ileA, ileB);
        
        if (pont != null) {
            return pont;
        }

        Pont nouveauPont = new Pont(ileA, ileB, EtatDuPont.VIDE);
        this.ponts.add(nouveauPont);
        return nouveauPont;
    }

    /**
     * Initialise tous les ponts possibles entre les îles du plateau.
     * Crée les connexions de ponts dans toutes les directions pour chaque île.
     */
    public void initialisationToutLesPonts() {
        for (Ile ileActuelle : iles.values()) {
            for (Direction direction : Direction.values()) {

                Ile ileVoisine = trouverVoisin(ileActuelle, direction);

                if (ileVoisine != null) {
                    Pont pont = obtenirOuCreerPont(ileActuelle, ileVoisine);

                    ileActuelle.ajouterPonts(direction, pont);
                    ileVoisine.ajouterPonts(direction.directionOppose(), pont);
                }
            }
        }
    }

    /**
     * Initialise tous les conflits possibles entre les ponts.
     * Un conflit existe lorsque deux ponts perpendiculaires se croisent sur le plateau.
     */
    public void initialisationToutLesConflits() {
        for(Pont pontA : ponts) {
            boolean isHorizontal = pontA.getOrientation() == Pont.Orientation.HORIZONTAL;
            for(Pont pontB : ponts) {
                if (pontA.getOrientation() != pontB.getOrientation()) {
                    if (isHorizontal) {
                        if(pontB.getileA().getCoordonnees().y<pontA.getileA().getCoordonnees().y 
                        && pontA.getileA().getCoordonnees().y<pontB.getileB().getCoordonnees().y  // teste si Aay < Bay < Aby
                        && pontA.getileA().getCoordonnees().x<pontB.getileA().getCoordonnees().x  
                        && pontB.getileA().getCoordonnees().x<pontA.getileB().getCoordonnees().x){// teste si Bax < Aax < Bbx
                            pontA.ajouterConflit(pontB);
                        }
                    } else { 
                        if(pontB.getileA().getCoordonnees().x<pontA.getileA().getCoordonnees().x  
                        && pontA.getileA().getCoordonnees().x<pontB.getileB().getCoordonnees().x  // teste si Bax < Aax < Bbx
                        && pontA.getileA().getCoordonnees().y<pontB.getileA().getCoordonnees().y  
                        && pontB.getileA().getCoordonnees().y<pontA.getileB().getCoordonnees().y){// teste si Aay < Bay < Aby
                            pontA.ajouterConflit(pontB);
                        }
                    }
                }
            }
        }
    }

    /**
     * Vérifie si une coordonnée est à l'intérieur des limites de la grille.
     * 
     * @param c la coordonnée à vérifier
     * @return true si la coordonnée est dans la grille, false sinon
     */
    public boolean estDansLaGrille(Coordonnees c) {
        return c.x >= 0 && c.x <= this.taille.x && c.y >= 0 && c.y <= this.taille.y;
    }

    /**
     * Retourne l'ensemble de tous les ponts du plateau.
     * 
     * @return l'ensemble des ponts
     */
    public Set<Pont> getPonts() {
        return ponts;
    }
    
    /**
     * Récupère une île à partir de ses coordonnées.
     * 
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @return l'île aux coordonnées spécifiées, ou null si aucune île n'existe
     */
    public Ile getIle(int x, int y) {
        return iles.get(new Coordonnees(x, y));
    }

    /**
     * Récupère le pont reliant deux îles.
     * 
     * @param ileA la première île
     * @param ileB la deuxième île
     * @return le pont reliant les deux îles, ou null s'il n'existe pas
     */
    public Pont getPont(Ile ileA, Ile ileB) {
        Pont test = new Pont(ileA, ileB, EtatDuPont.VIDE);
        return getPont(test);
    }

    /**
     * Cherche un pont égal au pont donné en paramètre.
     * 
     * @param recherche le pont à rechercher
     * @return le pont trouvé, ou null s'il n'existe pas
     */
    public Pont getPont(Pont recherche) {
        for (Pont pont : ponts) {
            if (pont.equals(recherche)) {
                return pont;
            }
        }
        return null;
    }

    /**
     * Vérifie si la partie est gagnée.
     * La partie est gagnée si tous les ponts sont correctement connectés
     * selon les contraintes des îles.
     * 
     * @return true si la partie est gagnée, false sinon
     */
    public boolean estGagne(){
        for(Pont p : ponts){
            if (!p.estCorrect()){
                return false;
            }
        }
        return true;
    }

    /**
     * Joue un coup sur un pont et l'enregistre dans l'historique
     * @param pont Le pont sur lequel jouer
     */
    public void jouer(Pont pont) {
        EtatDuPont avant = pont.getEtatActuel();
        pont.cycler();
        EtatDuPont apres = pont.getEtatActuel();
        
        if (avant != apres) {
            historique.ajouterAction(pont, avant, apres);
        }
    }
    
    /**
     * Annule le dernier coup (Ctrl+Z)
     * @return true si un coup a été annulé, false si rien à annuler
     */
    public boolean undo() {
        return historique.undo();
    }
    
    /**
     * Remet le dernier coup annulé (Ctrl+Y)
     * @return true si un coup a été remit, false si rien à ete remit
     */
    public boolean redo() {
        return historique.redo();
    }

     // Version d'affichage expériemental afin de visualiser le plateau dans la console en attnendant une interface graphique



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String[][] grilleAffichage = new String[taille.y + 1][taille.x + 1];

        // Grille vide
        for (int y = 0; y <= taille.y; y++)
            for (int x = 0; x <= taille.x; x++)
                grilleAffichage[y][x] = " ";

        // Placer les îles
        for (Ile ile : iles.values()) {
            int x = ile.getCoordonnees().x;
            int y = ile.getCoordonnees().y;

            grilleAffichage[y][x] = String.valueOf(ile.getNbPontsRequis());
        }

        // Placer les ponts
        for (Pont pont : ponts) {
            Ile a = pont.getileA();
            Ile b = pont.getileB();
            int x1 = a.getCoordonnees().x;
            int y1 = a.getCoordonnees().y;
            int x2 = b.getCoordonnees().x;
            int y2 = b.getCoordonnees().y;

            String c;

            if (pont.getEtatActuel() == EtatDuPont.SIMPLE) c = pont.getOrientation() == Pont.Orientation.HORIZONTAL ? "─" : "|";
            else if (pont.getEtatActuel() == EtatDuPont.DOUBLE) c = pont.getOrientation() == Pont.Orientation.HORIZONTAL ? "═" : "║";
            else c = ".";

            if (pont.getOrientation() == Pont.Orientation.HORIZONTAL) {
                for (int x = Math.min(x1, x2) + 1; x < Math.max(x1, x2); x++)
                    // Vérifie que le pont n'en écrase pas un autre
                    if (grilleAffichage[y1][x].equals(" ") || grilleAffichage[y1][x].equals(".")){
                        grilleAffichage[y1][x] = c;
                    }  
            } else {
                for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++)
                    if (grilleAffichage[y][x1].equals(" ") || grilleAffichage[y][x1].equals(".")){
                        grilleAffichage[y][x1] = c;
                    }  
            }
        }

        // Affichage stylé avec coordonnées
        sb.append("    ");

        for (int x = 0; x <= taille.x; x++) sb.append(x).append(" ");

        sb.append("\n  ┌");
        for (int i = 0; i <= taille.x * 2 + 2; i++) sb.append("─");
        sb.append("┐\n");


        for (int y = 0; y <= taille.y; y++) {
            sb.append(y).append(" │ ");
            for (int x = 0; x <= taille.x; x++) sb.append(grilleAffichage[y][x]).append(" ");
            sb.append("│\n");
        }

        sb.append("  └");
        for (int i = 0; i <= taille.x * 2 + 2; i++) sb.append("─");
        sb.append("┘\n");

        return sb.toString();
    }
}

