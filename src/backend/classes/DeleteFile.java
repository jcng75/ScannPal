package backend.classes;

import java.io.File;
import java.util.List;

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

      public static boolean checkFile(String directoryPath, String fileStart){
        File dir = new File(directoryPath);
        int counter = 0;
        
        File[] files = dir.listFiles();
        for (File file : files){
            if (file.getName().contains(fileStart)){
                counter++;
            }
        }
        System.out.println(String.format("(+) %s contains %s files", fileStart, counter));
        if (files[files.length-1].getName().contains(fileStart)){
            return true;
        }
        return counter < 2;

      }

}