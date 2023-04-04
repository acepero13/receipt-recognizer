package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.api.Image2String;
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

public final class Reader {
    private final Image2String converter;

    public Reader(Image2String converter) {
        this.converter = converter;
    }

    public Receipt readImage(Path path) {
        try {
            //BufferedImage preprocessedImage = ImageUtils.preprocessImage(path);
            String result = converter.convertImageToString(path);
            return createReceipt(result);
        } catch (TesseractException e) {
            // TODO: Handle exception
            throw new RuntimeException(e);
        }
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
