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
    
}

