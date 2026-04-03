//Attribut au paquet
package hashiGRP3.Logic;

import java.util.List;

/**
 * Fournit les scénarios de tutoriel pour chaque grille.
 * Chaque scénario est une liste d'étapes (EtapeTutoriel).
 * Les conditions peuvent être ajoutées au fur et à mesure.
 */
public class ScenarioTutoriel {

    /**
     * Retourne la liste des étapes pour un numéro de grille tutoriel donné.
     *
     * @param numGrille le numéro de la grille (0 à 14)
     * @return la liste des étapes du scénario, ou une liste vide si non défini
     */
    public static List<EtapeTutoriel> getEtapes(int numGrille) {
        return switch (numGrille) {
            case 0  -> etapesHashi0();
            case 1  -> etapesHashi1();
            case 2  -> etapesHashi2();
            case 3  -> etapesHashi3();
            case 4  -> etapesHashi4();
            case 5  -> etapesHashi5();
            case 6  -> etapesHashi6();
            case 7  -> etapesHashi7();
            case 8  -> etapesHashi8();
            case 9  -> etapesHashi9();
            case 10 -> etapesHashi10();
            case 11 -> etapesHashi11();
            case 12 -> etapesHashi12();
            case 13 -> etapesHashi13();
            case 14 -> etapesHashi14();
            default -> List.of();
        };
    }

