package hashiGRP3.Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    void testDirectionValues() {
        // Teste à la fois les anciens getters et le nouvel objet delta
        assertEquals(0, Direction.HAUT.getDx());
        assertEquals(-1, Direction.HAUT.getDy());
        assertEquals(new Coordonnees(0, -1), Direction.HAUT.getDelta());
        
        assertEquals(0, Direction.BAS.getDx());
        assertEquals(1, Direction.BAS.getDy());
        assertEquals(new Coordonnees(0, 1), Direction.BAS.getDelta());
        
        assertEquals(1, Direction.DROITE.getDx());
        assertEquals(0, Direction.DROITE.getDy());
        assertEquals(new Coordonnees(1, 0), Direction.DROITE.getDelta());
        
        assertEquals(-1, Direction.GAUCHE.getDx());
        assertEquals(0, Direction.GAUCHE.getDy());
        assertEquals(new Coordonnees(-1, 0), Direction.GAUCHE.getDelta());
    }

    @Test
    void testDirectionOppose() {
        assertEquals(Direction.BAS, Direction.HAUT.directionOppose());
        assertEquals(Direction.HAUT, Direction.BAS.directionOppose());
        assertEquals(Direction.GAUCHE, Direction.DROITE.directionOppose());
        assertEquals(Direction.DROITE, Direction.GAUCHE.directionOppose());
    }

    @Test
    void testToString() {
        assertEquals("haut", Direction.HAUT.toString());
        assertEquals("bas", Direction.BAS.toString());
        assertEquals("droite", Direction.DROITE.toString());
        assertEquals("gauche", Direction.GAUCHE.toString());
    }
}