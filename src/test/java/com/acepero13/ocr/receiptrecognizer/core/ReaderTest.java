package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.model.Receipt;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test void readsOneReceipt(){
        Path imagePath = Path.of("src/test/resources/dm1.jpeg");
        var reader = new Reader(TessOcr.ofGerman());

        Receipt receipt = reader.readImage(imagePath);

        assertThat(receipt.purchasedItems().size(), equalTo(6));

        assertThat(receipt.price(), closeTo(27.66, 0.1));

    }

}