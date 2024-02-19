package backend.classes;

import java.io.File;

public class DeleteFile {

    public static void removeFile(String fileName){
        String filePath = "photos/" + fileName;
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Unable to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}