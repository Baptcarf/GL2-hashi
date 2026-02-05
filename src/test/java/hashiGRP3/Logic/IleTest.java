package hashiGRP3.Logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IleTest {
    @Test //Teste la création d'une île avec des coordonnées et un nombre de ponts requis valides
    public void testNombrePontsRequis() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 3);
        assertEquals(3, ile1.getNbPontsRequis());
    }

    @Test //Teste la création d'une île avec des coordonnées valides et un nombre de ponts requis à 0 ou à 8
    public void testNombrePontsRequisLimites() {
        Ile ile1 = new Ile(new Coordonnees(1, 1), 0);
        assertEquals(0, ile1.getNbPontsRequis());
        Ile ile2 = new Ile(new Coordonnees(2, 2), 8);
        assertEquals(8, ile2.getNbPontsRequis());
    }

    @Test //Test les cas où le nombre de ponts requis est négatif ou supérieur à 8
    public void testNombrePontsRequisIncorrect() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, 0), -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, 0), 9);
        });
    }

    @Test //Test les cas où les coordonnées sont négatives (que ce soit x, y ou les deux)
    public void testCoordonneesNegatives() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(-1, 0), 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(0, -1), 2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Ile(new Coordonnees(-1, -1), 2);
        });
    }

    @Test //Test la création d'une île avec des coordonnées nulles
    public void testCoordonneesNull() {
        assertThrows(NullPointerException.class, () -> {
            new Ile(null, 3);
        });
    }

    @Test //Test l'ajout de ponts à une île et le calcul du nombre de ponts actuels connectés
    public void testgetNbPontsActuels() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);
        Pont pont = new Pont(ile, ile2, EtatDuPont.DOUBLE);
        ile.ajouterPonts(Direction.BAS, pont);
        assertEquals(2, ile.getNbPontsActuels());
    }

    @Test //test l'ajout de plusieurs ponts à une île et le calcul du nombre total de ponts actuels connectés
    public void testAjoutPlusieursPonts() {
        Ile ile = new Ile(new Coordonnees(0, 0), 4);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);
        Ile ile3 = new Ile(new Coordonnees(1, 0), 2);
        ile.ajouterPonts(Direction.BAS, new Pont(ile, ile2, EtatDuPont.SIMPLE));
        ile.ajouterPonts(Direction.DROITE, new Pont(ile, ile3, EtatDuPont.DOUBLE));
        assertEquals(3, ile.getNbPontsActuels());
}

    @Test //Test le cas où le nombre de ponts actuels connectés dépasse le nombre de ponts requis pour l'île
    public void testDepassementPontsRequis() {
        Ile ile = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);

        ile.ajouterPonts(Direction.BAS, new Pont(ile, ile2, EtatDuPont.DOUBLE));

        assertTrue(ile.getNbPontsActuels() > ile.getNbPontsRequis());
    }

    @Test //Test la comparaison entre une île et un objet non-île
    public void testEqualsAvecUnNonIle() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        Object nonIle = "Je ne siuis pas une île";
        assertFalse(ile.equals(nonIle));
    }

    @Test //Test l'égalité d'une île avec elle-même
    public void testEqualsAvecLaMemeIle() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        assertTrue(ile.equals(ile));
    }

    @Test //Test l'égalité de deux îles différentes avec les mêmes coordonnées et le même nombre de ponts requis
    public void testEqualsAvecDifferentesIles() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 3);
        assertFalse(ile1.equals(ile2));
    }

    @Test //Test l'égalité d'une île avec null
    public void testEqualsNull() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        assertFalse(ile.equals(null));
    }

    @Test // Test l'égalité entre deux îles distinctes ayant les mêmes attributs
    public void testEqualsSymetrique() {
        Ile ile1 = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 0), 3);

        assertFalse(ile1.equals(ile2));
        assertFalse(ile2.equals(ile1));
    }

    @Test //Test le toString d'une île
    public void testToString() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        String expected = "Ile n°"+ile.getId()+"|(0,0), (0/3) ponts connecté\n"  
                + "{}";
        assertEquals(expected, ile.toString());
    }

    @Test //Test le toString d'une île après l'ajout de ponts
    public void testToStringAvecPonts() {
        Ile ile = new Ile(new Coordonnees(0, 0), 3);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);
        Pont pont1 = new Pont(ile, ile2, EtatDuPont.DOUBLE);
        ile.ajouterPonts(Direction.BAS, pont1);
        String expected = "Ile n°"+ile.getId()+"|(0,0), (2/3) ponts connecté\n"  
                + "{bas=(0,0)-V-(0,1) [VIDE]}";
        System.out.println(ile);
        assertEquals(expected, ile.toString());
    }  
    
    @Test //Test le toString d'une île après l'ajout de ponts dépassant le nombre de ponts requis
    public void testToStringAvecTopDePonts() {
        Ile ile = new Ile(new Coordonnees(0, 0), 1);
        Ile ile2 = new Ile(new Coordonnees(0, 1), 2);
        Pont pont1 = new Pont(ile, ile2, EtatDuPont.DOUBLE);
        ile.ajouterPonts(Direction.BAS, pont1);
        String expected = "Ile n°"+ile.getId()+"|(0,0), (2/1) ponts connecté\n"  
                + "{bas=(0,0)-V-(0,1) [VIDE]}";
        assertEquals(expected, ile.toString());
    }  

}
