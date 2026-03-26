package hashiGRP3.Logic.Aide;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hashiGRP3.Logic.Coordonnees;
import hashiGRP3.Logic.Direction;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;

/**
 * Utilitaires pour l'analyse des graphes de ponts du Hashi.
 * Permet de détecter les composantes connexes
 */
public class GraphUtils {

    /**
     * Cree une copie profonde d'un Hashi
     * L'historique n'est pas copié car cette copie sert à la simulation logique
     *
     * @param original Le Hashi source
     * @return Un nouveau Hashi du source
     */
    public static Hashi clonerHashi(Hashi original) {
        Hashi clone = new Hashi();

        Map<Coordonnees, Ile> ilesCloneesParCoord = new HashMap<>();
        for (Ile ileOriginale : original.getIles()) {
            Coordonnees coord = ileOriginale.getCoordonnees();
            Coordonnees coordCopie = new Coordonnees(coord.x, coord.y);

            Ile ileClonee = new Ile(coordCopie, ileOriginale.getNbPontsRequis());
            clone.ajouterIle(ileClonee);
            ilesCloneesParCoord.put(coordCopie, ileClonee);
        }

        clone.initialisationToutLesPonts();
        clone.initialisationToutLesConflits();

        for (Pont pontOriginal : original.getPonts()) {
            Coordonnees coordA = pontOriginal.getileA().getCoordonnees();
            Coordonnees coordB = pontOriginal.getileB().getCoordonnees();

            Ile ileAClonee = ilesCloneesParCoord.get(new Coordonnees(coordA.x, coordA.y));
            Ile ileBClonee = ilesCloneesParCoord.get(new Coordonnees(coordB.x, coordB.y));

            if (ileAClonee == null || ileBClonee == null) {
                continue;
            }

            Pont pontClone = clone.getPont(ileAClonee, ileBClonee);
            if (pontClone == null) {
                continue;
            }

            pontClone.setEtatActuel(pontOriginal.getEtatActuel());
            pontClone.setEtatCorrect(pontOriginal.getEtatCorrect());
            pontClone.setEstHypothese(pontOriginal.isEstHypothese());
        }

        clone.setModeHypothese(original.getHypothese());
        clone.setErreur(original.EstEtatErreur());

        return clone;
    }

    /**
     * Trouve l'ensemble des iles atteignables à partir d'une ile donnée
     * en utilisant les ponts actifs (SIMPLE ou DOUBLE)
     * Effectue une BFS sur le graphe des ponts.
     * @param ileDépart l'ile de départ
     * @param hashi     le plateau de jeu
     * @return l'ensemble des iles atteignables incluant l'ile de départ
     */
    public static Set<Ile> composanteConnexe(Ile ileDépart, Hashi hashi) {
        Set<Ile> composante = new HashSet<>();
        Queue<Ile> file = new LinkedList<>();

        file.add(ileDépart);
        composante.add(ileDépart);

        while (!file.isEmpty()) {
            Ile ileActuelle = file.poll();

            // Parcourir tous les ponts de l'ile dans les 4 directions
            for (Direction direction : Direction.values()) {
                Pont pont = ileActuelle.getPont(direction);

                if (pont != null && estPontActif(pont)) {
                    // Trouver l'autre ile du pont
                    Ile autreIle = (pont.getileA() == ileActuelle) ? pont.getileB() : pont.getileA();

                    if (!composante.contains(autreIle)) {
                        composante.add(autreIle);
                        file.add(autreIle);
                    }
                }
            }
        }

        return composante;
    }

    /**
     * Vérifie que toutes les iles du plateau sont connexes.
     * Qu'il existe une bonne connexion entre toutes les iles
     *
     * @param hashi le plateau de jeu
     * @return true si toutes les iles forment une seule composante connexe
     */
    public static boolean estConnexe(Hashi hashi) {
        List<Ile> toutesLesIles = hashi.getIles();

        if (toutesLesIles.isEmpty()) {
            return true;
        }

        // Prendre une ile comme point de départ
        Ile ileDépart = toutesLesIles.get(0);
        Set<Ile> composante = composanteConnexe(ileDépart, hashi);

        // Vérifier si la composante contient toutes les iles
        return composante.size() == toutesLesIles.size();
    }

    /**
     * Determine si poser un pont crée un cycle.
     * Un pont crée un cycle si ses deux iles sont déjà dans la même composante connexe.
     *
     * @param pont  le pont à tester
     * @param hashi le plateau de jeu
     * @return true si poser ce pont connecte deux iles déjà dans la même composante
     */
    public static boolean creeraitCycle(Pont pont, Hashi hashi) {
        Ile ileA = pont.getileA();
        Ile ileB = pont.getileB();

        // Obtenir la composante connexe de l'ile A
        Set<Ile> composante = composanteConnexe(ileA, hashi);

        // Si l'ile B est déjà dans la composante de A, poser ce pont créerait un cycle
        return composante.contains(ileB);
    }

    /**
     * Vérifie si un pont est considéré comme actif pour la traversée du graphe.
     * Un pont actif est un pont en état SIMPLE ou DOUBLE.
     *
     * @param pont le pont à vérifier
     * @return true si le pont est actif (SIMPLE ou DOUBLE)
     */
    private static boolean estPontActif(Pont pont) {
        return pont.getEtatActuel() == EtatDuPont.SIMPLE || pont.getEtatActuel() == EtatDuPont.DOUBLE;
    }
}
