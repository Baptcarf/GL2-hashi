package hashiGRP3.Logic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hashi {
    private Map<Coordonnees, Ile> iles = new HashMap<>(); // Une hashmap où les clés sont les coordonées (x, y) => Une Ile 
    private Set<Pont> ponts = new HashSet<>();
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
                    this.ponts.add(pont);

                    // On connecte le pont aux deux ile (A-->B, B-->A)
                    ileActuel.ajouterPonts(direction, pont);
                    ileVoisine.ajouterPonts(direction.directionOppose(), pont);
                }
            }
        }
    }

    public void initialisationToutLesConflits() {

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
    
}

