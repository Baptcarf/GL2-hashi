package hashiGRP3;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import java.nio.file.*;
import java.io.*;

import hashiGRP3.compDB.*;

public class BaseDb {

	private static final String URL = "jdbc:sqlite:data/Hashi.db";

	public BaseDb() {

	}

	public void init() {
		File dbFile = new File("data/Hashi.db");
		if (!dbFile.exists()) {
			try (Connection conn = DriverManager.getConnection(URL);
					Statement stmt = conn.createStatement()) {

				// Lire le fichier SQL depuis resources
				InputStream is = BaseDb.class.getResourceAsStream("/db/schema.sql");
				if (is == null)
					throw new FileNotFoundException("schema.sql introuvable");

				String sql = new String(is.readAllBytes());
				stmt.executeUpdate(sql);

			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
	}

	public void insertUser(String pseudo, String couleur) {
		String sql = "INSERT INTO Utilisateur(pseudo, Couleur) VALUES(?, ?)";

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

	public void deletetUser(String pseudo) {
		String sql = "DELETE FROM Utilisateur where pseudo == ?";

		try (Connection conn = DriverManager.getConnection(
				URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, pseudo);

			pstmt.executeUpdate();
			System.out.println("Utilisateur supprimé : ");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Utilisateur> findAllUser() {
		List<Utilisateur> au = new ArrayList<>();
		String sql = "SELECT * FROM Utilisateur";

		try (Connection conn = DriverManager.getConnection(
				URL);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet r = pstmt.executeQuery();
			if (r == null) {
				return au;
			}

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

}
