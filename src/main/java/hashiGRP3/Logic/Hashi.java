package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hashi {
    private Map<Coordonnees, Ile> iles = new HashMap<>(); // Une hashmap où les clés sont les coordonées (x, y) => Une Ile 
    public  Set<Pont> ponts = new HashSet<>();
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

    public void initialisationToutLesPonts() {
        // Pour chaque ile,
        for (Ile ileActuel : iles.values()) {
            Coordonnees coordonneesIleActuel = ileActuel.getCoordonnees();
            // Pour chaque direction, 
            for (Direction direction : Direction.values()) {

                int x = coordonneesIleActuel.x + direction.getDx();
                int y = coordonneesIleActuel.y + direction.getDy();

                Ile ileVoisine = null;

                // On avance dans la direction actuel tout pendant que on trouve pas d'ile ou tant qu'on est dans la grille
                while (x >= 0 && x <= this.tailleX && y >= 0 && y <= this.tailleY) {
                    Coordonnees testCoordonnees = new Coordonnees(x, y);
                    if (iles.containsKey(testCoordonnees)) {
                        ileVoisine = iles.get(testCoordonnees);
                        break;
                    }

                    x += direction.getDx();
                    y += direction.getDy();
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

    }
    public void conflictPont() { 
        for(Pont pontA : ponts) {
            boolean isHorizontal = pontA.getOrientation() == Pont.Orientation.HORIZONTAL; //True si le pont est Horizontal, False sinon
            for(Pont pontB : ponts) {
                if (pontA.getOrientation() != pontB.getOrientation()) { //Teste si les ponts sont perpendiculaires
                    if (isHorizontal) {
                        if(pontB.getileA().getCoordonnees().y<pontA.getileA().getCoordonnees().y 
                        && pontA.getileA().getCoordonnees().y<pontB.getileB().getCoordonnees().y){// teste si Bay < Aay < Bby
                            pontA.ajouterConflit(pontB);
                        }
                    } else { 
                        if(pontB.getileA().getCoordonnees().x<pontA.getileA().getCoordonnees().x  
                        && pontA.getileA().getCoordonnees().x<pontB.getileB().getCoordonnees().x){// teste si Bax < Aax < Bbx
                            pontA.ajouterConflit(pontB);
                        }
                    }
                }
            }
        }
    }

    public Map<Coordonnees, Ile> getIles() {
        return iles;
    }

    public Ile getIle(int x, int y) {
        return iles.get(new Coordonnees(x, y));
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
    
}

