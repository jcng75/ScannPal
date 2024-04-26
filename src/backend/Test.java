package backend;

import java.sql.Connection;

import backend.aws.MySQLConnection;

public class Test {
    public static void main(String[] args) {
    
        MySQLConnection sqlConnection = new MySQLConnection();
        Connection conn = sqlConnection.createConnection();

        String resultString = "SELECT * FROM Task WHERE job_id = 21";
        sqlConnection.displaySelectAsTable(resultString);
    }
}
