package backend.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class MySQLConnection {

    // Creates a connection to a MySQL database
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

    // Used for void SQL queries such as insert, update, or delete
    public void runUpdate(String query) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException error in runUpdate()");
            System.out.println("Error message: " + e.getMessage());
        }
    }

    // Displays a select query as a list of data
    public void displaySelectAsList(String selectQuery) {
        Connection conn = this.createConnection();
        System.out.println("Running " + selectQuery + "\n");
        try {
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet selectResults = selectStatement.executeQuery();

            ResultSetMetaData rsMetaData = selectResults.getMetaData();
            int count = rsMetaData.getColumnCount();
        
            while (selectResults.next()) {
                for (int i = 1; i <= count; i++) {
                    System.out.print(rsMetaData.getColumnName(i) + ": " + selectResults.getString(i) + "\n");
                }
                System.out.println();
            }
            selectResults.close();
            selectStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException error in displaySelectAsList()");
            System.out.println("Error message: " + e.getMessage());
        }
    }

    // Display a select query in a table format
    public void displaySelectAsTable(String selectQuery) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet selectResults = selectStatement.executeQuery();
            DBTablePrinter.printResultSet(selectResults);
            selectResults.close();
            selectStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException error in displaySelectQuery()");
            System.out.println("Error message: " + e.getMessage());
        }
    }

    // returns the results of a SQL select query as a ResultSet object
    /*
     How to loop through the ResultSet object
     while (rs.next()) {
        int user_id = rs.getInt(1);
        String fname = rs.getString(2);
        String lname = rs.getString(3);
        etc ... (based on what your table columns are)
     }
     */
    public ResultSet getSelectResults(String selectQuery) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet selectResultSet = selectStatement.executeQuery();
            return selectResultSet;
        } catch (SQLException e) {
            System.out.println("SQLException error in getSelectResults()");
            System.out.println("Error message: " + e.getMessage());
        }
        return null;
    }
}
