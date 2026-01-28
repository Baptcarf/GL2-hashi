package hashiGRP3.Logic.InOut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;

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

        System.out.println("\n=== Ponts ===");
        for (String ligne : lignesPonts) System.out.println(ligne);

        // à faire pour avoir le Hashi terminé

        return HashiGrille;
    }

    // Prend les nodes du fichier txt sous la forme : (x,y):nbPonts
    public static Ile ligneVersIle(String ligne) {
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

        // Prend x et y restant
        int x = Integer.parseInt(xy[0].trim());
        int y = Integer.parseInt(xy[1].trim());

        // Créer et retourner l'île
        return new Ile(new Coordonnees(x, y), nbPonts);
    }
}
