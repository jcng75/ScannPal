package backend.classes;

import java.io.File;

public class DeleteFile {

    public static void deleteFile(String fileName) {
        String directory = "photos";
        String filePath = directory + File.separator + fileName;
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
              System.out.println(fileToDelete.getName() + " deleted successfully");
            } 
            else {
              System.out.println("Failed to delete " + fileToDelete.getName());
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
      }

}