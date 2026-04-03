//Attribut au paquet
package hashiGRP3.Logic;

import java.util.List;

/**
 * Fournit les scénarios de tutoriel pour chaque grille.
 * Basé sur les techniques de détection réelles du moteur d'indices.
 */
public class ScenarioTutoriel {

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
    // (0,2):2  (2,0):1  (2,2):4  (2,4):1
    // Solution : (0,2)-(2,2):2  (2,0)-(2,2):1  (2,2)-(2,4):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi0() {
        return List.of(

            new EtapeTutoriel(
                "Bienvenue dans Hashi !",
                "Hashi est un puzzle japonais. Le plateau contient des îles numérotées. " +
                "Votre objectif : relier toutes les îles par des ponts de façon à ce que " +
                "chaque île ait exactement autant de ponts que son numéro l'indique."
            ),

            new EtapeTutoriel(
                "Les ponts",
                "Un pont relie deux îles voisines (horizontalement ou verticalement). " +
                "Un premier clic place un pont simple. Un second clic place un pont double. " +
                "Un troisième clic supprime le pont. Les ponts ne peuvent pas se croiser !"
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 1",
                "L'île en (2,0) vaut 1 et n'a qu'un seul voisin : (2,2). " +
                "Elle doit donc avoir exactement 1 pont vers (2,2). " +
                "Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 1",
                "L'île en (2,4) vaut 1 et n'a qu'un seul voisin : (2,2). " +
                "Placez un pont simple entre (2,4) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 2",
                "L'île en (0,2) vaut 2 et n'a qu'un seul voisin : (2,2). " +
                "Elle doit donc avoir 2 ponts vers (2,2). " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez terminé votre première grille ! " +
                "L'île (2,2) valait 4 : elle a reçu 2 ponts de (0,2), 1 de (2,0) et 1 de (2,4). " +
                "Vous êtes prêt pour les vrais tutoriels !"
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 1 — Technique Saturation totale
    // (0,0):4  (0,2):6  (0,4):4  (2,0):4  (2,2):8  (2,4):4  (4,2):2
    // Solution : tous doubles
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi1() {
        return List.of(

            new EtapeTutoriel(
                "Technique : saturation totale",
                "Quand le nombre de ponts d'une île est égal au double de son nombre de voisins, " +
                "elle doit avoir un pont DOUBLE vers chacun d'eux. " +
                "Repérez ces îles en premier : elles se résolvent automatiquement !"
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "(4,2):2 n'a qu'un voisin : (2,2). 2 = 1×2 → pont double obligatoire. " +
                "Placez un pont double entre (4,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 4",
                "(0,0):4 a 2 voisins : (0,2) et (2,0). 4 = 2×2 → ponts doubles obligatoires. " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — suite",
                "Placez un pont double entre (0,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 4",
                "(0,4):4 a 2 voisins : (0,2) et (2,4). Même raisonnement. " +
                "Placez un pont double entre (0,4) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — suite",
                "Placez un pont double entre (0,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 4",
                "(2,0):4 a 2 voisins. 1 double vers (0,0) déjà placé. " +
                "Placez un pont double entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "(2,4):4. 1 double vers (0,4). Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 6",
                "(0,2):6 a 3 voisins. 6 = 3×2 → tout en double. " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Quand nbPonts = nbVoisins × 2, tous les ponts sont doubles. " +
                "C'est la technique la plus rapide pour démarrer une grille !"
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 2 — Technique Isolation (île avec un seul voisin)
    // (0,0):3  (3,0):1  (0,2):4  (2,2):6  (4,2):2  (2,4):4  (5,4):2
    // Solution : (0,0)-(3,0):1  (0,0)-(0,2):2  (0,2)-(2,2):2  (2,2)-(4,2):2  (2,2)-(2,4):2  (2,4)-(5,4):2
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi2() {
        return List.of(

            new EtapeTutoriel(
                "Technique : île avec un seul voisin",
                "Quand une île n'a qu'un seul voisin accessible, " +
                "tous ses ponts vont forcément vers lui. " +
                "Repérez ces îles en premier pour démarrer la résolution !"
            ),

            new EtapeTutoriel(
                "Île (3,0) — valeur 1",
                "(3,0):1 n'a qu'un voisin : (0,0). Placez un pont simple entre (3,0) et (0,0).",
                hashi -> {
                    Ile a = hashi.getIle(3, 0); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "(4,2):2 n'a qu'un voisin : (2,2). 2 ponts vers un seul voisin = pont double. " +
                "Placez un pont double entre (4,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,4) — valeur 2",
                "(5,4):2 n'a qu'un voisin : (2,4). Placez un pont double entre (5,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(5, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "(0,0):3. 1 pont vers (3,0). Il reste 2 ponts et (0,2) est le seul autre voisin. " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 4",
                "(0,2):4. 2 ponts vers (0,0). Il reste 2 ponts et (2,2) est le seul autre voisin. " +
                "Placez un pont double entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "(2,4):4. 2 ponts vers (5,4). Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous maîtrisez la technique des îles à un seul voisin ! " +
                "Repérez-les toujours en premier : elles se résolvent sans ambiguïté."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 3 — TechniqueSaturationMoinsUn (7 au milieu)
    // (0,0):3  (0,2):3  (2,0):5  (2,2):7  (2,4):2  (4,0):1  (4,2):2  (6,2):1
    // Solution : (0,0)-(0,2):1  (0,0)-(2,0):2  (0,2)-(2,2):2  (2,0)-(2,2):2
    //            (2,0)-(4,0):1  (2,2)-(4,2):1  (2,2)-(2,4):2  (4,2)-(6,2):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi3() {
        return List.of(

            new EtapeTutoriel(
                "Technique : saturation moins un",
                "Quand nbPonts = nbVoisins×2 - 1, l'île a un pont de moins que la saturation totale. " +
                "Chaque voisin reçoit alors au moins un pont simple. " +
                "Ici (2,2):7 a 4 voisins (max 8), donc au moins 1 pont vers chacun !"
            ),

            new EtapeTutoriel(
                "Îles à un seul voisin d'abord",
                "(4,0):1 n'a qu'un voisin : (2,0). Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (6,2) — valeur 1",
                "(6,2):1 n'a qu'un voisin : (4,2). Placez un pont simple entre (6,2) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(6, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 2",
                "(2,4):2 n'a qu'un voisin : (2,2). Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 7",
                "(2,2):7 a 4 voisins. 7 = 4×2-1 → au moins 1 pont vers chacun. " +
                "2 ponts vers (2,4). Il reste 5 ponts pour 3 voisins. " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Il reste 1 pont pour (2,2). Placez un pont simple entre (2,2) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 5",
                "(2,0):5. 1 pont vers (4,0), 2 vers (2,2). Il reste 2 vers (0,0). " +
                "Placez un pont double entre (2,0) et (0,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "(0,0):3. 2 ponts vers (2,0). Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Quand nbPonts = nbVoisins×2-1, au moins 1 pont simple " +
                "est garanti vers chaque voisin."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 4 — Cas spéciaux (SaturationMoinsDeux / SaturationCapaciteMax)
    // (0,0):2  (0,2):1  (0,4):1  (2,0):5  (2,2):7  (2,4):4  (4,0):1  (4,2):3  (4,4):2
    // Solution : (0,0)-(2,0):2  (0,2)-(2,2):1  (0,4)-(2,4):1  (2,0)-(4,0):1
    //            (2,0)-(2,2):2  (2,2)-(4,2):2  (2,2)-(2,4):2  (2,4)-(4,4):1  (4,2)-(4,4):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi4() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spéciaux de saturation",
                "Quand nbPonts = nbVoisins×2-2 et qu'un voisin est limité à 1 pont, " +
                "les autres voisins reçoivent au moins un pont simple chacun. " +
                "Ici (2,2):7 a des voisins à 1 pont qui contraignent la distribution !"
            ),

            new EtapeTutoriel(
                "Îles à un seul voisin d'abord",
                "(4,0):1 n'a qu'un voisin : (2,0). Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 1",
                "(0,2):1 n'a qu'un voisin : (2,2). Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "(0,4):1 n'a qu'un voisin : (2,4). Placez un pont simple entre (0,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 2",
                "(0,0):2 n'a qu'un voisin accessible : (2,0). " +
                "Placez un pont double entre (0,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 7",
                "(2,2):7 a 4 voisins. 1 pont vers (0,2) (limité à 1). " +
                "Il reste 6 ponts pour 3 voisins → ponts doubles obligatoires vers (2,0), (2,4) et (4,2). " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 2",
                "(4,4):2 a 2 voisins : (2,4) et (4,2). " +
                "Placez un pont simple entre (4,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — suite",
                "Placez un pont simple entre (4,4) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Les voisins limités à 1 pont contraignent la distribution " +
                "et permettent de déduire les ponts doubles obligatoires."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 5 — Cas spécial de 4 sur le côté (SaturationCapaciteMax)
    // (0,4):1  (2,0):2  (2,2):3  (2,4):4  (4,0):1  (4,4):1
    // Solution : (0,4)-(2,4):1  (2,4)-(4,4):1  (2,2)-(2,4):2  (2,2)-(2,0):1  (2,0)-(4,0):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi5() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spécial de 4 sur le côté",
                "Une île de valeur 4 avec 3 voisins dont deux sont limités à 1 pont " +
                "doit obligatoirement avoir un pont double vers le troisième. " +
                "Comparez les capacités restantes de chaque voisin avec les ponts à placer !"
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "(0,4):1 n'a qu'un voisin : (2,4). Placez un pont simple entre (0,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 1",
                "(4,4):1 n'a qu'un voisin : (2,4). Placez un pont simple entre (4,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 4",
                "(2,4):4 a 3 voisins : (0,4):1, (4,4):1, (2,2). " +
                "2 ponts placés (1 vers chaque voisin à 1). Il reste 2 ponts, seul (2,2) peut les recevoir. " +
                "Placez un pont double entre (2,4) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "(4,0):1 n'a qu'un voisin : (2,0). Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 3",
                "(2,2):3. 2 ponts vers (2,4). Placez un pont simple entre (2,2) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bien joué ! Quand une île a des voisins limités, " +
                "comparez les capacités restantes pour déduire les ponts obligatoires."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 6 — Cas spécial de 6 au milieu (SaturationMoinsUn)
    // (0,0):1  (0,2):3  (2,0):2  (2,2):6  (2,4):3  (4,2):1  (4,4):2
    // Solution : (0,0)-(0,2):1  (0,2)-(2,2):2  (2,0)-(2,2):2  (2,2)-(4,2):1  (2,2)-(2,4):1  (2,4)-(4,4):2
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi6() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spécial de 6 au milieu",
                "Une île de valeur 6 avec 4 voisins vérifie 6 = 4×2-2. " +
                "Si deux voisins sont limités à 1 pont, les deux autres reçoivent un pont double. " +
                "Observez (2,2):6 avec ses voisins contraints !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "(0,0):1 n'a qu'un voisin : (0,2). Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 1",
                "(4,2):1 n'a qu'un voisin : (2,2). Placez un pont simple entre (4,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 6",
                "(2,2):6 a 4 voisins. (4,2):1 et (2,4) sont contraints. " +
                "1 pont vers (4,2). Il reste 5 ponts → (2,0) et (0,2) reçoivent des doubles. " +
                "Placez un pont double entre (2,2) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Placez un pont double entre (2,2) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — suite",
                "Il reste 1 pont pour (2,2). Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,4) — valeur 3",
                "(2,4):3. 1 pont vers (2,2). Il reste 2 ponts vers (4,4). " +
                "Placez un pont double entre (2,4) et (4,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Les voisins contraints révèlent les ponts doubles obligatoires " +
                "sur les autres directions."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 7 — Isolement d'un segment à deux îles (TechniqueIsolationDeuxIles)
    // (0,0):1  (0,2):2  (2,0):1  (2,2):2
    // Solution : (0,0)-(0,2):1  (0,2)-(2,2):1  (2,0)-(2,2):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi7() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement d'un segment à deux îles",
                "Si deux îles se connectent au maximum et épuisent leurs deux indices, " +
                "elles forment un segment isolé du reste de la grille. " +
                "Cette connexion maximale est alors interdite !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "(0,0):1 n'a qu'un voisin : (0,2). Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 1",
                "(2,0):1 n'a qu'un voisin : (2,2). Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Attention à l'isolement !",
                "Pourrait-on mettre un pont double entre (0,2) et (2,2) ? " +
                "Non ! (0,2):2 + (2,2):2 = 4, et 2 ponts internes × 2 = 4 : segment isolé ! " +
                "On pose donc 1 seul pont simple. Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Avant de doubler un pont, vérifiez que les deux îles reliées " +
                "ont encore des sorties vers le reste de la grille."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 8 — Isolement d'un segment à trois îles (TechniqueIsolationTroisIles)
    // (0,0):2  (0,2):3  (0,4):1  (2,2):2  (2,4):2
    // Solution : (0,0)-(0,2):2  (0,2)-(2,2):1  (0,4)-(2,4):1  (2,2)-(2,4):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi8() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement d'un segment à trois îles",
                "Trois îles en chaîne peuvent former un segment isolé si l'île centrale " +
                "sature ses deux extrémités sans sortie externe. " +
                "L'île centrale doit conserver au moins une sortie vers l'extérieur !"
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "(0,4):1 n'a qu'un voisin : (2,4). Placez un pont simple entre (0,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 2",
                "(0,0):2 n'a qu'un voisin : (0,2). Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "(0,2):3. 2 ponts vers (0,0). Il reste 1 pont. " +
                "Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 2",
                "(2,2):2. 1 pont vers (0,2). Il reste 1 pont vers (2,4). " +
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bien joué ! Sur une chaîne de trois îles, vérifiez toujours " +
                "que l'île centrale garde une sortie externe."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 9 — Isolement segment vers île (TechniqueIsolationSegmentIle)
    // 14 îles
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi9() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement segment vers île",
                "Quand un groupe d'îles connectées n'a qu'une seule sortie possible, " +
                "ce pont est obligatoire. Identifiez les groupes quasi-fermés " +
                "et forcez leur unique connexion externe !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "(0,0):1 n'a qu'un voisin : (0,2). Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 1",
                "(0,4):1 n'a qu'un voisin : (0,2). Placez un pont simple entre (0,4) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 1",
                "(4,4):1 n'a qu'un voisin : (2,4). Placez un pont simple entre (4,4) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (1,5) — valeur 1",
                "(1,5):1 n'a qu'un voisin : (3,5). Placez un pont simple entre (1,5) et (3,5).",
                hashi -> {
                    Ile a = hashi.getIle(1, 5); Ile b = hashi.getIle(3, 5);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (3,3) — valeur 2",
                "(3,3):2 n'a qu'un voisin accessible : (5,3). " +
                "Placez un pont double entre (3,3) et (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(3, 3); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 3",
                "(2,0):3 a 2 voisins : (5,0) et (2,2). " +
                "Placez un pont double entre (2,0) et (5,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(5, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — suite",
                "Il reste 1 pont pour (2,0). Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 2",
                "(4,2):2 n'a qu'un voisin : (2,2). Placez un pont double entre (4,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (3,5) — valeur 3",
                "(3,5):3. 1 pont vers (1,5). Placez un pont double entre (3,5) et (5,5).",
                hashi -> {
                    Ile a = hashi.getIle(3, 5); Ile b = hashi.getIle(5, 5);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "(0,2):3. 1 pont vers (0,0), 1 vers (0,4). Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 5",
                "(2,2):5. 1 vers (2,0), 2 vers (4,2), 1 vers (0,2). " +
                "Placez un pont simple entre (2,2) et (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,0) — valeur 3",
                "(5,0):3. 2 ponts vers (2,0). Placez un pont simple entre (5,0) et (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(5, 0); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,3) — valeur 4",
                "(5,3):4. 2 vers (3,3), 1 vers (5,0). Placez un pont simple entre (5,3) et (5,5).",
                hashi -> {
                    Ile a = hashi.getIle(5, 3); Ile b = hashi.getIle(5, 5);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Identifiez les groupes quasi-fermés : " +
                "leur unique sortie possible devient un pont obligatoire."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 10 — Isolement segment vers segment (TechniqueIsolementSegment)
    // (0,0):3  (0,2):3  (4,0):1  (2,0):3  (2,2):2
    // Solution : (0,0)-(0,2):2  (0,0)-(2,0):1  (0,2)-(2,2):1  (2,0)-(2,2):1  (2,0)-(4,0):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi10() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isolement segment vers segment",
                "Quand deux groupes d'îles fusionnent en formant un bloc sans sortie externe, " +
                "ce pont est interdit. Avant de renforcer un pont entre deux segments, " +
                "vérifiez que la fusion conserve une ouverture vers le reste !"
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 1",
                "(4,0):1 n'a qu'un voisin : (2,0). Placez un pont simple entre (4,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 3",
                "(0,0):3 a 2 voisins : (0,2) et (2,0). " +
                "Placez un pont double entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Attention à l'isolement !",
                "Pourrait-on mettre un pont double entre (0,0) et (2,0) ? " +
                "Non ! Cela formerait un groupe {(0,0),(0,2),(2,0),(4,0)} sans sortie. " +
                "Placez seulement un pont simple entre (0,0) et (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 3",
                "(2,0):3. 1 pont vers (4,0), 1 vers (0,0). " +
                "Placez un pont simple entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 3",
                "(0,2):3. 2 ponts vers (0,0). Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bien joué ! Avant de renforcer un pont entre deux segments, " +
                "vérifiez toujours que la fusion garde une sortie externe possible."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 11 — Isoler un segment en bloquant un pont (TechniquesBloquePont)
    // (0,0):2  (0,2):2  (2,2):2  (4,0):2  (4,2):4  (4,4):2
    // Solution : (0,0)-(0,2):1  (0,0)-(4,0):1  (0,2)-(2,2):1  (4,0)-(4,2):1  (2,2)-(4,2):1  (4,2)-(4,4):2
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi11() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isoler un segment en bloquant un pont",
                "Si supprimer un pont laisse une île sans aucune sortie vers le reste, " +
                "ce pont est indispensable. Testez mentalement : " +
                "que se passe-t-il si ce pont n'existe pas ?"
            ),

            new EtapeTutoriel(
                "Île (4,4) — valeur 2",
                "(4,4):2 n'a qu'un voisin : (4,2). Placez un pont double entre (4,4) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Raisonnement par blocage",
                "Si on bloquait (0,0)-(4,0), l'île (0,0) n'aurait plus que (0,2) comme sortie. " +
                "Ce pont est donc nécessaire pour garder la connexion globale. " +
                "Placez un pont simple entre (0,0) et (4,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(4, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — suite",
                "(0,0):2. 1 pont vers (4,0). Placez un pont simple entre (0,0) et (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 2",
                "(4,0):2. 1 pont vers (0,0). Placez un pont simple entre (4,0) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,2) — valeur 2",
                "(0,2):2. 1 pont vers (0,0). Placez un pont simple entre (0,2) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 2",
                "(2,2):2. 1 pont vers (0,2). Placez un pont simple entre (2,2) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Excellent ! Simuler la suppression d'un pont révèle " +
                "les liens indispensables à la connectivité de la grille."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 12 — Isoler un segment en ajoutant un pont (TechniqueIsolationSegmentIle)
    // (0,1):1  (0,3):2  (1,0):1  (2,3):3  (3,0):2  (5,0):2  (5,3):3
    // Solution : (0,1)-(0,3):1  (0,3)-(2,3):1  (1,0)-(3,0):1  (2,3)-(5,3):2  (3,0)-(5,0):1  (5,0)-(5,3):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi12() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isoler un segment en ajoutant un pont",
                "Parfois, ajouter un pont à un groupe d'îles l'enferme sans sortie externe. " +
                "Ce pont est alors interdit. Identifiez le seul pont valide qui " +
                "garde une ouverture vers le reste de la grille !"
            ),

            new EtapeTutoriel(
                "Île (0,1) — valeur 1",
                "(0,1):1 n'a qu'un voisin : (0,3). Placez un pont simple entre (0,1) et (0,3).",
                hashi -> {
                    Ile a = hashi.getIle(0, 1); Ile b = hashi.getIle(0, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (1,0) — valeur 1",
                "(1,0):1 n'a qu'un voisin : (3,0). Placez un pont simple entre (1,0) et (3,0).",
                hashi -> {
                    Ile a = hashi.getIle(1, 0); Ile b = hashi.getIle(3, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,3) — valeur 3",
                "(2,3):3 a 2 voisins : (0,3) et (5,3). " +
                "Placez un pont double entre (2,3) et (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(2, 3); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,3) — valeur 2",
                "(0,3):2. 1 pont vers (0,1). Placez un pont simple entre (0,3) et (2,3).",
                hashi -> {
                    Ile a = hashi.getIle(0, 3); Ile b = hashi.getIle(2, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (3,0) — valeur 2",
                "(3,0):2. 1 pont vers (1,0). Placez un pont simple entre (3,0) et (5,0).",
                hashi -> {
                    Ile a = hashi.getIle(3, 0); Ile b = hashi.getIle(5, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (5,0) — valeur 2",
                "(5,0):2. 1 pont vers (3,0). Placez un pont simple entre (5,0) et (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(5, 0); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Bravo ! Identifiez toujours le pont qui maintient une ouverture " +
                "entre les différents groupes d'îles de la grille."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 13 — Isoler une île avec des ponts (TechniqueIsolationIle)
    // (0,0):2  (0,4):3  (2,2):1  (4,0):2  (4,4):2  (6,0):2  (6,2):2
    // Solution : (0,0)-(4,0):1  (0,0)-(0,4):1  (0,4)-(4,4):2  (2,2)-(6,2):1  (4,0)-(6,0):1  (6,0)-(6,2):1
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi13() {
        return List.of(

            new EtapeTutoriel(
                "Technique : isoler une île avec des ponts",
                "Si retirer un pont laisse une île sans aucune sortie possible, " +
                "ce pont est indispensable pour maintenir la connexité. " +
                "Testez chaque pont en imaginant sa suppression !"
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 1",
                "(2,2):1 n'a qu'un voisin : (6,2). Placez un pont simple entre (2,2) et (6,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(6, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — valeur 3",
                "(0,4):3 a 2 voisins : (0,0) et (4,4). " +
                "Placez un pont double entre (0,4) et (4,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,4) — suite",
                "Il reste 1 pont pour (0,4). Placez un pont simple entre (0,4) et (0,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 2",
                "(0,0):2. 1 pont vers (0,4). Placez un pont simple entre (0,0) et (4,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(4, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,0) — valeur 2",
                "(4,0):2. 1 pont vers (0,0). Placez un pont simple entre (4,0) et (6,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(6, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (6,0) — valeur 2",
                "(6,0):2. 1 pont vers (4,0). Placez un pont simple entre (6,0) et (6,2).",
                hashi -> {
                    Ile a = hashi.getIle(6, 0); Ile b = hashi.getIle(6, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Parfait ! La simulation mentale de suppression de ponts " +
                "est une technique puissante pour identifier les liens obligatoires."
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 14 — Création de conflits de connexion pont (TechniqueIsolementSegment)
    // (0,0):1  (0,4):2  (2,0):2  (2,2):4  (4,2):4  (4,4):3
    // Solution : (0,0)-(0,4):1  (0,4)-(4,4):1  (2,0)-(2,2):2  (2,2)-(4,2):2  (4,2)-(4,4):2
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi14() {
        return List.of(

            new EtapeTutoriel(
                "Technique : conflits de connexion de ponts",
                "Deux ponts perpendiculaires ne peuvent pas se croiser. " +
                "Poser un pont double peut bloquer un autre pont nécessaire. " +
                "Anticipez ces conflits pour ne pas bloquer la résolution !"
            ),

            new EtapeTutoriel(
                "Île (0,0) — valeur 1",
                "(0,0):1 n'a qu'un voisin : (0,4). Placez un pont simple entre (0,0) et (0,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,0) — valeur 2",
                "(2,0):2 n'a qu'un voisin : (2,2). Placez un pont double entre (2,0) et (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île (2,2) — valeur 4",
                "(2,2):4. 2 ponts vers (2,0). Placez un pont double entre (2,2) et (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Attention aux conflits !",
                "Un pont double entre (0,4) et (4,4) croiserait (2,0)-(2,2) et (2,2)-(4,2). " +
                "Ce pont ne peut donc être que simple. " +
                "Placez un pont simple entre (0,4) et (4,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île (4,2) — valeur 4",
                "(4,2):4. 2 ponts vers (2,2). Placez un pont double entre (4,2) et (4,4).",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez terminé tous les tutoriels ! Vous maîtrisez maintenant " +
                "les techniques essentielles de Hashi : saturation, isolation, et conflits de ponts. " +
                "Bonne chance sur les vraies grilles !"
            )
        );
    }
}