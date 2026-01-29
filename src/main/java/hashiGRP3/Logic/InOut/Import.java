package hashiGRP3.Logic.InOut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

public class Import {
    public static Hashi chargerFichier(Path chemin) throws IOException {
        List<String> txt = Files.readAllLines(chemin);
        List<String> lignesIles = new ArrayList<>();
        List<String> lignesPonts = new ArrayList<>();
        Hashi HashiGrille = new Hashi();

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

        // Vérification
        System.out.println("=== Iles ===");
        for (String ligne : lignesIles) System.out.println(ligne);

        for (String ligne : lignesIles) {
            Ile ile = ligneVersIle(ligne);  // transforme "(x,y):nbPonts" en Ile
            HashiGrille.ajouterIle(ile);        // ajoute dans l'objet Hashi
        }

        HashiGrille.initialisationToutLesPonts();

        System.out.println("\n=== Ponts ===");
        for (String ligne : lignesPonts) System.out.println(ligne);

        Set<Pont> pontsFinis = new HashSet<>();

        for (String ligne : lignesPonts) {
            Pont pont = ligneVersPont(ligne, HashiGrille);
            if (pont != null) {
                pontsFinis.add(pont); // ajouter uniquement si le pont existait
            }
        }

        return HashiGrille;
    }

    // Prend les nodes du fichier txt sous la forme : (x,y):nbPonts
    private static Ile ligneVersIle(String ligne) {
        // Supprime les espaces inutiles
        ligne = ligne.trim();

        // Split sur ":" pour séparer coordonnees et nb de ponts
        String[] parts = ligne.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Ligne invalide : " + ligne);
        }

        String coordPart = parts[0].trim();   // "(x,y)"
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

        // Prend x et y restant, le -1 car on à fait humainement les txt et pas avec index = 0
        int x = Integer.parseInt(xy[0].trim());
        int y = Integer.parseInt(xy[1].trim());

        // Créer et retourner l'île
        return new Ile(new Coordonnees(x, y), nbPonts);
    }

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
        ileStrings[0] = ileStrings[0].substring(1);       // enlever le "(" initial
        ileStrings[1] = ileStrings[1].substring(0, ileStrings[1].length()-1); // enlever le ")" final

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

        // ✅ Récupérer le pont déjà existant
        Pont pontExistant = hash.getPont(new Pont(ileA, ileB, EtatDuPont.VIDE));
        if (pontExistant == null) {
            System.err.println("⚠ Pont du fichier non trouvé dans la grille : "
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