package backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import backend.aws.MySQLConnection;
import backend.results.TestCase;
import backend.scan.AttackInjector;
import backend.scan.MyWebDriver;
import backend.scan.WebCrawler;
import backend.scan.WorkerNode;

import java.util.HashMap;

// For Local Tests
public class Main {
    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {
        System.setProperty("webdriver.chrome.driver", "/ScannPal/chromedriver-linux64/chromedriver");

        // url username password userID passID depth email
        WebCrawler crawl = new WebCrawler(args[0], args[1], args[2], args[3], args[4]);
        int depth = Integer.parseInt(args[5]);
        String parsedUrl = crawl.getParsedUrl();
        List<TestCase> testCases = crawl.crawl(depth);
        List<List<TestCase>> injectedCases = AttackInjector.generateInjectedCases(testCases);
        HashMap<String, List<List<TestCase>>> splitCases = AttackInjector.splitTestCases(injectedCases);
        WorkerNode.createTasks(splitCases, args[6], parsedUrl);
        MyWebDriver.getDriver().quit();
        System.out.println("(+) Scan has been completed!");
    }
}