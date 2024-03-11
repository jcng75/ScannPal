package backend.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class MySQLConnection {

    public Connection createConnection() {
        Connection conn = null;
        Dotenv dotenv = Dotenv.configure().load();
        String jdbcURL = dotenv.get("MYSQL_URL");
        String username = dotenv.get("MYSQL_USERNAME");
        String password = dotenv.get("MYSQL_PASSWORD");

        try {
            // Load the JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the connection
            conn = DriverManager.getConnection(jdbcURL, username, password);
            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load JDBC driver");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return null;
    }

    public void runUpdate(String query) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement createTableStatement = conn.prepareStatement(query);
            createTableStatement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void runSelect(String query) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();
        
            while (rs.next()) {
                for (int i = 1; i <= count; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": " + rs.getString(i) + "\n");
                }
                System.out.println();
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
