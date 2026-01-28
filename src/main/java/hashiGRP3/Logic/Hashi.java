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
}

