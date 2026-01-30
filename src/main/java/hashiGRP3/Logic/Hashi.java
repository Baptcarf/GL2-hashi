package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hashi {
    private final Map<Coordonnees, Ile> iles = new HashMap<>(); // Une hashmap où les clés sont les coordonées (x, y) => Une Ile 
    private  Set<Pont> ponts = new HashSet<>();
    private int tailleX = 0;
    private int tailleY = 0;

    public void ajouterIle(Ile ile) {
        iles.put(ile.getCoordonnees(), ile);

        int x = ile.getCoordonnees().x;
        int y = ile.getCoordonnees().y;

        if (x > this.tailleX) {
            this.tailleX = x;
        }
        if (y > this.tailleY) {
            this.tailleY = y;
        }
    }
    
    // Version d'affichage expériemental afin de visualiser le plateau dans la console en attnendant une interface graphique
    public void afficherPlateau() {
        String[][] grilleAffichage = new String[tailleY + 1][tailleX + 1];

        // Grille vide
        for (int y = 0; y <= tailleY; y++) {
            for (int x = 0; x <= tailleX; x++) {
                grilleAffichage[y][x] = " "; 
            }
        }

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
            if (pont.getOrientation() == Pont.Orientation.HORIZONTAL) {
                String c;
                if (pont.getEtatActuel() == EtatDuPont.SIMPLE) {
                    c = "─";
                } else if (pont.getEtatActuel() == EtatDuPont.DOUBLE) {
                    c = "═";
                } else {
                    c = "."; // Pont possible pendant la partie
                }
                int minX = Math.min(x1, x2) + 1;
                int maxX = Math.max(x1, x2);
                for (int x = minX; x < maxX; x++) {
                    grilleAffichage[y1][x] = c;
                }
            } else { // VERTICAL
                String c;
                if (pont.getEtatActuel() == EtatDuPont.SIMPLE) {
                    c = "|";
                } else if (pont.getEtatActuel() == EtatDuPont.DOUBLE) {
                    c = "║";
                } else {
                    c = "."; // Pont possible pendant la partie
                }
                int minY = Math.min(y1, y2) + 1;
                int maxY = Math.max(y1, y2);
                for (int y = minY; y < maxY; y++) {
                    grilleAffichage[y][x1] = c;
                }
            }
        }

        // Affichage stylé avec coordonnées
        System.out.print("    "); 
        for (int x = 0; x <= tailleX; x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.print("  ┌");
        for (int i = 0; i <= tailleX * 2 + 2; i++) System.out.print("─");
        System.out.println("┐");

        for (int y = 0; y <= tailleY; y++) {
            System.out.print(y + " │ "); 
            for (int x = 0; x <= tailleX; x++) {
                System.out.print(grilleAffichage[y][x] + " ");
            }
            System.out.println("│");
        }

        System.out.print("  └");
        for (int i = 0; i <= tailleX * 2 + 2; i++) System.out.print("─");
        System.out.println("┘");
    }


    public void initialisationToutLesPonts() {
        // Pour chaque ile,
        for (Ile ileActuel : iles.values()) {
            Coordonnees coordonneesIleActuel = ileActuel.getCoordonnees();
            // Pour chaque direction, 
            for (Direction direction : Direction.values()) {

                Coordonnees deplacementTheorique = coordonneesIleActuel.additionner(direction.getDelta());

                Ile ileVoisine = null;

                // On avance dans la direction actuel tout pendant que on trouve pas d'ile ou tant qu'on est dans la grille
                while (estDansLaGrille(deplacementTheorique)) {
                    if (iles.containsKey(deplacementTheorique)) {
                        ileVoisine = iles.get(deplacementTheorique);
                        break;
                    }

                    deplacementTheorique = deplacementTheorique.additionner(direction.getDelta());
                }

                if (ileVoisine != null) {
                    Pont pont = new Pont(ileActuel, ileVoisine, EtatDuPont.VIDE);
                    
                    // Vérifier si le pont existe déjà dans le Set
                    Pont pontExistant = null;
                    for (Pont p : this.ponts) {
                        if (p.equals(pont)) {
                            pontExistant = p;
                            break;
                        }
                    }
                    
                    if (pontExistant == null) {
                        // Nouveau pont, on l'ajoute
                        this.ponts.add(pont);
                        ileActuel.ajouterPonts(direction, pont);
                        ileVoisine.ajouterPonts(direction.directionOppose(), pont);
                    } else {
                        // Pont existe deja on utilise l'instance de base
                        ileActuel.ajouterPonts(direction, pontExistant);
                        ileVoisine.ajouterPonts(direction.directionOppose(), pontExistant);
                    }
                }
            }
        }
    }

    public void initialisationToutLesConflits() {
        for(Pont pontA : ponts) {
            boolean isHorizontal = pontA.getOrientation() == Pont.Orientation.HORIZONTAL; //True si le pont est Horizontal, False sinon
            for(Pont pontB : ponts) {
                if (pontA.getOrientation() != pontB.getOrientation()) { //Teste si les ponts sont perpendiculaires
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
     * Verifie si une coord est dans de la grille
     * @param c La coordonnée
     * @return true si la coordonnée est dans la grille, false sinon
     */
    public boolean estDansLaGrille(Coordonnees c) {
        return c.x >= 0 && c.x <= this.tailleX && c.y >= 0 && c.y <= this.tailleY;
    }

    public Map<Coordonnees, Ile> getIles() {
        return iles;
    }

    public Set<Pont> getPonts() {
        return ponts;
    }

    public int getTailleX() {
        return tailleX;
    }

    public int getTailleY() {
        return tailleY;
    }
    public Ile getIle(int x, int y) {
        return iles.get(new Coordonnees(x, y));
    }

    public Pont getPont(Pont recherche) {
        for (Pont pont : ponts) {
            if (pont.equals(recherche)) {
                return pont;
            }
        }
        return null;
    }
}