    // -------------------------------------------------------------------------
    // HASHI 0 — Règles du jeu
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi0() {
        return List.of(

            new EtapeTutoriel(
                "Bienvenue dans Hashi !",
                "Hashi est un puzzle japonais.  " +
                "Le plateau contient des îles numérotées. " +
                "Votre objectif : relier toutes les îles par des ponts " +
                "de façon à ce que chaque île ait exactement " +
                "autant de ponts que son numéro l'indique.  " +
                "Cliquez sur Suivant pour continuer."
            ),

            new EtapeTutoriel(
                "Les ponts",
                "Un pont relie deux îles voisines " +
                "(horizontalement ou verticalement).  " +
                "Un premier clic place un pont simple (─). " +
                "Un second clic place un pont double (═). " +
                "Un troisième clic supprime le pont.  " +
                "Les ponts ne peuvent pas se croiser !  " +
                "Cliquez sur Suivant pour continuer."
            ),

            new EtapeTutoriel(
                "À vous de jouer !",
                "L'île en (0,2) a la valeur 2. " +
                "L'île en (2,2) a la valeur 4.  " +
                "Placez un pont DOUBLE entre ces deux îles " +
                "(cliquez deux fois entre (0,2) et (2,2)).",
                hashi -> {
                    Ile ileA = hashi.getIle(0, 2);
                    Ile ileB = hashi.getIle(2, 2);
                    if (ileA == null || ileB == null) return false;
                    Pont pont = hashi.getPont(ileA, ileB);
                    return pont != null && pont.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Bien joué !",
                "L'île en (2,0) a la valeur 1. " +
                "Elle n'a qu'un seul voisin : l'île en (2,2).  " +
                "Placez un pont SIMPLE entre (2,0) et (2,2).",
                hashi -> {
                    Ile ileA = hashi.getIle(2, 0);
                    Ile ileB = hashi.getIle(2, 2);
                    if (ileA == null || ileB == null) return false;
                    Pont pont = hashi.getPont(ileA, ileB);
                    return pont != null && pont.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Dernière étape !",
                "L'île en (2,4) a la valeur 1. " +
                "Placez un pont SIMPLE entre (2,2) et (2,4) " +
                "pour terminer la grille.",
                hashi -> {
                    Ile ileA = hashi.getIle(2, 2);
                    Ile ileB = hashi.getIle(2, 4);
                    if (ileA == null || ileB == null) return false;
                    Pont pont = hashi.getPont(ileA, ileB);
                    return pont != null && pont.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez terminé la grille !  " +
                "Toutes les îles sont connectées avec " +
                "le bon nombre de ponts.  " +
                "Vous êtes prêt pour les vrais tutoriels !"
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 1 — Îles avec 4 dans le coin, 6 sur le côté et 8 au milieu
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi1() {
        return List.of(

            new EtapeTutoriel(
                "Technique : saturation maximale",
                "Certaines îles ont un nombre de ponts " +
                "si élevé qu'on peut déduire directement " +
                "quels ponts placer.  " +
                "Une île de valeur 4 dans un coin n'a " +
                "que 2 voisins : elle a forcément " +
                "2 ponts doubles vers chacun d'eux !  " +
                "Cliquez sur Suivant pour commencer."
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 4",
                "L'île en (0,0) vaut 4. " +
                "Ses seuls voisins sont (0,2) et (2,0).  " +
                "Elle doit avoir 4 ponts au total, " +
                "répartis sur 2 voisins : " +
                "→ 2 ponts doubles obligatoires !  " +
                "Placez un pont double entre " +
                "(0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — suite",
                "Parfait ! Maintenant placez un pont " +
                "double entre (0,0) et (2,0).  " +
                "L'île (0,0) sera alors complète.",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 4",
                "Même raisonnement pour l'île (0,4). " +
                "Ses voisins sont (0,2) et (2,4).  " +
                "Placez un pont double entre " +
                "(0,4) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,4), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — suite",
                "Placez maintenant un pont double " +
                "entre (0,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 4",
                "L'île (2,0) vaut 4. " +
                "Ses voisins sont (0,0) et (2,2). " +
                "(0,0)-(2,0) est déjà double.  " +
                "Placez un pont double entre " +
                "(2,0) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "L'île (2,4) vaut 4. " +
                "Ses voisins sont (0,4) et (2,2). " +
                "(0,4)-(2,4) est déjà double.  " +
                "Placez un pont double entre " +
                "(2,4) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 6",
                "L'île (0,2) vaut 6. " +
                "Elle a 3 voisins : (0,0), (0,4), (2,2). " +
                "2 ponts sont déjà placés vers (0,0) " +
                "et 2 vers (0,4).  " +
                "Il reste 2 ponts à placer. " +
                "Placez un pont double entre " +
                "(0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "Dernière île : (4,2) vaut 2. " +
                "Son seul voisin est (2,2).  " +
                "Placez un pont double entre " +
                "(4,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez terminé ce tutoriel !  " +
                "Retenez la technique : " +
                "une île dont la valeur est égale " +
                "au double de son nombre de voisins " +
                "a forcément des ponts doubles " +
                "vers chacun d'eux."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi2() {
        return List.of(

            new EtapeTutoriel(
                "Technique : île avec un seul voisin",
                "Quand une île n'a qu'un seul voisin, " +
                "tous ses ponts vont forcément vers lui. " +
                "Repérez ces îles en premier !"
            ),

            new EtapeTutoriel(
                "Île (3,0) — valeur 1",
                "L'île (3,0) vaut 1 et n'a qu'un seul voisin : (0,0). " +
                "Placez un pont simple entre (3,0) et (0,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(3,0), hashi.getIle(0,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "L'île (4,2) vaut 2 et n'a qu'un seul voisin : (2,2). " +
                "Placez un pont double entre (4,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,4) — valeur 2",
                "L'île (5,4) vaut 2 et n'a qu'un seul voisin : (2,4). " +
                "Placez un pont double entre (5,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(5,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "L'île (0,0) vaut 3. " +
                "1 pont est déjà placé vers (3,0). " +
                "Il en reste 2 vers (0,2). " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 4",
                "L'île (0,2) vaut 4. " +
                "2 ponts sont déjà placés vers (0,0). " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "L'île (2,4) vaut 4. " +
                "2 ponts sont déjà placés vers (5,4). " +
                "Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 6",
                "L'île (2,2) vaut 6. " +
                "4 ponts sont déjà placés. " +
                "Placez un pont double entre (2,2) et (4,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(4,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous maîtrisez la technique des îles à un seul voisin. " +
                "Repérez-les toujours en premier pour démarrer une grille !"
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi3() {
        return List.of(

            new EtapeTutoriel(
                "Technique : 3 dans le coin, 5 sur le côté, 7 au milieu",
                "Une île de valeur impaire élevée par rapport à ses voisins " +
                "impose au moins un pont vers chacun d'eux. " +
                "Observez l'île (2,2) qui vaut 7 avec 4 voisins : " +
                "elle a forcément au moins 1 pont vers chacun !"
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "Commençons par les îles à un seul voisin. " +
                "L'île (4,0) vaut 1, son seul voisin est (2,0). " +
                "Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (6,2) — valeur 1",
                "L'île (6,2) vaut 1, son seul voisin est (4,2). " +
                "Placez un pont simple entre (6,2) et (4,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(6,2), hashi.getIle(4,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 2",
                "L'île (2,4) vaut 2, son seul voisin est (2,2). " +
                "Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 7",
                "L'île (2,2) vaut 7 avec 4 voisins (max 8 ponts). " +
                "2 ponts sont déjà placés vers (2,4). " +
                "Il reste 5 ponts pour 3 voisins : au moins 1 pont vers chacun, " +
                "et 2 vers l'un d'eux. " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 5",
                "L'île (2,0) vaut 5. " +
                "1 pont est placé vers (4,0), 2 vers (2,2). " +
                "Il reste 2 ponts vers (0,0). " +
                "Placez un pont double entre (2,0) et (0,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(0,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "L'île (0,0) vaut 3. " +
                "2 ponts sont placés vers (2,0). " +
                "Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "1 pont est placé vers (0,0). " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "L'île (4,2) vaut 2. " +
                "1 pont est placé vers (6,2). " +
                "Placez un pont simple entre (4,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous maîtrisez la technique des valeurs élevées. " +
                "Une île dont la valeur est proche du maximum impose " +
                "des ponts vers chacun de ses voisins !"
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi4() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spéciaux",
                "Parfois une île ne peut pas saturer tous ses voisins " +
                "à cause des contraintes de ses voisins eux-mêmes. " +
                "On doit alors raisonner par élimination."
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "L'île (4,0) vaut 1, son seul voisin est (2,0). " +
                "Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (6,2) — valeur 1",
                "L'île (6,2) vaut 1, son seul voisin est (4,2). " +
                "Placez un pont simple entre (6,2) et (4,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(6,2), hashi.getIle(4,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 2",
                "L'île (2,4) vaut 2, son seul voisin est (2,2). " +
                "Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 7",
                "L'île (2,2) vaut 7. " +
                "2 ponts sont placés vers (2,4), 1 vers (4,2). " +
                "Il reste 4 ponts pour 2 voisins : (0,2) et (2,0). " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 5",
                "L'île (2,0) vaut 5. " +
                "1 pont vers (4,0), 2 vers (2,2). " +
                "Il reste 2 ponts vers (0,0). " +
                "Placez un pont double entre (2,0) et (0,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(0,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "L'île (0,0) vaut 3. " +
                "2 ponts sont placés vers (2,0). " +
                "Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "L'île (4,2) vaut 2. " +
                "1 pont vers (6,2), 1 vers (2,2). " +
                "Elle est complète !",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bien joué ! Quand une île a beaucoup de ponts, " +
                "raisonnez par élimination en partant des contraintes déjà connues."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi5() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spécial de 4 sur le côté",
                "Une île de valeur 4 sur le bord de la grille " +
                "n'a que 3 voisins au maximum. " +
                "Elle a donc forcément au moins 1 pont vers chacun !"
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "L'île (4,0) vaut 1, son seul voisin est (2,0). " +
                "Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 2",
                "L'île (2,4) vaut 2, son seul voisin est (2,2). " +
                "Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "L'île (0,4) vaut 1, son seul voisin est (2,4). " +
                "Placez un pont simple entre (0,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 1",
                "L'île (4,4) vaut 1, son seul voisin est (2,4). " +
                "Placez un pont simple entre (4,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "L'île (2,4) vaut 4. " +
                "2 ponts vers (2,2), 1 vers (0,4), 1 vers (4,4). " +
                "Elle est complète ! Observez comment la valeur 4 " +
                "a imposé au moins 1 pont vers chacun de ses voisins."
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 3",
                "L'île (2,2) vaut 3. " +
                "2 ponts sont placés vers (2,4). " +
                "Placez un pont simple entre (2,2) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 2",
                "L'île (2,0) vaut 2. " +
                "1 pont est placé vers (4,0) et 1 vers (2,2). " +
                "Elle est complète !"
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez résolu la grille ! " +
                "Retenez que les îles à fort coefficient sur le bord " +
                "imposent des ponts vers tous leurs voisins."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi6() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spécial de 6 au milieu",
                "Une île de valeur 6 au milieu de la grille " +
                "a 4 voisins possibles (max 8 ponts). " +
                "Elle a forcément au moins 1 pont vers chacun de ses voisins !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "L'île (0,0) vaut 1, son seul voisin est (0,2). " +
                "Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 1",
                "L'île (4,2) vaut 1, son seul voisin est (2,2). " +
                "Placez un pont simple entre (4,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 6",
                "L'île (2,2) vaut 6 avec 4 voisins. " +
                "1 pont est déjà placé vers (4,2). " +
                "Il reste 5 ponts pour 3 voisins : au moins 1 vers chacun. " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 2",
                "L'île (2,0) vaut 2. " +
                "2 ponts sont placés vers (2,2). Elle est complète !"
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "1 pont vers (0,0), 2 vers (2,2). Elle est complète !"
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 2",
                "L'île (4,4) vaut 2, son seul voisin restant est (2,4). " +
                "Placez un pont double entre (4,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Une île de valeur 6 au milieu impose " +
                "au moins 1 pont vers chacun de ses voisins. " +
                "Cette contrainte permet de démarrer la résolution rapidement."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi7() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement d'un segment à deux îles",
                "Quand deux îles ne peuvent se connecter qu'entre elles " +
                "sans relier le reste de la grille, " +
                "elles forment un segment isolé. " +
                "Ce segment ne doit pas couper la grille en deux parties !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "L'île (0,0) vaut 1, son seul voisin est (0,2). " +
                "Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 2",
                "L'île (2,0) vaut 2, son seul voisin est (2,2). " +
                "Placez un pont double entre (2,0) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "1 pont vers (0,0). " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 6",
                "L'île (2,2) vaut 6. " +
                "2 ponts vers (2,0), 2 vers (0,2). " +
                "Placez un pont simple entre (2,2) et (4,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(4,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 1",
                "L'île (4,2) vaut 1. " +
                "1 pont vers (2,2). Elle est complète !"
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 3",
                "L'île (2,4) vaut 3. " +
                "1 pont vers (2,2). " +
                "Placez un pont double entre (2,4) et (4,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,4), hashi.getIle(4,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Vous avez appris à identifier les segments isolés " +
                "et à les résoudre sans couper la connectivité de la grille."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi8() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement d'un segment à trois îles",
                "Quand trois îles forment un groupe qui ne peut se connecter " +
                "qu'entre elles, elles forment un segment isolé. " +
                "Attention à ne pas créer de sous-groupes déconnectés du reste !"
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "L'île (0,4) vaut 1, son seul voisin est (2,4). " +
                "Placez un pont simple entre (0,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 2",
                "L'île (0,0) vaut 2, son seul voisin est (0,2). " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "2 ponts sont placés vers (0,0). " +
                "Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 2",
                "L'île (2,2) vaut 2. " +
                "1 pont vers (0,2). " +
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Vous avez identifié et résolu un segment à trois îles. " +
                "Observez comment (0,4), (2,4) et (2,2) forment un groupe " +
                "relié au reste via (0,2)."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi9() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement lorsqu'un segment se connecte à une île",
                "Quand un segment isolé doit obligatoirement se connecter " +
                "à une île précise, on peut en déduire des ponts forcés " +
                "vers cette île."
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "L'île (0,0) vaut 1, son seul voisin est (0,2). " +
                "Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "L'île (0,4) vaut 1, son seul voisin est (0,2). " +
                "Placez un pont simple entre (0,4) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,4), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 1",
                "L'île (4,4) vaut 1, son seul voisin est (2,4). " +
                "Placez un pont simple entre (4,4) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,4), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (1,5) — valeur 1",
                "L'île (1,5) vaut 1, son seul voisin est (3,5). " +
                "Placez un pont simple entre (1,5) et (3,5).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(1,5), hashi.getIle(3,5));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 3",
                "L'île (2,0) vaut 3. " +
                "Placez un pont double entre (2,0) et (5,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(5,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — suite",
                "Il reste 1 pont pour (2,0). " +
                "Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "L'île (4,2) vaut 2, son seul voisin est (2,2). " +
                "Placez un pont double entre (4,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (3,3) — valeur 2",
                "L'île (3,3) vaut 2, son seul voisin restant est (5,3). " +
                "Placez un pont double entre (3,3) et (5,3).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(3,3), hashi.getIle(5,3));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (3,5) — valeur 3",
                "L'île (3,5) vaut 3. " +
                "1 pont vers (1,5). " +
                "Placez un pont double entre (3,5) et (5,5).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(3,5), hashi.getIle(5,5));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "1 pont vers (0,0), 1 vers (0,4). " +
                "Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 5",
                "L'île (2,2) vaut 5. " +
                "1 pont vers (2,0), 2 vers (4,2), 1 vers (0,2). " +
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,2), hashi.getIle(2,4));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,0) — valeur 3",
                "L'île (5,0) vaut 3. " +
                "2 ponts vers (2,0). " +
                "Placez un pont simple entre (5,0) et (5,3).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(5,0), hashi.getIle(5,3));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,3) — valeur 4",
                "L'île (5,3) vaut 4. " +
                "2 ponts vers (3,3), 1 vers (5,0). " +
                "Placez un pont simple entre (5,3) et (5,5).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(5,3), hashi.getIle(5,5));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Cette grille illustre bien comment " +
                "les segments isolés se connectent obligatoirement " +
                "à certaines îles, ce qui permet de déduire des ponts forcés."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi10() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement lorsqu'un segment se connecte à un autre segment",
                "Deux groupes d'îles peuvent être forcés à se connecter entre eux. " +
                "Identifier ces groupes permet de déduire les ponts qui les relient."
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "L'île (4,0) vaut 1, son seul voisin est (2,0). " +
                "Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(4,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "L'île (0,0) vaut 3. " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(0,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — suite",
                "Il reste 1 pont pour (0,0). " +
                "Placez un pont simple entre (0,0) et (2,0).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,0), hashi.getIle(2,0));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 3",
                "L'île (2,0) vaut 3. " +
                "1 pont vers (4,0), 1 vers (0,0). " +
                "Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(2,0), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "L'île (0,2) vaut 3. " +
                "2 ponts vers (0,0). " +
                "Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Pont p = hashi.getPont(hashi.getIle(0,2), hashi.getIle(2,2));
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 2",
                "L'île (2,2) vaut 2. " +
                "1 pont vers (2,0), 1 vers (0,2). Elle est complète !"
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bien joué ! Vous avez appris à identifier deux segments " +
                "qui doivent obligatoirement se connecter, " +
                "ce qui impose les ponts entre eux."
            )
        );
    }

    private static List<EtapeTutoriel> etapesHashi11() {
        return List.of(
            new EtapeTutoriel("Tutoriel 11", "Contenu à venir.  Cliquez sur Suivant pour commencer.")
        );
    }

    private static List<EtapeTutoriel> etapesHashi12() {
        return List.of(
            new EtapeTutoriel("Tutoriel 12", "Contenu à venir.  Cliquez sur Suivant pour commencer.")
        );
    }

    private static List<EtapeTutoriel> etapesHashi13() {
        return List.of(
            new EtapeTutoriel("Tutoriel 13", "Contenu à venir.  Cliquez sur Suivant pour commencer.")
        );
    }

    private static List<EtapeTutoriel> etapesHashi14() {
        return List.of(
            new EtapeTutoriel("Tutoriel 14", "Contenu à venir.  Cliquez sur Suivant pour commencer.")
        );
    }
}