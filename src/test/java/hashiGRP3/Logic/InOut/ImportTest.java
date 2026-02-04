package hashiGRP3.Logic.InOut;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class ImportTest {

    // Contenu d'exemple fourni par l'utilisateur
    private static final List<String> SAMPLE = List.of(
        "(0,0):4",
        "(0,4):3",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );
    
    // Problème de parenthèse
    private static final List<String> SAMPLE2 = List.of(
        "(0,0):4",
        "(0,4:3",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );

    
    // Problème de parenthèse V2
    private static final List<String> SAMPLE2V2 = List.of(
        "(0,0):4",
        "0,4):3",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );

    // 3 coordonnées données au lieu de 2
    private static final List<String> SAMPLE3 = List.of(
        "(0,0):4",
        "(0,4,3):3",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );   
    
    // 3 arguments séparé par des ':' au lieu de 2
    private static final List<String> SAMPLE4 = List.of(
        "(0,0):4",
        "(0,4):3:5",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );

    // 3 arguments séparé par des ':' au lieu de 2 (pour la deuxième partie du txt)
    private static final List<String> SAMPLE5 = List.of(
        "(0,0):4",
        "(0,4):3",
        "(0,6):2",
        "(2,0):5",
        "(2,5):4",
        "(4,0):5",
        "(4,4):2",
        "(5,1):1",
        "(5,5):3",
        "(6,0):2",
        "(6,3):1",
        "(6,6):2",
        "-",
        "(0,0)(0,4):2",
        "(0,0)(2,0):2",
        "(0,4)(0,6):1",
        "(0,6)(6,6):1",
        "(2,0)(2,5):2",
        "(2,0)(4,0):1:SkibidiSigmaToiletRatio",
        "(2,5)(5,5):2",
        "(4,0)(4,4):2",
        "(4,0)(6,0):2",
        "(5,1)(5,5):1",
        "(6,3)(6,6):1"
    );

    @Test
    public void testChargerFichier_sample_shouldLoadIlesEtPonts() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample", ".txt");
        try {
            Files.write(tmp, SAMPLE);

            Hashi hashi = Import.chargerFichier(tmp);
            assertNotNull(hashi, "L'import doit retourner un objet Hashi non nul");

            // Vérifier quelques îles connues
            Ile ile00 = hashi.getIle(0, 0);
            Ile ile55 = hashi.getIle(5, 5);
            assertNotNull(ile00, "Ile (0,0) doit être présente");
            assertNotNull(ile55, "Ile (5,5) doit être présente");

            // Vérifier l'état de quelques ponts fournis dans le fichier
            Pont p1 = hashi.getPont(new Pont(ile00, hashi.getIle(0, 4), EtatDuPont.VIDE));
            assertNotNull(p1, "Pont (0,0)-(0,4) doit exister");
            assertEquals(EtatDuPont.DOUBLE, p1.getEtatCorrect(), "Le pont (0,0)-(0,4) doit être double (2)");

            Pont p2 = hashi.getPont(new Pont(hashi.getIle(0, 4), hashi.getIle(0, 6), EtatDuPont.VIDE));
            assertNotNull(p2, "Pont (0,4)-(0,6) doit exister");
            assertEquals(EtatDuPont.SIMPLE, p2.getEtatCorrect(), "Le pont (0,4)-(0,6) doit être simple (1)");

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testChargerFichier_mauvaiseLigneIle_doThrowIllegalArgumentException() throws IOException {
        Path tmp = Files.createTempFile("hashi_bad", ".txt");
        try {
            // Ligne d'île invalide (il manque le ':'), doit provoquer une IllegalArgumentException
            List<String> bad = List.of("(0,0)4");
            Files.write(tmp, bad);

            assertThrows(IllegalArgumentException.class, () -> {
                Import.chargerFichier(tmp);
            });

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testChargerFichier_sample2_missingParenthesis_shouldThrow() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample2", ".txt");
        try {
        Files.write(tmp, SAMPLE2);


        // La ligne "0,4):3" ne commence pas par '(', on attend IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Import.chargerFichier(tmp));


        } finally {
        Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testChargerFichier_sample2v2_missingParenthesis_shouldThrow() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample2v2", ".txt");
        try {
        Files.write(tmp, SAMPLE2V2);


        // La ligne "0,4):3" ne commence pas par '(', on attend IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Import.chargerFichier(tmp));


        } finally {
        Files.deleteIfExists(tmp);
        }
    }


    @Test
    public void testChargerFichier_sample3_threeCoordinates_shouldThrow() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample3", ".txt");
        try {
            Files.write(tmp, SAMPLE3);


        // La ligne "(0,4,3):3" contient 3 valeurs séparées par des virgules -> IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> Import.chargerFichier(tmp));

        }finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testChargerFichier_sample4_tooManyColonArguments_shouldThrow() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample4", ".txt");
        try {
            Files.write(tmp, SAMPLE4);

            // "(0,4):3:5" -> split(":") donne 3 parties => IllegalArgumentException attendue
            assertThrows(IllegalArgumentException.class, () -> {
                Import.chargerFichier(tmp);
            });

        } finally {
            Files.deleteIfExists(tmp);
        }
    }
    @Test
    public void testChargerFichier_sample4_tooManyColonArguments_shouldThrow2() throws IOException {
        Path tmp = Files.createTempFile("hashi_sample5", ".txt");
        try {
            Files.write(tmp, SAMPLE5);

            // "(0,4):3:SkibidiToiletSigmaRatio" -> split(":") donne 3 parties => IllegalArgumentException attendue
            assertThrows(IllegalArgumentException.class, () -> {
                Import.chargerFichier(tmp);
            });

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testLigneVersPont_ileNonTrouvee_shouldThrow() throws IOException {
        List<String> data = List.of(
            "(0,0):1",
            "(1,0):1",
            "-",
            "(99,99)(0,0):1" // île inexistante
        );

        Path tmp = Files.createTempFile("hashi_missing_ile", ".txt");
        try {
            Files.write(tmp, data);

            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Import.chargerFichier(tmp)
            );

            assertTrue(
                ex.getMessage().contains("Ile non trouvée"),
                "Le message doit indiquer qu'une île est introuvable"
            );

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testLigneVersPont_ileNonTrouvee_shouldThrowv2() throws IOException {
        List<String> data = List.of(
            "(0,0):1",
            "(1,0):1",
            "-",
            "(0,0)(99,99):1" // île inexistante
        );

        Path tmp = Files.createTempFile("hashi_missing_ilev2", ".txt");
        try {
            Files.write(tmp, data);

            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Import.chargerFichier(tmp)
            );

            assertTrue(
                ex.getMessage().contains("Ile non trouvée"),
                "Le message doit indiquer qu'une île est introuvable"
            );

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testLigneVersPont_nbPontsDefault_shouldSetVide() throws IOException {
        List<String> data = List.of(
            "(0,0):5",
            "(0,1):5",
            "-",
            "(0,0)(0,1):5" // nbPonts invalide
        );

        Path tmp = Files.createTempFile("hashi_default_state", ".txt");
        try {
            Files.write(tmp, data);

            Hashi hashi = Import.chargerFichier(tmp);

            Pont p = hashi.getPont(
                new Pont(
                    hashi.getIle(0,0),
                    hashi.getIle(0,1),
                    EtatDuPont.VIDE
                )
            );

            assertNotNull(p, "Le pont doit exister");
            assertEquals(
                EtatDuPont.VIDE,
                p.getEtatCorrect(),
                "Un nbPonts différent de 1 ou 2 doit donner VIDE"
            );

        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    @Test
    public void testLigneVersPont_pontExistantNull_shouldPrintErrorAndIgnore() throws IOException {
        List<String> data = List.of(
            "(0,0):1",
            "(0,1):1", // île intermédiaire bloqueuse
            "(0,2):1",
            "-",
            "(0,0)(0,2):1" // pont impossible car île au milieu
        );

        Path tmp = Files.createTempFile("hashi_pont_null", ".txt");

        var errContent = new java.io.ByteArrayOutputStream();
        var originalErr = System.err;
        System.setErr(new java.io.PrintStream(errContent));

        try {
            Files.write(tmp, data);

            Hashi hashi = Import.chargerFichier(tmp);
            assertNotNull(hashi);

            // Message d'erreur attendu
            assertTrue(
                errContent.toString().contains("Pont du fichier non trouvé dans la grille"),
                "Un message d'erreur doit être affiché si le pont n'existe pas"
            );

            // Le pont ne doit pas exister
            assertNull(
                hashi.getPont(new Pont(
                    hashi.getIle(0,0),
                    hashi.getIle(0,2),
                    EtatDuPont.VIDE
                ))
            );

        } finally {
            System.setErr(originalErr);
            Files.deleteIfExists(tmp);
        }
    }

}
