package backend.results;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;

// Java Wrapper class for romankh3/image-comparison on github:
// https://github.com/romankh3/image-comparison
public class ImageDifferenceBox {
    
    public static void compareImages(
        String baseImageFilePath, 
        String compareImageFilePath,
        String resultImageFilePath
    ) 
        throws IOException {
            // Load two images to compare
            BufferedImage baseImage = ImageComparisonUtil.readImageFromResources(baseImageFilePath);
            BufferedImage compareImage = ImageComparisonUtil.readImageFromResources(compareImageFilePath);

            // Create ImageComparison object with result destination and compare the images.
            ImageComparisonResult imageComparisonResult = new ImageComparison(baseImage, compareImage).compareImages();

            // Image can be saved after comparison, using ImageComparisonUtil.
            File resultDestination = new File(resultImageFilePath);
            ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());

    }

    public static float getPercentDifference(String baseImageFilePath, String compareImageFilePath) {
        // Load two images to compare
        BufferedImage baseImage = ImageComparisonUtil.readImageFromResources(baseImageFilePath);
        BufferedImage compareImage = ImageComparisonUtil.readImageFromResources(compareImageFilePath);

        // return the percent difference as a float
        return ImageComparisonUtil.getDifferencePercent(baseImage, compareImage);
    }
}
