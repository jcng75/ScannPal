package backend;

import backend.scan.WorkerNode;

// For Cloud Tests
public class App {
    public static void main(String args[]) throws Exception {
        System.setProperty("webdriver.chrome.driver", "/ScannPal/chromedriver-linux64/chromedriver");
        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\Justin Ng\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        
        WorkerNode workerNode = new WorkerNode();
        workerNode.runNode();
    }
}
