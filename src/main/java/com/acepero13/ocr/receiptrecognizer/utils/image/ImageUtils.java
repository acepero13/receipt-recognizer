package com.acepero13.ocr.receiptrecognizer.utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
/**
 * This class is used to save images.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public class ImageUtils {

    private ImageUtils() {
    }

    /**
     * This method is used to save an image.
     *
     * @param image The image to save.
     * @param path  The path to save the image.
     */

    public static void saveImage(BufferedImage image, String path) {
        try {
            ImageIO.write(image, "jpg", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveImage(BufferedImage preprocessImage) {
        saveImage(preprocessImage, "image.jpg");
    }

}
