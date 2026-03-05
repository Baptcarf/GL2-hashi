//Attribut au paquet
package hashiGRP3;

//Imports
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import java.nio.file.*;
import java.io.*;

import hashiGRP3.Logic.Historique.HistoriqueManager;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Hashi;
import hashiGRP3.compDB.*;;

/* Class */
public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:data/Hashi.db";

    /**
     * Constructeur
     */
    public DatabaseManager() {
        this.init();
    }

    /**
     * Initialise la base de données avec le schéma construit.
     */
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

        String sql = "INSERT INTO Utilisateur(pseudo, Couleur) VALUES(?, ?)";// préparation de la requète

        try (Connection conn = DriverManager.getConnection(
                URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pseudo);
            pstmt.setString(2, couleur);

            pstmt.executeUpdate();
            System.out.println("Utilisateur insérées : ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            System.out.println("Utilisateur supprimé : ");

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
     * test si un utilisateur existe déjà avec un certain pseudo
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

    // Obtient le meilleur score d'un joueur (via pseudo) sur une grille donnée
    // retourne -1 si aucun score
    public int obtenirScore(int id_grille, String pseudo) {

        int meilleurScore = -1;
        int id_utilisateur = getIdUtilisateur(pseudo);

        // Si utilisateur inexistant
        if (id_utilisateur == -1) {
            return -1;
        }

        String sql = "SELECT MAX(score) AS meilleurScore " +
                "FROM Partie " +
                "WHERE id_grille = ? " +
                "AND id_utilisateur = ? " +
                "AND statut = 2";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_grille);
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

    // retourne true si la grille est complétée par le joueur (via pseudo)
    public boolean grilleCompletee(int id_grille, String pseudo) {

        int id_utilisateur = getIdUtilisateur(pseudo);

        // Si l'utilisateur n'existe pas
        if (id_utilisateur == -1) {
            return false;
        }

        String sql = "SELECT 1 FROM Partie " +
                "WHERE id_grille = ? " +
                "AND id_utilisateur = ? " +
                "AND statut = 2";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_grille);
            ps.setInt(2, id_utilisateur);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si au moins une ligne existe

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // recupere l'id_utilisateur depuis le pseudo de l'utilisateur
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

    public int obtenirNombreIle(int id_grille) {

        String sql = "SELECT nbIle FROM Grille WHERE id_grille = ?";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_grille);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("nbIle");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void remplirCoup(HistoriqueManager h, int idUtilisateur, Hashi ha, int id_grille) {

        String sql = "SELECT node_dep,node_arr,val_coup_avant,val_coup_apres FROM Coup NATURAL JOIN partie NATURAL JOIN Utilisateur WHERE id_utilisateur = ?  AND id_grille = ? ORDER BY num_coup";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Integer.toString(idUtilisateur));
            ps.setString(2, Integer.toString(id_grille));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int nodeDep = rs.getInt("node_dep");
                int nodeArr = rs.getInt("node_arr");
                int valCoupAvant = rs.getInt("val_coup_avant");
                int valCoupApres = rs.getInt("val_coup_avant");

                /*
                 * ha.ajouterActionHistorique(ha.getPont(ha.getIleById(nodeDep),
                 * ha.getIleById(nodeArr)),
                 * EtatDuPont.fromValue(valCoupAvant), EtatDuPont.fromValue(valCoupApres));
                 */
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
    public List<String> obtenirTop5ScoresParGrille(int id_grille) {
        List<String> scores = new ArrayList<>();

        String sql = "SELECT u.pseudo, p.score " +
                "FROM Partie p " +
                "JOIN Utilisateur u ON p.id_utilisateur = u.id_utilisateur " +
                "WHERE p.id_grille = ? AND p.statut = 2 AND p.score IS NOT NULL " +
                "ORDER BY p.score ASC " +
                "LIMIT 5";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_grille);
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

}
