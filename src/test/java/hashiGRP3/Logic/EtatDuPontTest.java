package hashiGRP3.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;



public class EtatDuPontTest {

    @Test
    void testGetValue() {
        assertEquals(0, EtatDuPont.VIDE.getValue());
        assertEquals(1, EtatDuPont.SIMPLE.getValue());
        assertEquals(2, EtatDuPont.DOUBLE.getValue());
    }

    @Test
    void testEnumValues() {
        EtatDuPont[] values = EtatDuPont.values();
        assertEquals(3, values.length);
        assertEquals(EtatDuPont.VIDE, values[0]);
        assertEquals(EtatDuPont.SIMPLE, values[1]);
        assertEquals(EtatDuPont.DOUBLE, values[2]);
    }

    @Test
    void testEnumEquality() {
        assertEquals(EtatDuPont.VIDE, EtatDuPont.valueOf("VIDE"));
        assertEquals(EtatDuPont.SIMPLE, EtatDuPont.valueOf("SIMPLE"));
        assertEquals(EtatDuPont.DOUBLE, EtatDuPont.valueOf("DOUBLE"));
        assertNotEquals(EtatDuPont.VIDE, EtatDuPont.SIMPLE);
        assertNotEquals(EtatDuPont.SIMPLE, EtatDuPont.DOUBLE);
        assertNotEquals(EtatDuPont.DOUBLE, EtatDuPont.VIDE);
    }

    @Test
    void testToString() {
        assertEquals("VIDE", EtatDuPont.VIDE.toString());
        assertEquals("SIMPLE", EtatDuPont.SIMPLE.toString());
        assertEquals("DOUBLE", EtatDuPont.DOUBLE.toString());
    }
}