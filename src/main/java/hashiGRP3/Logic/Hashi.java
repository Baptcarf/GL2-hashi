package hashiGRP3.Logic;

import java.util.HashSet;
import java.util.Set;

public class Hashi {
    private Set<Ile> iles = new HashSet<>();
    private Set<Pont> ponts = new HashSet<>();
    private int tailleX = 0;
    private int tailleY = 0;

    public void ajouterIle(Ile ile) {
        iles.add(ile);
        
        if (ile.getX() > this.tailleX) {
            this.tailleX = ile.getX();
        }
        
        if (ile.getY() > this.tailleY) {
            this.tailleY = ile.getY();
        }
    }

}

