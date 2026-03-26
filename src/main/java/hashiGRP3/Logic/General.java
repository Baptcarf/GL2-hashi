//Attribut au paquet
package hashiGRP3.Logic;

//Imports
import hashiGRP3.DatabaseManager;

/** Classe stockant les objets commun entre les pages */
public class General {

    /** Id de l'utilisateur qui joue */
    private static int idUtilisateur = 0;
    /** Grille hashi sur lequel le joueur joue */
    private static Hashi hashi = new Hashi();
    /** Connecteur de base de donnée partagé */
    private static DatabaseManager db = new DatabaseManager();
    /** Id de la grille de hashi sur lequel le joueur joue */
    private static int id_grille = 0;
    /** Numéro de la grille sur lequel le joueur joue */
    private static int num_grille = 0;
    /** Id de la partie en cours */
    private static int id_partie = -1;

    private static long startTime;
    private static double elapsedBefore = 0;
    private static boolean running = false;

    /** Constructeur simple, lance la base de donnée. */
    public General() {
        db.init();
    }

    public static void startTimer() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }

    public static void stopTimer() {
        if (running) {
            elapsedBefore += (System.nanoTime() - startTime) / 1_000_000_000.0;
            running = false;
        }
    }

    public static double getElapsedTime() {
        if (running) {
            return elapsedBefore + (System.nanoTime() - startTime) / 1_000_000_000.0;
        }
        return elapsedBefore;
    }

    public static void resetTimer() {
        elapsedBefore = 0;
        running = false;
    }

    public static void setElapsedTime(double el) {
        elapsedBefore = el;
    }

    /** Getter sur l'id de l'utilisateur */
    public static int getIdUtilisateur() {
        return idUtilisateur;
    }

    /** Getter sur la grille de Hashi logique */
    public static Hashi getHashi() {
        return hashi;
    }

    /** Setter sur la grille de Hashi logique */
    public static void setHashi(Hashi h) {
        General.hashi = h;
    }

    /** Setter sur l'id utilisateur */
    public static void setIdUtilisateur(int idUtilisateur) {
        General.idUtilisateur = idUtilisateur;
    }

    /** Getter sur la base de donnée */
    public static DatabaseManager getDb() {
        return db;
    }

    /** Getter sur l'id de la grille */
    public static int getId_grille() {
        return id_grille;
    }

    /** Setter sur l'id de la grille */
    public static void setId_grille(int id_grille) {
        General.id_grille = id_grille;
    }

    /** Getter sur l'id de partie */
    public static int getId_partie() {
        return id_partie;
    }

    /** Setter sur l'id de partie */
    public static void setId_partie(int id) {
        id_partie = id;
    }

    /** Getter sur le numéro de la grille */
    public static int getNum_grille() {
        return num_grille;
    }

    /** Setter sur le numéro de la grille */
    public static void setNum_grille(int num_grille) {
        General.num_grille = num_grille;
    }
}
