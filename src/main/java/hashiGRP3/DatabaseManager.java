//Attribut au paquet
package hashiGRP3;

//Imports
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumSet;
import java.io.*;

import hashiGRP3.Logic.Historique.*;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.Logic.Ile;
import hashiGRP3.Logic.Pont;
import hashiGRP3.compDB.*;
import hashiGRP3.Logic.General;

/** Classe pour la gestion de la base de donnée. */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:data/Hashi.db";

    /** Simple constructeur */
    public DatabaseManager() {
        this.init();
    }

    /** Initialise la base de données avec le schéma construit. */
    public void init() {
        File dbFile = new File("data/Hashi.db");
        if (!dbFile.exists()) {
            try (Connection conn = DriverManager.getConnection(URL);
                    Statement stmt = conn.createStatement()) {

                // Lire le fichier SQL depuis resources
                InputStream is = DatabaseManager.class.getResourceAsStream("/db/schema.sql");
                if (is == null)
                    throw new FileNotFoundException("schema.sql introuvable");

                String sql = new String(is.readAllBytes());
                stmt.executeUpdate(sql);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Renvoie un connecteur vers la base de donnée.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Insère un utilisateur dans la base de donnée.
     */
    public void insertUser(String pseudo, String couleur) {

        // Préparation de la requête
        String sql = "INSERT INTO Utilisateur(pseudo, Couleur, id_avancement_tutoriel) VALUES(?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pseudo);
            pstmt.setString(2, couleur);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Ajoute une partie dans la base de donnée */
    public int creerPartie(int id_utilisateur, int numGrille) {

        String sqlCheck = "SELECT id_partie, statut FROM Partie JOIN Grille on partie.id_grille = Grille.id_grille WHERE id_utilisateur = ? AND numeroGrille = ? ORDER BY id_partie DESC LIMIT 1";
        String sqlInsert = "INSERT INTO Partie (id_utilisateur, id_grille, statut,score) VALUES (?,(SELECT id_grille FROM Grille where numeroGrille = ?), ?,0)";

        boolean reset = false;
        int id_partie = -1;
        boolean creer = true;

        try (Connection conn = DriverManager.getConnection(URL)) {

            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, id_utilisateur);
                psCheck.setInt(2, numGrille);

                try (ResultSet rs = psCheck.executeQuery()) {

                    if (rs.next()) {

                        if (rs.getInt("statut") == 2) {
                            id_partie = rs.getInt("id_partie");
                            creer = false;
                            reset = true;

                        } else {
                            return rs.getInt("id_partie");
                        }
                    }
                }
            }

            if (creer) {

                try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

                    psInsert.setInt(1, id_utilisateur);
                    psInsert.setInt(2, numGrille);
                    psInsert.setInt(3, 1);

                    psInsert.executeUpdate();

                    try (ResultSet rs = psInsert.getGeneratedKeys()) {

                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (reset) {
            General.setId_partie(id_partie);
            resetCoupPartie();
            changeStatutPartie(1);
            updateScorePartie(0.0);
            return id_partie;
        }

        return -1;
    }

    /** Ajoute une grille dans la base de donnée */
    public double checkScorePartie() {
        String sqlCheck = "SELECT Score from Partie WHERE id_partie = ?";

        try (Connection conn = DriverManager.getConnection(
                URL);
                PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {

            pstmt.setInt(1, General.getId_partie());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }

            }
            return 0.0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }

    }

    /** Met à jour le score dans la base de donnée */
    public void updateScorePartie(Double score) {
        String sqlCheck = "UPDATE Partie SET score = ? WHERE id_partie = ?";

        try (Connection conn = DriverManager.getConnection(
                URL);
                PreparedStatement pstmt = conn.prepareStatement(sqlCheck)) {

            pstmt.setDouble(1, score);
            pstmt.setInt(2, General.getId_partie());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** Créer une grille dans la base de donnée */
    public int creerGrille(int numGrille, String nomGrille, int nbIle) {
        int idGrille = -1;

        String sqlCheck = "SELECT id_grille FROM Grille WHERE grille = ? LIMIT 1";
        String sqlInsert = "INSERT INTO Grille (grille, nbIle,numeroGrille) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setString(1, nomGrille);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        idGrille = rs.getInt("id_grille");
                        return idGrille;
                    }
                }
            }

            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setString(1, nomGrille);
                psInsert.setInt(2, nbIle);
                psInsert.setInt(3, numGrille);

                int affectedRows = psInsert.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("La création de la grille a échoué, aucune ligne insérée.");
                }
                try (ResultSet rs = psInsert.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGrille = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGrille;
    }

    /**
     * Supprime un utilisateur de la base de donnée.
     * 
     * @param pseudo le pseudo du compte utilisateur
     */
    public void deleteUser(String pseudo) {
        String sql = "DELETE FROM Utilisateur where pseudo == ?";

        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            stmt.execute("PRAGMA foreign_keys = ON");
            pstmt.setString(1, pseudo);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoie tout les utilisateurs de la base de donnée.
     * 
     * @return la liste de tout les utilsiateurs
     */
    public List<Utilisateur> findAllUser() {
        List<Utilisateur> au = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                String name = r.getString("pseudo");
                String color = r.getString("Couleur");

                au.add(new Utilisateur(name, color));
            }
            return au;

        } catch (SQLException e) {
            e.printStackTrace();
            return au;
        }
    }

    /**
     * Test si un utilisateur existe déjà avec un certain pseudo.
     * 
     * @param pseudo
     * @return true si un utilisateur existe déjà avec un pseudo x
     */
    public Boolean userExist(String pseudo) {
        String sql = "SELECT * FROM Utilisateur where pseudo = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pseudo);

            ResultSet r = pstmt.executeQuery();
            if (!r.next()) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Supprime le status des parties d'un joueur.
     */
    public void resetLastTutoriel(String pseudo) {

        String sql = "DELETE FROM Partie\r\n" + //
                "WHERE statut = 1\r\n" + //
                "  AND id_grille IN (\r\n" + //
                "        SELECT g.id_grille\r\n" + //
                "        FROM Grille g\r\n" + //
                "        WHERE g.niveau = 0\r\n" + //
                "  )\r\n" + //
                "  AND id_utilisateur IN (\r\n" + //
                "        SELECT u.id_utilisateur\r\n" + //
                "        FROM Utilisateur u\r\n" + //
                "        WHERE u.pseudo = ?\r\n" + //
                "  );";

        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            stmt.execute("PRAGMA foreign_keys = ON");
            pstmt.setString(1, pseudo);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtient le meilleur score d'un joueur (via pseudo) sur une grille donnée
     * 
     * @return -1 si on a aucun score.
     */
    public int obtenirScore(int numeroGrille, String pseudo) {

        int meilleurScore = -1;
        int id_utilisateur = getIdUtilisateur(pseudo);

        // Si utilisateur inexistant
        if (id_utilisateur == -1) {
            return -1;
        }

        String sql = "SELECT MAX(Partie.score) AS meilleurScore " +
                "FROM Partie " +
                "JOIN Grille ON Partie.id_grille = Grille.id_grille " +
                "WHERE Grille.numeroGrille = ? " +
                "AND Partie.id_utilisateur = ? " +
                "AND Partie.statut = 2";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroGrille);
            ps.setInt(2, id_utilisateur);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                meilleurScore = rs.getInt("meilleurScore");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meilleurScore;
    }

    /**
     * Check si une grille de la base de donnée est complète.
     * 
     * @return true si la grille est complétée par le joueur (via pseudo)
     */
    public boolean grilleCompletee(int numeroGrille, String pseudo) {

        int id_utilisateur = getIdUtilisateur(pseudo);

        // Si l'utilisateur n'existe pas
        if (id_utilisateur == -1) {
            return false;
        }

        String sql = "SELECT 1 FROM Partie " +
                "JOIN Grille ON Partie.id_grille = Grille.id_grille " +
                "WHERE Grille.numeroGrille = ? " +
                "AND id_utilisateur = ? " +
                "AND statut = 2";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroGrille);
            ps.setInt(2, id_utilisateur);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si au moins une ligne existe

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Recupere l'id_utilisateur depuis le pseudo de l'utilisateur
     */
    public int getIdUtilisateur(String pseudo) {

        int idUtilisateur = -1;

        String sql = "SELECT id_utilisateur FROM Utilisateur WHERE pseudo = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pseudo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idUtilisateur = rs.getInt("id_utilisateur");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUtilisateur;
    }

    /**
     * Getter pour obtenir le nombre d'ilot d'une grille.
     */
    public int obtenirNombreIle(int numGrile) {

        String sql = "SELECT nbIle FROM Grille WHERE numeroGrille = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numGrile);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("nbIle");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /** Rempli l'historique de la BDD avec les coups */
    public void remplirCoup(HistoriqueManager h, int idUtilisateur, Hashi ha, int id_grille) {

        for (Pont pont : ha.getPonts()) {
            pont.setEtatActuel(EtatDuPont.VIDE);
        }

        int idPartie = General.getId_partie();

        if (idPartie == -1)
            return;

        String sql = """
                SELECT node_dep, node_arr, val_coup_avant, val_coup_apres,mode_coup,erreur
                FROM Coup
                WHERE id_partie = ?
                ORDER BY num_coup
                """;

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPartie);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ile dep = ha.getIleById(rs.getInt("node_dep"));
                Ile arr = ha.getIleById(rs.getInt("node_arr"));
                int mode = rs.getInt("mode_coup");
                boolean erreur = rs.getBoolean("erreur");
                if (dep == null || arr == null)
                    continue;

                Pont pont = ha.getPont(dep, arr);
                if (pont == null)
                    continue;

                ArrayList<Mode> modes = new ArrayList<>();
                if (mode == 1) {
                    modes.add(Mode.TEMPORAIRE);
                    General.getHashi().setModeHypothese(true);
                } else {
                    modes.add(Mode.HISTORIQUE);
                }

                if (erreur) {
                    modes.add(Mode.ERREUR);
                    General.getHashi().setErreur(true);

                }

                EtatDuPont avant = EtatDuPont.fromValue(rs.getInt("val_coup_avant"));
                EtatDuPont apres = EtatDuPont.fromValue(rs.getInt("val_coup_apres"));

                if (mode == 1) {
                    pont.setEstHypothese(true);
                }
                pont.setEtatActuel(apres);
                h.ajouterActionNotSave(pont, avant, apres, EnumSet.copyOf(modes));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un coup dans l'historique de la base de donnée
     * 
     * @param id_utilisateur L'id de l'utilisateur qui a réalisé le coup.
     * @param id_grille      L'il de la grille sur laquelle le joueur joue.
     */
    public void addCoup(int id_utilisateur, int id_grille, int id_dep, int id_arr, int valCoupAvant, int valCoupApres,
            EnumSet<Mode> modes) {

        int idPartie = General.getId_partie();

        int mode = 0;
        boolean erreur = false;

        if (modes.contains(Mode.TEMPORAIRE))
            mode = 1;
        else if (modes.contains(Mode.HISTORIQUE))
            mode = 0;

        if (modes.contains(Mode.ERREUR)) {
            erreur = true;
        }

        if (idPartie == -1) {
            System.err.println("Aucune partie active !");
            return;
        }

        // Trouver le prochain num_coup
        String sqlMax = "SELECT COALESCE(MAX(num_coup), -1) + 1 AS next_num FROM Coup WHERE id_partie = ?";
        String sqlInsert = "INSERT INTO Coup VALUES (?, ?, ?, ?, ?, ?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            int numCoup = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlMax)) {
                ps.setInt(1, idPartie);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        numCoup = rs.getInt("next_num");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, idPartie);
                ps.setInt(2, numCoup);
                ps.setInt(3, id_dep);
                ps.setInt(4, id_arr);
                ps.setInt(5, valCoupAvant);
                ps.setInt(6, valCoupApres);
                ps.setInt(7, mode);
                ps.setBoolean(8, erreur);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les 5 meilleurs scores de tous les temps
     * 
     * @return une liste de chaînes au format "pseudo score"
     */
    public List<String> obtenirTop5Scores() {
        List<String> scores = new ArrayList<>();

        String sql = "SELECT u.pseudo, p.score " +
                "FROM Partie p " +
                "JOIN Utilisateur u ON p.id_utilisateur = u.id_utilisateur " +
                "WHERE p.statut = 2 AND p.score IS NOT NULL " +
                "ORDER BY p.score ASC " +
                "LIMIT 5";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String pseudo = rs.getString("pseudo");
                int score = rs.getInt("score");
                scores.add(pseudo + " " + score + "s");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

    /**
     * Récupère les 5 meilleurs scores pour une grille spécifique
     * 
     * @param id_grille l'ID de la grille
     * @return une liste de chaînes au format "pseudo score"
     */
    public List<String> obtenirTop5ScoresParGrille(int numeroGrille) {
        List<String> scores = new ArrayList<>();

        // On joint la table Grille pour filtrer sur le numeroGrille (1, 2, 3...)
        // et non sur l'id_grille (la clé primaire auto-incrémentée)
        String sql = "SELECT u.pseudo, p.score " +
                "FROM Partie p " +
                "JOIN Utilisateur u ON p.id_utilisateur = u.id_utilisateur " +
                "JOIN Grille g ON p.id_grille = g.id_grille " +
                "WHERE g.numeroGrille = ? AND p.statut = 2 AND p.score IS NOT NULL " +
                "ORDER BY p.score DESC " +
                "LIMIT 5";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroGrille);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String pseudo = rs.getString("pseudo");
                int score = rs.getInt("score");
                scores.add(pseudo + " " + score + "s");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

    /**
     * Récupère l'avancement du tutoriel pour un utilisateur.
     * 
     * @param pseudo le pseudo de l'utilisateur
     * @return le numéro du dernier tutoriel complété (0-9), ou 0 si aucun n'a été
     *         complété
     */
    public int obtenirAvancementTutoriel(String pseudo) {
        String sql = "SELECT id_avancement_tutoriel FROM Utilisateur WHERE pseudo = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pseudo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_avancement_tutoriel");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Par défaut, aucun tutoriel complété
    }

    /**
     * Augmente l'avancement du tutoriel pour un utilisateur.
     * 
     * @param pseudo           le pseudo de l'utilisateur
     * @param nouvelAvancement le nouvel avancement du tutoriel
     */
    public void incrementerAvancementTutoriel(String pseudo, int nouvelAvancement) {
        String sql = "UPDATE Utilisateur SET id_avancement_tutoriel = ? WHERE pseudo = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nouvelAvancement);
            pstmt.setString(2, pseudo);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Efface tout les coups d'une partie */
    public void resetCoupPartie() {
        String sql = "DELETE FROM Coup WHERE id_partie = ? ";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, General.getId_partie());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** Change le status d'une partie ; la partie est soit en cours soit terminé. */
    public void changeStatutPartie(int status) {
        String sql = "UPDATE Partie SET statut = ? WHERE id_partie = ?";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, status);
            pstmt.setInt(2, General.getId_partie());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Separer les lignes correspondant à des ponts de celle correspondant à des iles d'un texte donné
     */
    private static void separerLignes(String txt, List<String> lignesIles, List<String> lignesPonts) {
        String[] lignes = txt.split("\n");
        boolean isIles = true;
        for (String ligne : lignes) {
            ligne = ligne.trim();
            if (ligne.isEmpty())
                continue;
            if (ligne.equals("PONTS")) {
                isIles = false;
                continue;
            }
            if (isIles) {
                lignesIles.add(ligne);
            } else {
                lignesPonts.add(ligne);
            }
        }
    }

    /** Valide les coups réalisés lors du mode hypothèse */
    public void validerHypothese() {
        String sql = "UPDATE Coup SET mode_coup = 0 WHERE id_partie = ?";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, General.getId_partie());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** Supprimer les coups réalisés lors du mode hypothèse */
    public void annulerHypothese() {
        String sql = "DELETE FROM Coup where mode_coup = 1 and id_partie = ?";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, General.getId_partie());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** Supprime de la base de données les coups qui menaient à une grille fausse */
    public void retourEtatCorrect() {
        String sql = "DELETE FROM Coup where erreur = true and id_partie = ?";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, General.getId_partie());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
