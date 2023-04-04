package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.api.Image2String;
import com.acepero13.ocr.receiptrecognizer.api.RecognitionResult;
import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidResult;
import com.acepero13.ocr.receiptrecognizer.model.Billable;
import com.acepero13.ocr.receiptrecognizer.model.Receipt;
import com.acepero13.ocr.receiptrecognizer.parser.ExpressionTable;
import com.acepero13.ocr.receiptrecognizer.utils.image.ImageUtils;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
/**
 * This class is responsible for reading the image and converting it to a receipt.
 */
public final class Reader {
    private final Image2String converter;

    /**
     * Creates a new reader.
     * @param converter The converter to be used. For example, {@link TessOcr}.
     */
    public Reader(Image2String converter) {
        this.converter = converter;
    }

    /**
     * Reads the image and converts it to a receipt.
     * @param path Path to the image to be read.
     * @return The receipt.
     */

    public Receipt readImage(Path path) {
        return tryToReadImageFrom(path);
    }

    private Receipt tryToReadImageFrom(Path path) {
        try {
            RecognitionResult result = converter.convertImageToString(path);
            return createReceiptFrom(result);
        } catch (TesseractException e) {
            // TODO: Handle exception
            throw new RuntimeException(e);
        }
    }

    private Receipt createReceiptFrom(RecognitionResult result) {
        if(result.isInValid()) {
            throw new InvalidResult();
        }
        return createReceipt(result.text());
    }

    private Receipt createReceipt(String result) {
        String[] itemsAsString = result.split("\n");
        List<Billable> billableItems = Stream.of(itemsAsString)
                .map(ExpressionTable::of)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return new Receipt(billableItems, new Date());
    }


}
