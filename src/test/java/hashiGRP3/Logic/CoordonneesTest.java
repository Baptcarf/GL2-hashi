package hashiGRP3.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CoordonneesTest {
    @Test
    void testEquals() {
        Coordonnees c1 = new Coordonnees(2, 4);
        Coordonnees c2 = new Coordonnees(2, 4);
        Coordonnees c3 = new Coordonnees(3, 4);
        Coordonnees c4 = new Coordonnees(2, 5);
        Coordonnees c5 = new Coordonnees(1, 4);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertNotEquals(c1, c4);
        assertNotEquals(c1, c5);
        assertEquals(c1, c1);
        assertNotEquals(c1, null);
        assertNotEquals(c1, "string");
    }

    @Test
    void testToString() {
        Coordonnees c1 = new Coordonnees(1, 2);
        assertEquals("(1,2)", c1.toString());
        Coordonnees c2 = new Coordonnees(0, 0);
        assertEquals("(0,0)", c2.toString());
        Coordonnees c3 = new Coordonnees(-1, 5);
        assertEquals("(-1,5)", c3.toString());
    }

    @Test
    void testCompareTo() {
        Coordonnees c1 = new Coordonnees(1, 5);
        Coordonnees c2 = new Coordonnees(2, 3);
        Coordonnees c3 = new Coordonnees(1, 3);
        Coordonnees c4 = new Coordonnees(1, 5);

        assertTrue(c1.compareTo(c2) < 0);
        assertTrue(c2.compareTo(c1) > 0);

        assertTrue(c1.compareTo(c3) > 0);
        assertTrue(c3.compareTo(c1) < 0);

        assertEquals(0, c1.compareTo(c4));
        assertEquals(0, c4.compareTo(c1));
    }
    
    @Test
    void testMemeLigne() {
        Coordonnees c1 = new Coordonnees(3, 5);
        Coordonnees c2 = new Coordonnees(1, 5);
        Coordonnees c3 = new Coordonnees(3, 4);
        Coordonnees c4 = new Coordonnees(3, 5);
        
        assertTrue(c1.memeLigne(c2));
        assertFalse(c1.memeLigne(c3));
        assertTrue(c1.memeLigne(c1));
        assertTrue(c1.memeLigne(c4));
    }

    @Test
    void testMemeColonne() {
        Coordonnees c1 = new Coordonnees(3, 5);
        Coordonnees c2 = new Coordonnees(3, 2);
        Coordonnees c3 = new Coordonnees(1, 5);
        Coordonnees c4 = new Coordonnees(3, 5);

        assertTrue(c1.memeColonne(c2));
        assertFalse(c1.memeColonne(c3));
        assertTrue(c1.memeColonne(c1));
        assertTrue(c1.memeColonne(c4));
    }

    @Test
    void testHashCode() {
        Coordonnees c1 = new Coordonnees(2, 4);
        Coordonnees c2 = new Coordonnees(2, 4);
        Coordonnees c3 = new Coordonnees(3, 4);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
        assertEquals(c1.hashCode(), c1.hashCode());
    }
}