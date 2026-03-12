package hashiGRP3.Logic;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import hashiGRP3.Logic.InOut.Import;


public class HashiTest {
    @Test
    public void testConflitListePontsUniquee() { //Test de la génération des conflits dans le cas où la liste de ponts ne contient qu'un seul élément  
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class HashiTest {
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

    @Test //Test de la génération des conflits dans le cas où l'île A de chaque ponts ont une seule coordonnée commune
    public void testConflitCoordonneeCommune1(){ 
        Hashi hashi = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(2, 4), 2); 
        Ile ile3 = new Ile(new Coordonnees(3, 4), 2); 
        Ile ile4 = new Ile(new Coordonnees(4, 4), 2);
        hashi.ajouterIle(ile1); 
        hashi.ajouterIle(ile2); 
        hashi.ajouterIle(ile3);
        hashi.ajouterIle(ile4);
        hashi.initialisationToutLesPonts();
        hashi.initialisationToutLesConflits();
        assertTrue(ile1.getPont(Direction.BAS).getConflits().size()==0 &&
                   ile2.getPont(Direction.HAUT).getConflits().size()==0 &&
                   ile3.getPont(Direction.DROITE).getConflits().size()==0 &&
                   ile4.getPont(Direction.GAUCHE).getConflits().size()==0);
    }


    @Test //Test de estGagne dans le cas où la grille ne contient aucune île
    public void testEstGagneGrilleVide(){
        Hashi h = new Hashi();
        assertTrue(h.estGagne());
    }

    @Test //Test de estGagne dans le cas où la grille est bien complete, mais avec 2 iles
    public void testEstGagneGrilleCompleteEt2Iles(){ 
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2); 
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.DOUBLE); 
        assertTrue(h.estGagne()); 
    } 

    @Test //Test de estGagne dans le cas où la grille est bien complete, mais avec plus de 2 iles
    public void testEstGagneGrilleCompleteEtPlusDe2Iles(){ 
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 4); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 2); 
        Ile ile3 = new Ile(new Coordonnees(2, 0), 2);
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.ajouterIle(ile3);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.DOUBLE); 
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatActuel(EtatDuPont.DOUBLE); 
        assertTrue(h.estGagne()); 
    } 

    @Test //Test de estGagne dans le cas où la grille ne possède pas assez de ponts, mais que 2 iles
    public void testEstGagneGrilleIncorrecteInfEt2Iles(){
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 1);
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.SIMPLE);
        assertFalse(h.estGagne());
    }

    @Test //Test de estGagne dans le cas où la grille ne possède pas assez de ponts, mais avec plus de 2 iles
    public void testEstGagneGrilleIncorrecteInfEtPlusDe2Iles(){
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 1);
        Ile ile3 = new Ile(new Coordonnees(2, 0), 1);
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.ajouterIle(ile3);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.SIMPLE);
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatCorrect(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatActuel(EtatDuPont.SIMPLE);
        assertFalse(h.estGagne());
    }

    @Test //Test de estGagne dans le cas où la grille ne possède trop de ponts, mais avec 2 iles
    public void testEstGagneGrilleIncorrecteSupEt2Iles(){
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 1); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 1);
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.SIMPLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.DOUBLE);
        assertFalse(h.estGagne());
    }

    @Test //Test de estGagne dans le cas où la grille ne possède trop de ponts, mais avec plus de 2 iles
    public void testEstGagneGrilleIncorrecteSupEtPlusDe2Iles(){
        Hashi h = new Hashi(); 
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 1);
        Ile ile3 = new Ile(new Coordonnees(2, 0), 1);
        h.ajouterIle(ile1); 
        h.ajouterIle(ile2);
        h.ajouterIle(ile3);
        h.initialisationToutLesPonts();
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatCorrect(EtatDuPont.SIMPLE);
        ile1.getPont(hashiGRP3.Logic.Direction.BAS).setEtatActuel(EtatDuPont.DOUBLE);
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatCorrect(EtatDuPont.SIMPLE);
        ile1.getPont(hashiGRP3.Logic.Direction.DROITE).setEtatActuel(EtatDuPont.DOUBLE);
        assertFalse(h.estGagne());
    }

    @Test //Test de jouer() dans un cas possible
    public void testJouer(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(0, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(0, 2), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        h.jouer(ile1.getPont(hashiGRP3.Logic.Direction.BAS));
        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getEtatActuel()==EtatDuPont.SIMPLE);
    }

    @Test //Test de jouer() dans un cas où le pont qui vient d'être tenté de jouer entre en conflit avec un autre
    public void testJouerCasConflit(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        Ile ile3 = new Ile(new Coordonnees(0, 2), 2);
        Ile ile4 = new Ile(new Coordonnees(4, 2), 2);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.ajouterIle(ile3);
        h.ajouterIle(ile4);
        h.initialisationToutLesPonts();
        h.initialisationToutLesConflits();
        ile3.getPont(Direction.DROITE).setEtatActuel(EtatDuPont.SIMPLE);
        h.jouer(ile1.getPont(hashiGRP3.Logic.Direction.BAS));
        assertTrue(ile1.getPont(hashiGRP3.Logic.Direction.BAS).getEtatActuel()==EtatDuPont.VIDE);
    }

    @Test //Test de Undo => Cas où aucune actions n'a été faite avant
    public void testUndoVide(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        assertFalse(h.undo());
    }

    @Test //Test de Undo => Cas ou une action a été faite avant
    public void testUndo(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        h.jouer(ile1.getPont(Direction.BAS));
        assertTrue(h.undo());
        assertTrue(ile1.getPont(Direction.BAS).getEtatActuel()==EtatDuPont.VIDE);
    }

    @Test //Test de Redo => Cas où aucune actions n'a été annulée avant
    public void testRedoVide(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        assertFalse(h.redo());
        assertTrue(ile1.getPont(Direction.BAS).getEtatActuel()==EtatDuPont.VIDE);
    }

    @Test //Test de Undo => Cas ou une action a été annulée avant
    public void testRedo(){
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2); 
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        h.jouer(ile1.getPont(Direction.BAS));
        h.undo();
        assertTrue(h.redo());
        assertTrue(ile1.getPont(Direction.BAS).getEtatActuel()==EtatDuPont.SIMPLE);
    }

    @Test //Test de getPonts => Cas où la liste des ponts est vide
    public void testgetPontsVide(){ 
        Hashi h = new Hashi(); 
        assertTrue(h.getPonts().isEmpty()); } 
    
    @Test //Test de getPonts => Cas où la liste des ponts contient des ponts 
    public void testgetPonts(){ 
        Hashi h = new Hashi();
        Ile ile1 = new Ile(new Coordonnees(2, 0), 2);
        Ile ile2 = new Ile(new Coordonnees(2, 4), 1);
        h.ajouterIle(ile1);
        h.ajouterIle(ile2);
        h.initialisationToutLesPonts();
        assertTrue(h.getPonts().contains(ile1.getPont(Direction.BAS)) && 
                   h.getPonts().contains(ile2.getPont(Direction.HAUT)) && 
                   h.getPonts().size()==1); 
    }

    
    @Test
    public void testToStringAvecFichier() throws IOException {
        // On charge le fichier comme dans ton main
        Path chemin = Path.of("src/main/java/hashiGRP3/Ressources/10x10/hashi2.txt");
        Hashi hashi = Import.chargerFichier(chemin);

        // Initialisation des conflits pour parcourir le maximum du code
        hashi.initialisationToutLesConflits();

        for (Pont pont : hashi.getPonts()){
            pont.setEtatActuel(pont.getEtatCorrect());
        }
        // On appelle toString() pour le parcourir
        String s = hashi.toString();

        // Vérifie juste que la chaîne n'est pas nulle ou vide
        assert s != null && !s.isEmpty();
    }
}
