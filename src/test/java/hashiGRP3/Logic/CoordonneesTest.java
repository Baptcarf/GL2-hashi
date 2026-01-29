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
        Coordonnees c4 = new Coordonnees(2, 5); // même x, y différent
        Coordonnees c5 = new Coordonnees(1, 4); // x différent, même y

        // Egalité parfaite
        assertEquals(c1, c2, "Deux coordonnées identiques doivent être égales");

        // Différents (x différent)
        assertNotEquals(c1, c3, "Des coordonnées différentes ne doivent pas être égales");

        // Différents (y différent)
        assertNotEquals(c1, c4, "Même x mais y différent ne doivent pas être égales");
        
        // Différents (x différent, même y)
        assertNotEquals(c1, c5, "Même y mais x différent ne doivent pas être égales");

        // Auto-comparaison
        assertEquals(c1, c1, "Un objet doit être égal à lui-même");

        // Comparaison à null
        assertNotEquals(c1, null, "Un objet ne doit pas être égal à null");

        // Comparaison à un autre type
        assertNotEquals(c1, "string", "Un objet ne doit pas être égal à un autre type");
    }

    @Test
    void testToString() {
        Coordonnees c1 = new Coordonnees(1, 2);
        assertEquals("(1,2)", c1.toString(), "toString doit retourner le bon format");
        Coordonnees c2 = new Coordonnees(0, 0);
        assertEquals("(0,0)", c2.toString(), "toString doit fonctionner pour zéro");
        Coordonnees c3 = new Coordonnees(-1, 5);
        assertEquals("(-1,5)", c3.toString(), "toString doit fonctionner pour des valeurs négatives");
    }

    @Test
    void testCompareTo() {
        // x différents
        Coordonnees c1 = new Coordonnees(1, 5);
        Coordonnees c2 = new Coordonnees(2, 3);
        assertTrue(c1.compareTo(c2) < 0, "c1 doit être avant c2 car x plus petit");
        assertTrue(c2.compareTo(c1) > 0, "c2 doit être après c1 car x plus grand");

        // x égaux, y différents
        Coordonnees c3 = new Coordonnees(1, 3);
        assertTrue(c1.compareTo(c3) > 0, "c1 doit être après c3 car même x, y plus grand");
        assertTrue(c3.compareTo(c1) < 0, "c3 doit être avant c1 car même x, y plus petit");

        // x et y égaux
        Coordonnees c4 = new Coordonnees(1, 5);
        assertEquals(0, c1.compareTo(c4), "c1 doit être égal à c4");
        assertEquals(0, c4.compareTo(c1), "c4 doit être égal à c1");
    }
    
    @Test
    void testMemeLigne() {
        // Même ligne, x différents
        Coordonnees c1 = new Coordonnees(3, 5);
        Coordonnees c2 = new Coordonnees(1, 5);
        assertTrue(c1.memeLigne(c2), "c1 et c2 doivent être sur la même ligne");

        // Ligne différente
        Coordonnees c3 = new Coordonnees(3, 4);
        assertFalse(c1.memeLigne(c3), "c1 et c3 ne doivent pas être sur la même ligne");

        // Même objet
        assertTrue(c1.memeLigne(c1), "c1 doit être sur la même ligne que lui-même");

        // Même ligne, x et y égaux
        Coordonnees c4 = new Coordonnees(3, 5);
        assertTrue(c1.memeLigne(c4), "c1 et c4 doivent être sur la même ligne (égalité parfaite)");
    }

    @Test
    void testMemeColonne() {
        // Même colonne, y différents
        Coordonnees c1 = new Coordonnees(3, 5);
        Coordonnees c2 = new Coordonnees(3, 2);
        assertTrue(c1.memeColonne(c2), "c1 et c2 doivent être sur la même colonne");

        // Colonne différente
        Coordonnees c3 = new Coordonnees(1, 5);
        assertFalse(c1.memeColonne(c3), "c1 et c3 ne doivent pas être sur la même colonne");

        // Même objet
        assertTrue(c1.memeColonne(c1), "c1 doit être sur la même colonne que lui-même");

        // Même colonne, x et y égaux
        Coordonnees c4 = new Coordonnees(3, 5);
        assertTrue(c1.memeColonne(c4), "c1 et c4 doivent être sur la même colonne (égalité parfaite)");
    }

    @Test
    void testHashCode() {
        // Même coordonnées, hashCode identique
        Coordonnees c1 = new Coordonnees(2, 4);
        Coordonnees c2 = new Coordonnees(2, 4);
        assertEquals(c1.hashCode(), c2.hashCode(), "Deux objets égaux doivent avoir le même hashCode");

        // Coordonnées différentes, hashCode probablement différent
        Coordonnees c3 = new Coordonnees(3, 4);
        assertNotEquals(c1.hashCode(), c3.hashCode(), "Des objets différents devraient avoir un hashCode différent");

        // Auto-comparaison
        assertEquals(c1.hashCode(), c1.hashCode(), "Un objet doit avoir le même hashCode que lui-même");
    }
}