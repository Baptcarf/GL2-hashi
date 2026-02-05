package hashiGRP3.Logic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class IleTest {
    @Test //Teste la création d'une île avec des coordonnées et un nombre de ponts requis valides
    public void testNombrePontsRequis() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        assert(ile.getNbPontsRequis() == 3);
    }

    @Test //Teste les cas où le nombre de ponts requis est négatif ou supérieur à 8
    public void testNombrePontsRequisIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, 0), -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, 0), 9);
        });
    }

    @Test //Teste les cas où les coordonnées sont négatives
    public void testCoordonneesNegatives() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(-1, 0), 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, -1), 2);
        });
    }

    @Test //Teste l'ajout de ponts à une île et le calcul du nombre de ponts actuels connectés
    public void testgetNbPontsActuels() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);
        Pont pont = new Pont(ile, ile2, EtatDuPont.DOUBLE);
        ile.ajouterPonts(Direction.BAS, pont);
        assert(ile.getNbPontsActuels() == 2);
    }

    @Test //Teste la comparaison entre une île et un objet non-île
    public void testEqualsWithNonIle() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        String nonIle = "Not an island";
        assert(!ile.equals(nonIle));
    }

    @Test //Teste l'égalité d'une île avec elle-même
    public void testEqualsWithSameIle() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        assert(ile.equals(ile));
    }

    @Test //Teste l'égalité de deux îles différentes avec les mêmes coordonnées et le même nombre de ponts requis
    public void testEqualsWithDifferentIles() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 3);
        assert(!ile1.equals(ile2));
    }

    @Test //teste le toString d'une île
    public void testToString() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        String expected = "Ile n°"+ile.getId()+"|(0,0), (0/3) ponts connecté\n"  
                + "{}";
        System.out.println(ile);
        assert(ile.toString().equals(expected));
    }
}
