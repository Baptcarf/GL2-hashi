package hashiGRP3;

import java.sql.*;
import java.nio.file.*;
import java.io.*;

public class BaseDb {

        private static final String URL = "jdbc:sqlite:data/Hashi.db";

        public static void init() {
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

        public static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(URL);
        }
}
