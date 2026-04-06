//Attribut au paquet
package hashiGRP3.Logic;

import java.util.List;

/**
 * Fournit les scénarios de tutoriel pour chaque grille.
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
                "Bouton Annuler / Rétablir",
                "Les boutons ← et → en haut à droite permettent d'annuler ou rétablir " +
                "votre dernier coup. Utilisez-les sans hésiter si vous faites une erreur !"
            ),

            new EtapeTutoriel(
                "Bouton Vérification",
                "Le bouton ✓ (vert) vérifie votre grille et vous indique combien d'erreurs " +
                "vous avez commises. Attention : l'utiliser ajoute 60 secondes à votre score !"
            ),

            new EtapeTutoriel(
                "Bouton Indice",
                "Le bouton indice vous suggère un pont à placer avec une explication. " +
                "Attention : l'utiliser ajoute 10 secondes à votre score !"
            ),

            new EtapeTutoriel(
                "Mode Hypothèse",
                "Le bouton crayon active le mode hypothèse. Vos coups sont alors temporaires. " +
                "Si votre hypothèse est bonne, validez-la. Sinon, annulez pour revenir à l'état précédent. " +
                "Ce mode désactive temporairement la vérification et les indices."
            ),

            new EtapeTutoriel(
                "À vous de jouer !",
                "L'île en (2,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Continuez !",
                "L'île en (2,4) vaut 1 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Dernière étape !",
                "L'île en (0,2) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Elle doit donc avoir 2 ponts vers cette île. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Félicitations !",
                "Vous avez terminé les règles du jeu ! " +
                "L'île en (2,2) valait 4 : elle a reçu 2 ponts de l'île en (0,2), " +
                "1 de l'île en (2,0) et 1 de l'île en (2,4). " +
                "Vous êtes prêt pour les vrais tutoriels !"
            )
        );
    }

    // -------------------------------------------------------------------------
    // HASHI 1 — Saturation totale
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi1() {
        return List.of(

            new EtapeTutoriel(
                "Technique : saturation totale",
                "Quand le nombre de ponts d'une île est égal au double de son nombre de voisins, " +
                "elle doit avoir un pont double vers chacun d'eux. " +
                "Repérez ces îles en premier : elles se résolvent automatiquement !"
            ),

            new EtapeTutoriel(
                "Commençons !",
                "L'île en (4,2) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "2 = 1 voisin × 2 donc le pont doit être double. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 4 et a 2 voisins : les îles en (0,2) et (2,0). " +
                "4 = 2 voisins × 2 donc ponts doubles obligatoires vers les deux. " +
                "Placez un pont double entre l'île en (0,0) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0) — suite",
                "Placez maintenant un pont double entre l'île en (0,0) et l'île en (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4)",
                "L'île en (0,4) vaut 4 et a 2 voisins : les îles en (0,2) et (2,4). Même raisonnement. " +
                "Placez un pont double entre l'île en (0,4) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4) — suite",
                "Placez un pont double entre l'île en (0,4) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0)",
                "L'île en (2,0) vaut 4. Un pont double est déjà placé vers l'île en (0,0). " +
                "Placez un pont double entre l'île en (2,0) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,4)",
                "L'île en (2,4) vaut 4. Un pont double est déjà placé vers l'île en (0,4). " +
                "Placez un pont double entre l'île en (2,4) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 6 et a 3 voisins. 6 = 3 × 2 donc tout en double. " +
                "Placez un pont double entre l'île en (0,2) et l'île en (2,2).",
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
    // HASHI 2 — Île avec un seul voisin
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
                "Commençons !",
                "L'île en (3,0) vaut 1 et n'a qu'un seul voisin : l'île en (0,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(3, 0); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,2)",
                "L'île en (4,2) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "2 ponts vers un seul voisin signifie un pont double obligatoire. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (5,4)",
                "L'île en (5,4) vaut 2 et n'a qu'un seul voisin : l'île en (2,4). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(5, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 3. Un pont est déjà placé vers l'île en (3,0). " +
                "Il reste 2 ponts et l'île en (0,2) est le seul autre voisin. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 4. 2 ponts sont déjà placés vers l'île en (0,0). " +
                "Il reste 2 ponts et l'île en (2,2) est le seul autre voisin. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,4)",
                "L'île en (2,4) vaut 4. 2 ponts sont déjà placés vers l'île en (5,4). " +
                "Placez un pont double entre l'île en (2,4) et l'île en (2,2).",
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
    // HASHI 3 — Saturation moins un
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi3() {
        return List.of(

            new EtapeTutoriel(
                "Technique : saturation moins un",
                "Quand nbPonts = nbVoisins×2 - 1, l'île a un pont de moins que la saturation totale. " +
                "Chaque voisin reçoit alors au moins un pont simple. " +
                "L'île en (2,2) vaut 7 et a 4 voisins (max 8), donc au moins 1 pont vers chacun !"
            ),

            new EtapeTutoriel(
                "Îles à un seul voisin d'abord",
                "L'île en (4,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (6,2)",
                "L'île en (6,2) vaut 1 et n'a qu'un seul voisin : l'île en (4,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(6, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,4)",
                "L'île en (2,4) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 1",
                "L'île en (2,2) vaut 7 et a 4 voisins. 7 = 4×2-1 donc au moins 1 pont vers chacun. " +
                "2 ponts sont déjà placés vers l'île en (2,4). " +
                "Placez un pont double entre l'île en (2,2) et l'île en (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 2",
                "Placez un pont double entre l'île en (2,2) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 3",
                "Il reste 1 pont pour l'île en (2,2). " +
                "Placez un pont simple entre l'île en (2,2) et l'île en (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0)",
                "L'île en (2,0) vaut 5. 1 pont vers l'île en (4,0), 2 vers l'île en (2,2). " +
                "Il reste 2 ponts vers l'île en (0,0). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 3. 2 ponts sont déjà placés vers l'île en (2,0). " +
                "Placez un pont simple entre l'île en (0,0) et l'île en (0,2).",
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
    // HASHI 4 — Cas spéciaux de saturation
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi4() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spéciaux de saturation",
                "Quand nbPonts = nbVoisins×2-2 et qu'un voisin est limité à 1 pont, " +
                "les autres voisins reçoivent au moins un pont simple chacun. " +
                "L'île en (2,2) a des voisins à 1 pont qui contraignent la distribution !"
            ),

            new EtapeTutoriel(
                "Îles à un seul voisin d'abord",
                "L'île en (4,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 1 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4)",
                "L'île en (0,4) vaut 1 et n'a qu'un seul voisin : l'île en (2,4). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 2 et n'a qu'un seul voisin accessible : l'île en (2,0). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 1",
                "L'île en (2,2) vaut 7 et a 4 voisins. 1 pont est placé vers l'île en (0,2) (limitée à 1). " +
                "Il reste 6 ponts pour 3 voisins donc ponts doubles obligatoires. " +
                "Placez un pont double entre l'île en (2,2) et l'île en (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 2",
                "Placez un pont double entre l'île en (2,2) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 3",
                "Placez un pont double entre l'île en (2,2) et l'île en (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,4) — partie 1",
                "L'île en (4,4) vaut 2 et a 2 voisins : les îles en (2,4) et (4,2). " +
                "Placez un pont simple entre l'île en (4,4) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,4) — partie 2",
                "Placez un pont simple entre l'île en (4,4) et l'île en (4,2).",
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
    // HASHI 5 — Cas spécial de 4 sur le côté
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
                "Commençons !",
                "L'île en (0,4) vaut 1 et n'a qu'un seul voisin : l'île en (2,4). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,4)",
                "L'île en (4,4) vaut 1 et n'a qu'un seul voisin : l'île en (2,4). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,4)",
                "L'île en (2,4) vaut 4 et a 3 voisins : les îles en (0,4), (4,4) et (2,2). " +
                "2 ponts sont placés (1 vers chaque voisin limité à 1). " +
                "Il reste 2 ponts et seule l'île en (2,2) peut les recevoir. " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 4); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,0)",
                "L'île en (4,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2)",
                "L'île en (2,2) vaut 3. 2 ponts sont déjà placés vers l'île en (2,4). " +
                "Placez un pont simple entre l'île en (2,2) et l'île en (2,0).",
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
    // HASHI 6 — Cas spécial de 6 au milieu
    // -------------------------------------------------------------------------
    private static List<EtapeTutoriel> etapesHashi6() {
        return List.of(

            new EtapeTutoriel(
                "Technique : cas spécial de 6 au milieu",
                "Une île de valeur 6 avec 4 voisins vérifie 6 = 4×2-2. " +
                "Si deux voisins sont limités à 1 pont, les deux autres reçoivent un pont double. " +
                "Observez l'île en (2,2) avec ses voisins contraints !"
            ),

            new EtapeTutoriel(
                "Commençons !",
                "L'île en (0,0) vaut 1 et n'a qu'un seul voisin : l'île en (0,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,2)",
                "L'île en (4,2) vaut 1 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 1",
                "L'île en (2,2) vaut 6 et a 4 voisins. Les îles en (4,2) et en (2,4) sont contraintes. " +
                "1 pont est placé vers l'île en (4,2). " +
                "Les îles en (2,0) et en (0,2) reçoivent donc des doubles. " +
                "Placez un pont double entre l'île en (2,2) et l'île en (2,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 2",
                "Placez un pont double entre l'île en (2,2) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2) — partie 3",
                "Il reste 1 pont pour l'île en (2,2). " +
                "Placez un pont simple entre l'île en (2,2) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,4)",
                "L'île en (2,4) vaut 3. 1 pont est placé vers l'île en (2,2). " +
                "Il reste 2 ponts vers l'île en (4,4). " +
                "Placez un pont double entre ces deux îles.",
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
    // HASHI 7 — Isolement d'un segment à deux îles
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
                "Commençons !",
                "L'île en (0,0) vaut 1 et n'a qu'un seul voisin : l'île en (0,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0)",
                "L'île en (2,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Attention à l'isolement !",
                "Pourrait-on mettre un pont double entre l'île en (0,2) et l'île en (2,2) ? " +
                "Non ! Ces deux îles valent 2 chacune et n'ont que ce pont entre elles : " +
                "elles formeraient un segment isolé du reste ! " +
                "Placez seulement un pont simple entre ces deux îles.",
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
    // HASHI 8 — Isolement d'un segment à trois îles
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
                "Commençons !",
                "L'île en (0,4) vaut 1 et a 2 voisins : les îles en (0,2) et (2,4). " +
                "Mais l'île en (0,2) vaut 3 et doit recevoir 2 ponts de l'île en (0,0) et 1 de l'île en (2,2). " +
                "Elle n'a plus de place pour l'île en (0,4). " +
                "Le pont doit donc aller vers l'île en (2,4). " +
                "Placez un pont simple entre l'île en (0,4) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 2 et n'a qu'un seul voisin : l'île en (0,2). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 3. 2 ponts sont déjà placés vers l'île en (0,0). Il reste 1 pont. " +
                "Placez un pont simple entre l'île en (0,2) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2)",
                "L'île en (2,2) vaut 2. 1 pont est placé vers l'île en (0,2). Il reste 1 pont vers l'île en (2,4). " +
                "Placez un pont simple entre ces deux îles.",
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
    // HASHI 9 — Isolement segment vers île
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
                "Commençons !",
                "L'île en (0,0) vaut 1 et n'a qu'un seul voisin : l'île en (0,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4)",
                "L'île en (0,4) vaut 1 et n'a qu'un seul voisin : l'île en (0,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,4)",
                "L'île en (4,4) vaut 1 et n'a qu'un seul voisin : l'île en (2,4). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (1,5)",
                "L'île en (1,5) vaut 1 et n'a qu'un seul voisin : l'île en (3,5). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(1, 5); Ile b = hashi.getIle(3, 5);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (3,3)",
                "L'île en (3,3) vaut 2 et n'a qu'un seul voisin accessible : l'île en (5,3). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(3, 3); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0) — partie 1",
                "L'île en (2,0) vaut 3 et a 2 voisins : les îles en (5,0) et (2,2). " +
                "Placez un pont double entre l'île en (2,0) et l'île en (5,0).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(5, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0) — partie 2",
                "Il reste 1 pont pour l'île en (2,0). " +
                "Placez un pont simple entre l'île en (2,0) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,2)",
                "L'île en (4,2) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (3,5)",
                "L'île en (3,5) vaut 3. 1 pont est placé vers l'île en (1,5). " +
                "Placez un pont double entre l'île en (3,5) et l'île en (5,5).",
                hashi -> {
                    Ile a = hashi.getIle(3, 5); Ile b = hashi.getIle(5, 5);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 3. 1 pont vers l'île en (0,0) et 1 vers l'île en (0,4). " +
                "Placez un pont simple entre l'île en (0,2) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2)",
                "L'île en (2,2) vaut 5. 1 vers l'île en (2,0), 2 vers l'île en (4,2), 1 vers l'île en (0,2). " +
                "Placez un pont simple entre l'île en (2,2) et l'île en (2,4).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(2, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (5,0)",
                "L'île en (5,0) vaut 3. 2 ponts sont placés vers l'île en (2,0). " +
                "Placez un pont simple entre l'île en (5,0) et l'île en (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(5, 0); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (5,3)",
                "L'île en (5,3) vaut 4. 2 ponts vers l'île en (3,3), 1 vers l'île en (5,0). " +
                "Placez un pont simple entre l'île en (5,3) et l'île en (5,5).",
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
    // HASHI 10 — Isolement segment vers segment
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
                "Commençons !",
                "L'île en (4,0) vaut 1 et n'a qu'un seul voisin : l'île en (2,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 3 et a 2 voisins : les îles en (0,2) et (2,0). " +
                "Placez un pont double entre l'île en (0,0) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Attention à l'isolement !",
                "Pourrait-on mettre un pont double entre l'île en (0,0) et l'île en (2,0) ? " +
                "Non ! Cela formerait un groupe fermé sans sortie. " +
                "Placez seulement un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(2, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0)",
                "L'île en (2,0) vaut 3. 1 pont vers l'île en (4,0), 1 vers l'île en (0,0). " +
                "Placez un pont simple entre l'île en (2,0) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 3. 2 ponts sont placés vers l'île en (0,0). " +
                "Placez un pont simple entre l'île en (0,2) et l'île en (2,2).",
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
    // HASHI 11 — Isoler un segment en bloquant un pont
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
                "Commençons !",
                "L'île en (4,4) vaut 2 et n'a qu'un seul voisin : l'île en (4,2). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(4, 4); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Raisonnement par blocage",
                "Si on supprimait le pont entre l'île en (0,0) et l'île en (4,0), " +
                "l'île en (0,0) n'aurait plus que l'île en (0,2) comme sortie. " +
                "Ce pont est donc nécessaire pour garder la connexion globale. " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(4, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0) — suite",
                "L'île en (0,0) vaut 2. 1 pont est placé vers l'île en (4,0). " +
                "Placez un pont simple entre l'île en (0,0) et l'île en (0,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,0)",
                "L'île en (4,0) vaut 2. 1 pont est placé vers l'île en (0,0). " +
                "Placez un pont simple entre l'île en (4,0) et l'île en (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,2)",
                "L'île en (0,2) vaut 2. 1 pont est placé vers l'île en (0,0). " +
                "Placez un pont simple entre l'île en (0,2) et l'île en (2,2).",
                hashi -> {
                    Ile a = hashi.getIle(0, 2); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2)",
                "L'île en (2,2) vaut 2. 1 pont est placé vers l'île en (0,2). " +
                "Placez un pont simple entre l'île en (2,2) et l'île en (4,2).",
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
    // HASHI 12 — Isoler un segment en ajoutant un pont
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
                "Commençons !",
                "L'île en (0,1) vaut 1 et n'a qu'un seul voisin : l'île en (0,3). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 1); Ile b = hashi.getIle(0, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (1,0)",
                "L'île en (1,0) vaut 1 et n'a qu'un seul voisin : l'île en (3,0). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(1, 0); Ile b = hashi.getIle(3, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,3)",
                "L'île en (2,3) vaut 3 et a 2 voisins : les îles en (0,3) et (5,3). " +
                "Placez un pont double entre l'île en (2,3) et l'île en (5,3).",
                hashi -> {
                    Ile a = hashi.getIle(2, 3); Ile b = hashi.getIle(5, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,3)",
                "L'île en (0,3) vaut 2. 1 pont est placé vers l'île en (0,1). " +
                "Placez un pont simple entre l'île en (0,3) et l'île en (2,3).",
                hashi -> {
                    Ile a = hashi.getIle(0, 3); Ile b = hashi.getIle(2, 3);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (3,0)",
                "L'île en (3,0) vaut 2. 1 pont est placé vers l'île en (1,0). " +
                "Placez un pont simple entre l'île en (3,0) et l'île en (5,0).",
                hashi -> {
                    Ile a = hashi.getIle(3, 0); Ile b = hashi.getIle(5, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (5,0)",
                "L'île en (5,0) vaut 2. 1 pont est placé vers l'île en (3,0). " +
                "Placez un pont simple entre l'île en (5,0) et l'île en (5,3).",
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
    // HASHI 13 — Isoler une île avec des ponts
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
                "Commençons !",
                "L'île en (2,2) vaut 1 et n'a qu'un seul voisin : l'île en (6,2). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(6, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4) — partie 1",
                "L'île en (0,4) vaut 3 et a 2 voisins : les îles en (0,0) et (4,4). " +
                "Placez un pont double entre l'île en (0,4) et l'île en (4,4).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,4) — partie 2",
                "Il reste 1 pont pour l'île en (0,4). " +
                "Placez un pont simple entre l'île en (0,4) et l'île en (0,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(0, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (0,0)",
                "L'île en (0,0) vaut 2. 1 pont est placé vers l'île en (0,4). " +
                "Placez un pont simple entre l'île en (0,0) et l'île en (4,0).",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(4, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,0)",
                "L'île en (4,0) vaut 2. 1 pont est placé vers l'île en (0,0). " +
                "Placez un pont simple entre l'île en (4,0) et l'île en (6,0).",
                hashi -> {
                    Ile a = hashi.getIle(4, 0); Ile b = hashi.getIle(6, 0);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (6,0)",
                "L'île en (6,0) vaut 2. 1 pont est placé vers l'île en (4,0). " +
                "Placez un pont simple entre l'île en (6,0) et l'île en (6,2).",
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
    // HASHI 14 — Conflits de connexion de ponts
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
                "Commençons !",
                "L'île en (0,0) vaut 1 et n'a qu'un seul voisin : l'île en (0,4). " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 0); Ile b = hashi.getIle(0, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,0)",
                "L'île en (2,0) vaut 2 et n'a qu'un seul voisin : l'île en (2,2). " +
                "Placez un pont double entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(2, 0); Ile b = hashi.getIle(2, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (2,2)",
                "L'île en (2,2) vaut 4. 2 ponts sont placés vers l'île en (2,0). " +
                "Placez un pont double entre l'île en (2,2) et l'île en (4,2).",
                hashi -> {
                    Ile a = hashi.getIle(2, 2); Ile b = hashi.getIle(4, 2);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.DOUBLE;
                }
            ),

            new EtapeTutoriel(
                "Attention aux conflits !",
                "Un pont double entre l'île en (0,4) et l'île en (4,4) croiserait " +
                "les ponts déjà placés. Ce pont ne peut donc être que simple. " +
                "Placez un pont simple entre ces deux îles.",
                hashi -> {
                    Ile a = hashi.getIle(0, 4); Ile b = hashi.getIle(4, 4);
                    if (a == null || b == null) return false;
                    Pont p = hashi.getPont(a, b);
                    return p != null && p.getEtatActuel() == EtatDuPont.SIMPLE;
                }
            ),

            new EtapeTutoriel(
                "Île en (4,2)",
                "L'île en (4,2) vaut 4. 2 ponts sont placés vers l'île en (2,2). " +
                "Placez un pont double entre l'île en (4,2) et l'île en (4,4).",
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