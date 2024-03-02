package backend.classes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DeleteFile {

    public static void deleteFile(String fileName) {
        String directory = "photos";
        String filePath = directory + File.separator + fileName;
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
              System.out.println("(-) " + fileToDelete.getName() + " deleted successfully");
            } 
            else {
              System.out.println("(X) Failed to delete " + fileToDelete.getName());
            }
        } 
        else {
            System.out.println(fileToDelete.getName() + " does not exist");
        }
      }

      public static void clearDir(String directoryPath) {
        File dir = new File(directoryPath);

        for (File file : dir.listFiles()) {
            file.delete();
        } 
        System.out.println("(✓) Directory successfully cleared");
      }

      public static void deleteNonVulnerableImages(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        Map<String, Integer> BaseCaseToInjectedCaseMap = new HashMap<>();

        // Populate hashmap with all TestCase numbers and corresponding number of InjectedCases
        for (File file : files) {
          String filename = file.getName();
          // get the first part of the filename before the -- (which is the TestCase number)
          String testCaseNumber = filename.split("--")[0];

          if (filename.contains("BaseCase")) {
            BaseCaseToInjectedCaseMap.putIfAbsent(testCaseNumber, 0);
          }
          else if (filename.contains("InjectedCase")) {
            if (!BaseCaseToInjectedCaseMap.containsKey(testCaseNumber)) {
              BaseCaseToInjectedCaseMap.put(testCaseNumber, 1);
            }
            else { // if TestCase number is already in the list, then update the value by 1
              Integer value = BaseCaseToInjectedCaseMap.get(testCaseNumber);
              BaseCaseToInjectedCaseMap.put(testCaseNumber, value + 1);
            }
          }
        }

        // Loop through the list of files and delete the BaseCases with no corresponding InjectedCases
        for (File file : files) {
          String filename = file.getName();
          String testCaseNumber = filename.split("--")[0];

          Integer numInjectedCases = BaseCaseToInjectedCaseMap.get(testCaseNumber);
          if (numInjectedCases == 0) {
            deleteFile(filename);
          }
        }
      }
}