package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.api.Image2String;
import com.acepero13.ocr.receiptrecognizer.utils.image.ImageUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class TessOcr implements Image2String {
    private final Tesseract tesseract = new Tesseract();

    public static Image2String ofGerman() {
        return new TessOcr("deu");
    }

    private TessOcr(String language) {
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage(language);
        tesseract.setPageSegMode(3);
        tesseract.setOcrEngineMode(3);

    }

    @Override
    public String convertImageToString(BufferedImage image) throws TesseractException {
        ImageUtils.saveImage(image);
        return tesseract.doOCR(image);
    }

    public String convertImageToString(Path path) throws TesseractException {
        return tesseract.doOCR(path.toFile());
    }


}
