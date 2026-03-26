//Attribut au paquet
package hashiGRP3.Logic.InOut;

//Import
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;
import hashiGRP3.Logic.General;

/**
 * Classe pour importer une grille Hashi depuis un fichier texte.
 * Exemple de format du fichier texte :
 * (1,1):2
 * (1,5):2
 * -
 * (1,1)(1,5):2
 */
public class Import {
    private Import() {
        // classe utilitaire, pas d'instance
    }

    /**
     * Charge une grille Hashi depuis un fichier texte.
     *
     * @param chemin chemin du fichier à charger
     * @return la grille Hashi importée
     * @throws IOException en cas d'erreur de lecture du fichier
     */
    public static Hashi chargerFichier(Path chemin) throws IOException {
        List<String> txt = Files.readAllLines(chemin); // lire toutes les lignes du fichier
        List<String> lignesIles = new ArrayList<>(); // stocke les lignes des îles (nodes)
        List<String> lignesPonts = new ArrayList<>(); // stocke les lignes des ponts (edges)
        Hashi HashiGrille = new Hashi();

        separerLignes(txt, lignesIles, lignesPonts);
        ajouterIles(HashiGrille, lignesIles);
        HashiGrille.initialisationToutLesPonts();
        ajouterPonts(HashiGrille, lignesPonts);

        General.getDb().creerGrille(General.getNum_grille(), chemin.toString(), lignesIles.size());

        return HashiGrille;
    }

    /**
     * Ajoute les ponts à la grille Hashi à partir des lignes du fichier.
     *
     * @param hashi       la grille Hashi à compléter
     * @param lignesPonts les lignes décrivant les ponts
     */
    private static void ajouterPonts(Hashi hashi, List<String> lignesPonts) {
        for (String ligne : lignesPonts) {
            ligneVersPont(ligne, hashi); // transforme la ligne en Pont et applique l'état dans Hashi
        }
    }

    /**
     * Ajoute les îles à la grille Hashi à partir des lignes du fichier.
     *
     * @param hashi      la grille Hashi à compléter
     * @param lignesIles les lignes décrivant les îles
     */
    private static void ajouterIles(Hashi hashi, List<String> lignesIles) {
        for (String ligne : lignesIles) {
            Ile ile = ligneVersIle(ligne); // transforme "(x,y):nbPonts" en Ile
            hashi.ajouterIle(ile); // ajoute dans l'objet Hashi
        }
    }

    /**
     * Sépare les lignes du fichier en lignes d'îles et lignes de ponts.
     *
     * @param txt         toutes les lignes lues depuis le fichier
     * @param lignesIles  liste où stocker les lignes des îles
     * @param lignesPonts liste où stocker les lignes des ponts
     */
    private static void separerLignes(List<String> txt, List<String> lignesIles, List<String> lignesPonts) {
        boolean apresTiret = false;

        for (String ligne : txt) {
            ligne = ligne.trim(); // enlever les espaces inutiles

            if (ligne.equals("-")) {
                apresTiret = true;
                continue; // ne pas ajouter le tiret
            }

            if (!apresTiret) {
                lignesIles.add(ligne);
            } else {
                lignesPonts.add(ligne);
            }
        }
    }

    /**
     * Transforme une ligne de fichier représentant une île en objet Ile.
     * La ligne doit être au format "(x,y):nbPonts".
     *
     * @param ligne la ligne à convertir
     * @return l'objet Ile correspondant
     * @throws IllegalArgumentException si le format est invalide
     */
    private static Ile ligneVersIle(String ligne) {
        // Supprime les espaces inutiles
        ligne = ligne.trim();

        // Split sur ":" pour séparer coordonnees et nb de ponts
        String[] parts = ligne.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Ligne invalide : " + ligne);
        }

        String coordPart = parts[0].trim(); // "(x,y)"
        int nbPonts = Integer.parseInt(parts[1].trim()); // ":p"

        // Supprimer les parenthèses et récupérer x et y
        if (!coordPart.startsWith("(") || !coordPart.endsWith(")")) {
            throw new IllegalArgumentException("Format des coordonnées invalide : " + coordPart);
        }
        coordPart = coordPart.substring(1, coordPart.length() - 1);

        String[] xy = coordPart.split(",");
        if (xy.length != 2) {
            throw new IllegalArgumentException("Coordonnées invalides : " + coordPart);
        }

        // Prend x et y restant, le -1 car on à fait humainement les txt et pas avec
        // index = 0
        int x = Integer.parseInt(xy[0].trim());
        int y = Integer.parseInt(xy[1].trim());

        return new Ile(new Coordonnees(x, y), nbPonts);
    }

    /**
     * Transforme une ligne de fichier représentant un pont en objet Pont
     * et applique l'état correct dans la grille Hashi.
     * La ligne doit être au format "(x1,y1)(x2,y2):nbPonts".
     *
     * @param ligne la ligne à convertir
     * @param hash  la grille Hashi contenant les îles et ponts
     * @return le pont mis à jour, ou null si le pont n'existe pas dans la grille
     * @throws IllegalArgumentException si la ligne ou les îles sont invalides
     */
    private static Pont ligneVersPont(String ligne, Hashi hash) {
        // Exemple de ligne : "(1,1)(1,5):2"
        ligne = ligne.trim();

        // Séparer les coordonnées des îles et le nombre de ponts
        String[] parts = ligne.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Ligne pont invalide : " + ligne);
        }

        String coordPart = parts[0].trim(); // "(x1,y1)(x2,y2)"
        int nbPonts = Integer.parseInt(parts[1].trim()); // ":2"

        // Split "(x1,y1)(x2,y2)" en deux strings "(x1,y1)" et "(x2,y2)"
        String[] ileStrings = coordPart.split("\\)\\(");
        ileStrings[0] = ileStrings[0].substring(1); // enlever le "(" initial
        ileStrings[1] = ileStrings[1].substring(0, ileStrings[1].length() - 1); // enlever le ")" final

        // Convertir en coordonnées
        String[] xy1 = ileStrings[0].split(",");
        String[] xy2 = ileStrings[1].split(",");
        int x1 = Integer.parseInt(xy1[0].trim());
        int y1 = Integer.parseInt(xy1[1].trim());
        int x2 = Integer.parseInt(xy2[0].trim());
        int y2 = Integer.parseInt(xy2[1].trim());

        // Récupérer les objets Ile depuis le Hashi
        Ile ileA = hash.getIle(x1, y1);
        Ile ileB = hash.getIle(x2, y2);
        if (ileA == null || ileB == null) {
            throw new IllegalArgumentException("Ile non trouvée pour le pont : " + ligne);
        }

        // Récupérer le pont déjà existant
        Pont pontExistant = hash.getPont(new Pont(ileA, ileB, EtatDuPont.VIDE));
        if (pontExistant == null) {
            System.err.println("Pont du fichier non trouvé dans la grille : "
                    + ileA.getCoordonnees() + " <-> " + ileB.getCoordonnees());
            return null; // on renvoie null pour ne pas l'ajouter
        }

        // Déterminer l'état du pont à appliquer
        EtatDuPont etat = switch (nbPonts) {
            case 1 -> EtatDuPont.SIMPLE;
            case 2 -> EtatDuPont.DOUBLE;
            default -> EtatDuPont.VIDE;
        };

        // On applique l'état correct
        pontExistant.setEtatCorrect(etat);

        return pontExistant;
    }
}
