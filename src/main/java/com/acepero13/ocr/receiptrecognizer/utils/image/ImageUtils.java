package com.acepero13.ocr.receiptrecognizer.utils.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtils {
    private ImageUtils() {
    }

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

    public static BufferedImage preprocessImage(Path imagePath) throws IOException {
        BufferedImage image = null;
        try {
            // Load image file
            image = ImageIO.read(imagePath.toFile());

            // Convert image to grayscale
            BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            grayImage.getGraphics().drawImage(image, 0, 0, null);

            // Apply Gaussian blur to reduce noise
            int kernelSize = 3;
            double sigma = 0.0;
            float[] kernel = new float[kernelSize * kernelSize];
            for (int i = 0; i < kernel.length; i++) {
                kernel[i] = (float) (1.0 / (kernelSize * kernelSize));
            }
            ConvolveOp convolve = new ConvolveOp(new Kernel(kernelSize, kernelSize, kernel), ConvolveOp.EDGE_NO_OP, null);
            BufferedImage blurredImage = convolve.filter(grayImage, null);

            // Apply adaptive thresholding to improve contrast
            int blockSize = 15;
            int c = 5;
            BufferedImage thresholdedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            int[] histogram = new int[256];
            WritableRaster raster = blurredImage.getRaster();
            int width = raster.getWidth();
            int height = raster.getHeight();
            int[] pixels = raster.getPixels(0, 0, width, height, (int[]) null);
            for (int i = 0; i < pixels.length; i++) {
                histogram[pixels[i] & 0xFF]++;
            }
            for (int i = 1; i < histogram.length; i++) {
                histogram[i] += histogram[i - 1];
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = raster.getPixel(x, y, (int[]) null)[0] & 0xFF;
                    int threshold = (int) (c - blockSize * (histogram[pixel] / (double) (width * height) - 0.5));
                    int binaryPixel = pixel > threshold ? 255 : 0;
                    thresholdedImage.setRGB(x, y, binaryPixel << 16 | binaryPixel << 8 | binaryPixel);
                }
            }
            // Use the thresholded image as output
            return thresholdedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
