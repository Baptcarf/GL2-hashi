package hashiGRP3.Logic;

import hashiGRP3.DatabaseManager;

public class General {
    private static int idUtilisateur = 0;
    private static Hashi hashi = new Hashi();
    private static DatabaseManager db = new DatabaseManager();
    private static int id_grille = 0;
    private static int num_grille = 0;
    private static int id_partie = -1;

    public General() {
        db.init();
    }

    public static int getIdUtilisateur() {
        return idUtilisateur;
    }

    public static Hashi getHashi() {
        return hashi;
    }

    public static void setHashi(Hashi h) {
        General.hashi = h;
    }

    public static void setIdUtilisateur(int idUtilisateur) {
        General.idUtilisateur = idUtilisateur;
    }

    public static DatabaseManager getDb() {
        return db;
    }

    public static int getId_grille() {
        return id_grille;
    }

    public static void setId_grille(int id_grille) {
        General.id_grille = id_grille;
    }

    public static int getId_partie() {
        return id_partie;
    }

    public static void setId_partie(int id) {
        id_partie = id;
    }

    public static int getNum_grille() {
        return num_grille;
    }

    public static void setNum_grille(int num_grille) {
        General.num_grille = num_grille;
    }
}
