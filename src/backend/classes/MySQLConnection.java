package backend.classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

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

    // Create a single task for the worker node to complete
    public void createTask(int jobID, String nodeIP, byte[] blobData){
        Connection conn = this.createConnection();
        try {
            Blob blob = new SerialBlob(blobData);
            PreparedStatement preparedStatement = conn.prepareStatement("""
                INSERT INTO Task (job_id, node_ip, completed, date_started, test_cases)
                VALUES (?, ?, false, NOW(), ?)"""
            );
            preparedStatement.setInt(1, jobID);
            preparedStatement.setString(2, nodeIP);
            preparedStatement.setBlob(3, blob);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("SQLException error in createTask()");
            System.out.println("Error message: " + e.getMessage());
        }
    }

    // Add a single row to the Result database table
    public void addResult(int taskID, boolean vulnerable, String payload, String attackType, String htmlString, Blob screenshot) {
        Connection conn = this.createConnection();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("""
                INSERT INTO Result (task_id, is_vulnerable, payload, attack_type, html_string, screenshot)
                VALUES (?, ?, ?, ?, ?, ?)"""
            );
            preparedStatement.setInt(1, taskID);
            preparedStatement.setBoolean(2, vulnerable);
            preparedStatement.setString(3, payload);
            preparedStatement.setString(4, attackType);
            preparedStatement.setString(5, htmlString);
            preparedStatement.setBlob(6, screenshot);
            preparedStatement.execute();
        
        } catch (SQLException e) {
            System.out.println("SQLException error in addResult()");
            System.out.println("Error message: " + e.getMessage());
        }
    }

    // convert a screenshot file to a BLOB for database insertion
    public Blob convertScreenshotToBlob(String screenshotFileName) {
        File vulnerableScreenshot = new File(screenshotFileName);
        Blob screenshotBlob = null;
        try {
            byte[] screenshotBytes = Files.readAllBytes(vulnerableScreenshot.toPath());
            screenshotBlob = new SerialBlob(screenshotBytes);
            System.out.println("Screenshot converted to byte array successfully!");
        } catch(IOException e) {
            System.out.println("IOException error in convertScreenshotToBlob() when performing 'readAllBytes() operation'");
            System.out.println("Error message: " + e.getMessage());
        } catch (SerialException e) {
            System.out.println("SerialException error in convertScreenshotToBlob() when performing 'new SerialBlob(screenshotBytes)'");
            System.out.println("Error message: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException error in convertScreenshotToBlob() when performing 'new SerialBlob(screenshotBytes)'");
            System.out.println("Error message: " + e.getMessage());
        }
        return screenshotBlob;
    }

}
