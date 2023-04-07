package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.api.Image2String;
import com.acepero13.ocr.receiptrecognizer.api.RecognitionResult;
import com.acepero13.ocr.receiptrecognizer.utils.image.ImageUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * This class is responsible for converting an image to a string.
 */
public class TessOcr implements Image2String {
    private final Tesseract tesseract = new Tesseract();

    /**
     * Creates a new TessOcr for the German language.
     * @return A new TessOcr.
     */
    public static Image2String ofGerman() {
        return new TessOcr("deu");
    }

    public static Image2String ofEnglish() {
        return new TessOcr("eng");
    }

    private TessOcr(String language) {
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage(language);
        tesseract.setPageSegMode(3);
        tesseract.setOcrEngineMode(3);

    }

    @Override
    public RecognitionResult convertImageToString(BufferedImage image) throws TesseractException {
        ImageUtils.saveImage(image);
        return RecognitionResult.of(tesseract.doOCR(image));
    }

    public RecognitionResult convertImageToString(Path path) throws TesseractException {
        return RecognitionResult.of(tesseract.doOCR(path.toFile()));
    }


}
