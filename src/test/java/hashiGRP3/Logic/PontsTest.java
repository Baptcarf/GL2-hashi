package hashiGRP3.Logic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PontsTest {
    @Test //Test le cas où un pont essaie de relier une île à elle même
    public void PontReliantUneIleAElleMeme(){
        Ile ile1 = new Ile(new Coordonnees(3, 1), 2);
        assertThrows(IllegalArgumentException.class, () -> {
            new Pont(ile1, ile1, EtatDuPont.VIDE);
        });
    }

    @Test //Test le cas où un pont essaie de relier des îles en diagonales
    public void PontReliantsIlesEnDiagonales(){
        Ile ile1 = new Ile(new Coordonnees(3, 1), 2);
        Ile ile2 = new Ile(new Coordonnees(4, 2), 2);
        assertThrows(IllegalArgumentException.class, () -> {
            new Pont(ile1, ile2, EtatDuPont.VIDE);
        });
    }

    @Test //Test le coas où l'île la plus en bas à droite est donnée en premier argument
    public void PlusLoinDabbord(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile2, ile1, EtatDuPont.VIDE);
        assert(pont.getileA() == ile1 && pont.getileB() == ile2);
    }
    
    @Test ///Test les EtatDuPont si le pont est vide
    public void TestEtatDuPontVide(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
        assert(pont.getEtatActuel() == EtatDuPont.VIDE);
    }

    @Test ///Test les EtatDuPont si le pont est simple
    public void TestEtatDuPontSimple(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.SIMPLE);
        assert(pont.getEtatActuel() == EtatDuPont.SIMPLE);
    }

    @Test ///Test les EtatDuPont si le pont est double
    public void TestEtatDuPontDouble(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.DOUBLE);
        assert(pont.getEtatActuel() == EtatDuPont.DOUBLE);
    }

    @Test //Test EtatCorrect & SetEtatCorrect
    public void TestEtatCorrectEtSetEtatCorrect(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
        pont.setEtatCorrect(EtatDuPont.SIMPLE);
        assert(pont.getEtatCorrect() == EtatDuPont.SIMPLE);
    }

    @Test //Test EstCorrect
    public void TestEstCorrect(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 3);
        Ile ile3 = new Ile(new Coordonnees(2, 0), 1);
        Pont pont1 = new Pont(ile1, ile2, EtatDuPont.DOUBLE);
        Pont pont2 = new Pont(ile1, ile3, EtatDuPont.VIDE);
        pont1.setEtatCorrect(EtatDuPont.DOUBLE);
        pont2.setEtatCorrect(EtatDuPont.SIMPLE);
        assert(pont1.estCorrect() && !pont2.estCorrect());
    }

    @Test //Test SetEtatActuel
    public void TestSetEtatActuel(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 3);
        Pont pont1 = new Pont(ile1, ile2, EtatDuPont.VIDE);
        Pont pont2 = new Pont(ile1, ile2, EtatDuPont.VIDE);
        Pont pont3 = new Pont(ile1, ile2, EtatDuPont.VIDE);
        pont1.setEtatActuel(EtatDuPont.SIMPLE);
        pont2.setEtatActuel(EtatDuPont.DOUBLE);
        pont3.setEtatActuel(EtatDuPont.SIMPLE);
        pont3.setEtatActuel(EtatDuPont.VIDE);
        assert(pont1.getEtatActuel() == EtatDuPont.SIMPLE && pont2.getEtatActuel() == EtatDuPont.DOUBLE && pont3.getEtatActuel() == EtatDuPont.VIDE);
    }

    @Test //Test egalité entre pont et objet non pont
    public void TestEgalitePontEtObjetNonPont(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 3);
        Pont pont1 = new Pont(ile1, ile2, EtatDuPont.VIDE);
        Object notAPont = "Je ne suis pas un pont";
        assertFalse(pont1.equals(notAPont));
    }

    @Test
    void testPontEstPossibleNoConflit() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 1);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
        assertTrue(pont.pontEstPossible());
    }

    @Test
    void testPontEstPossibleConflitAvecPontOccupe() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 1);
        Ile ile3 = new Ile(new Coordonnees(0, 2), 1);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
        Pont pontConflit = new Pont(ile2, ile3, EtatDuPont.SIMPLE);
        pont.ajouterConflit(pontConflit);
        assertFalse(pont.pontEstPossible());
    }

    @Test
    void testPontEstPossibleConflitAvecPontVide() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 1);
        Ile ile3 = new Ile(new Coordonnees(0, 2), 1);
        Pont pont = new Pont(ile1, ile2, EtatDuPont.VIDE);
        Pont pontConflit = new Pont(ile2, ile3, EtatDuPont.VIDE);
        pont.ajouterConflit(pontConflit);
        assertTrue(pont.pontEstPossible());
    }

    @Test //Test du ToString
    public void TestToString(){
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 3);
        Ile ile3 = new Ile(new Coordonnees(2, 0), 1);
        Pont pont1 = new Pont(ile1, ile2, EtatDuPont.VIDE);
        String pont1StrAttendu = ile1.getCoordonnees() + "-V-"+ ile2.getCoordonnees() +" [" + EtatDuPont.VIDE + "]";  
        Pont pont2 = new Pont(ile1, ile3, EtatDuPont.VIDE);
        String pont2StrAttendu = ile1.getCoordonnees() + "-H-"+ ile3.getCoordonnees() +" [" + EtatDuPont.VIDE + "]"; 
        
        assert(pont1.toString().equals(pont1StrAttendu) && pont2.toString().equals(pont2StrAttendu));
    }
}
