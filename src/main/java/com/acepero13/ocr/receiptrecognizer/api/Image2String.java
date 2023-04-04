package com.acepero13.ocr.receiptrecognizer.api;

import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * This interface is used to convert an image to a string.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public interface Image2String {

    /**
     * This method is used to convert an image to a string.
     *
     * @param image The image to convert.
     * @return The string representation of the image.
     * @throws TesseractException If the image could not be converted.
     */
    RecognitionResult convertImageToString(BufferedImage image) throws TesseractException;


    /**
     * This method is used to convert an image to a string.
     *
     * @param path The path to the image.
     * @return The string representation of the image.
     * @throws TesseractException If the image could not be converted.
     */
    RecognitionResult convertImageToString(Path path) throws TesseractException;
}
