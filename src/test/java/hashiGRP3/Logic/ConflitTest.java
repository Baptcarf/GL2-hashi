package hashiGRP3.Logic;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class ConflitTest {
    @Test
    public void testConflitListePontsUniquee() { //Test de la génération des conflit dans le cas où la liste de ponts ne contient qu'un seul élément  
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(0, 20), 2);
        Ile ile2 = new Ile(new Coordonnees(30, 20), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();
        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide
                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont entre ile1 et ile2 vide
    }

    @Test
    public void testConflitCroisementMultiple() { //Test de la génération des conflit dans le cas où un pont croise plusieurs ponts
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(0, 20), 2);
        Ile ile2 = new Ile(new Coordonnees(30, 20), 2);
        Ile ile3 = new Ile(new Coordonnees(10, 0), 2);
        Ile ile4 = new Ile(new Coordonnees(10, 40), 2);
        Ile ile5 = new Ile(new Coordonnees(20, 10), 2);
        Ile ile6 = new Ile(new Coordonnees(20, 30), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.ajouterIle(ile5);
        hashi.ajouterIle(ile6);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().contains(ile3.getPont(hashiGRP3.Logic.Direction.BAS)) &&
                   ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().contains(ile5.getPont(hashiGRP3.Logic.Direction.BAS)) &&
                   ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().size() == 2 && //teste que la liste des conflit du pont entre ile1 et ile2 contient bien les deux ponts qu'il croise

                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().contains(ile4.getPont(hashiGRP3.Logic.Direction.HAUT)) &&
                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().contains(ile6.getPont(hashiGRP3.Logic.Direction.HAUT)) &&
                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().size() == 2 && //teste que la liste des conflit du pont entre ile1 et ile2 contient bien les deux ponts qu'il croise

                   ile3.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().contains(ile1.getPont(hashiGRP3.Logic.Direction.DROITE)) &&
                   ile3.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().size() == 1 &&    //teste que la liste des conflit du pont entre ile3 et ile4 contient bien uniquement le pont entre ile1 et ile2

                   ile4.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().contains(ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE)) &&
                   ile4.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().size() == 1 &&   //teste que la liste des conflit du pont entre ile3 et ile4 contient bien uniquement le pont entre ile1 et ile2

                   ile5.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().contains(ile1.getPont(hashiGRP3.Logic.Direction.DROITE)) &&
                   ile5.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().size() == 1 &&    //teste que la liste des conflit du pont entre ile5 et ile6 contient bien uniquement le pont entre ile1 et ile2
                   
                   ile6.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().contains(ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE)) &&
                   ile6.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().size() == 1);    //teste que la liste des conflit du pont entre ile5 et ile6 contient bien uniquement le pont entre ile1 et ile2
    }

    @Test
    public void testConflitCroisementSimple() { //Test de la génération des conflit dans le cas où un pont croise un seul autre pont
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(1, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(1, 2), 2);
        Ile ile3 = new Ile(new Coordonnees(0, 1), 2);
        Ile ile4 = new Ile(new Coordonnees(2, 1), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();
        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().contains(ile3.getPont(hashiGRP3.Logic.Direction.DROITE)) &&
                   ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().size() == 1 && //teste que la liste des conflit du pont entre ile1 et ile2 contient bien uniquement le pont entre ile3 et ile4

                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().contains(ile4.getPont(hashiGRP3.Logic.Direction.GAUCHE)) &&
                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().size() == 1 && //teste que la liste des conflit du pont de ile1 et ile2 contient bien uniquement le pont entre ile3 et ile4

                   ile3.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().contains(ile1.getPont(hashiGRP3.Logic.Direction.BAS)) &&
                   ile3.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().size() == 1 && //teste que la liste des conflit du pont de ile3 et ile4 contient bien uniquement le pont entre ile1 et ile2

                   ile4.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().contains(ile2.getPont(hashiGRP3.Logic.Direction.HAUT)) &&
                   ile4.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().size() == 1);   //teste que la liste des conflit du pont de ile3 et ile4 contient bien uniquement le pont entre ile1 et ile2
    }

    @Test
    public void testConflitPontParallele() { //Test de la génération des conflit dans le cas où un pont est parallèle à un autre pont
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Ile ile3 = new Ile(new Coordonnees(3, 1), 2);
        Ile ile4 = new Ile(new Coordonnees(3, 5), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide

                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile1 et ile2 est bien vide

                   ile3.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile3 et ile4 est bien vide

                   ile4.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont de ile3 et ile4 est bien vide
    }

    @Test
    public void testConflitIleCommune(){//Test de la génération des conflit dans le cas où deux ponts partagent une même île
         Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(0, 0), 4);
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2);
        Ile ile3 = new Ile(new Coordonnees(5, 0), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide
                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 vide
                   ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile3 est bien vide
                   ile3.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true); //teste que la liste des conflit du pont entre ile1 et ile3 vide
    }

    @Test //Test de la génération des conflit dans le cas où un pont est perpendiculaire à un autre pont sans pour autant le croiser: cas 1
    public void testConflitPontPerpendiculaireSansCroisement1(){ //Pont vertical à gauche du pont horizontal
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(3, 1), 2);
        Ile ile2 = new Ile(new Coordonnees(3, 5), 2);
        Ile ile3 = new Ile(new Coordonnees(5, 3), 2);
        Ile ile4 = new Ile(new Coordonnees(9, 3), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide

                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile1 et ile2 est bien vide

                   ile3.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile3 et ile4 est bien vide

                   ile4.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont de ile3 et ile4 est bien vide
    }

    @Test //Test de la génération des conflit dans le cas où un pont est perpendiculaire à un autre pont sans pour autant le croiser: cas 2
    public void testConflitPontPerpendiculaireSansCroisement2(){ //Pont vertical à droite du pont horizontal
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(7, 3), 2);
        Ile ile2 = new Ile(new Coordonnees(7, 10), 2);
        Ile ile3 = new Ile(new Coordonnees(2, 6), 2);
        Ile ile4 = new Ile(new Coordonnees(5, 6), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide

                   ile2.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile1 et ile2 est bien vide

                   ile3.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile3 et ile4 est bien vide

                   ile4.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont de ile3 et ile4 est bien vide
    }

    @Test //Test de la génération des conflit dans le cas où un pont est perpendiculaire à un autre pont sans pour autant le croiser: cas 3
    public void testConflitPontPerpendiculaireSansCroisement3(){ //Pont vertical au dessus du pont horizontal
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(3, 6), 2);
        Ile ile2 = new Ile(new Coordonnees(8, 6), 2);
        Ile ile3 = new Ile(new Coordonnees(10, 3), 2);
        Ile ile4 = new Ile(new Coordonnees(10, 8), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide

                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile1 et ile2 est bien vide

                   ile3.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile3 et ile4 est bien vide

                   ile4.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont de ile3 et ile4 est bien vide
    }

    @Test //Test de la génération des conflit dans le cas où un pont est perpendiculaire à un autre pont sans pour autant le croiser: cas 4
    public void testConflitPontPerpendiculaireSansCroisement4(){ //Pont vertical en dessous du pont horizontal
        Hashi hashi = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(5, 11), 2);
        Ile ile2 = new Ile(new Coordonnees(10, 11), 2);
        Ile ile3 = new Ile(new Coordonnees(7, 13), 2);
        Ile ile4 = new Ile(new Coordonnees(7, 19), 2);
        hashi.ajouterIle(ile1);
        hashi.ajouterIle(ile2);
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();

        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.DROITE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont entre ile1 et ile2 est bien vide

                   ile2.getPont(hashiGRP3.Logic.Direction.GAUCHE).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile1 et ile2 est bien vide

                   ile3.getPont(hashiGRP3.Logic.Direction.BAS).getConflits().isEmpty() == true && //teste que la liste des conflit du pont de ile3 et ile4 est bien vide

                   ile4.getPont(hashiGRP3.Logic.Direction.HAUT).getConflits().isEmpty() == true);  //teste que la liste des conflit du pont de ile3 et ile4 est bien vide
    }
}
