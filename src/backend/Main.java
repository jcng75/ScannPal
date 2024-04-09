package backend;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backend.aws.MySQLConnection;
import backend.results.TestCase;
import backend.results.TestResult;
import backend.scan.AttackInjector;
import backend.scan.WebCrawler;
import backend.scan.WorkerNode;

import java.util.HashMap;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // WebCrawler crawl = new WebCrawler("http://100.2.106.51/login.php", "admin", "password");
        // List<TestCase> testCases = crawl.crawl(3);
        // List<List<TestCase>> injectedCases = AttackInjector.generateInjectedCases(testCases);
        // HashMap<String, List<List<TestCase>>> splitCases = AttackInjector.splitTestCases(injectedCases);
        // WorkerNode.createTasks(splitCases, "mmollano1@pride.hofstra.edu");

        // int counter = 162;
        // for (List<TestCase> injectedCase : injectedCases){
            // List<TestResult> result = TestResult.generateResults(injectedCase);
            // ResultAnalysis.runAnalysis(result, counter);
            // counter ++;
            MySQLConnection mySQLConn = new MySQLConnection();
            Connection conn = mySQLConn.createConnection();
            int jobID = 12;
// 
                PreparedStatement checkJob = conn.prepareStatement("""
                    SELECT * FROM Task
                    WHERE job_id = ? and completed = false;
                """);

                checkJob.setInt(1, jobID);
                
                ResultSet rs = checkJob.executeQuery();
                // if no tasks left, run update query
                if (!rs.next()){
                    System.out.println("no tasks left, updating job");
                    PreparedStatement completeJobQuery = conn.prepareStatement("""
                        UPDATE Job
                        SET completed = true, date_completed = NOW()
                        WHERE job_id = ?;
                    """);
                    completeJobQuery.setInt(1, jobID);
                    completeJobQuery.execute();
                }
        // }
        // List<TestResult> testResults = TestResult.generateResults(testCases);


        // String selectQuery2 = "SELECT * FROM Job";
        // mySQLConn.displaySelectAsTable(selectQuery2);
        // String selectQuery1 = "SELECT * FROM Task";

        // mySQLConn.displaySelectAsList(selectQuery2);
        // mySQLConn.displaySelectAsTable(selectQuery1);
        

        // String selectQuery3 = "SELECT task_id, payload FROM Result";
        // String selectQuery4 = "SELECT * FROM Result";
        // mySQLConn.displaySelectAsList(selectQuery3);        

                
            }
        }