package com.acepero13.ocr.receiptrecognizer.core;

import com.acepero13.ocr.receiptrecognizer.model.Receipt;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

class ReaderTest {

    @Test
    void readsOneReceipt() {
        Path imagePath = Path.of("src/test/resources/dm1.jpeg");
        var reader = new Reader(TessOcr.ofGerman());

        Receipt receipt = reader.readImage(imagePath);

        assertThat(receipt.purchasedItems().size(), equalTo(6));

        assertThat(receipt.price().value(), closeTo(27.66, 0.1));

    }

    @Test
    void readsPostReceipt() {
        Path imagePath = Path.of("src/test/resources/post1.jpeg");
        var reader = new Reader(TessOcr.ofGerman());

        Receipt receipt = reader.readImage(imagePath);

        assertThat(receipt.purchasedItems().size(), equalTo(2));

        assertThat(receipt.price().value(), closeTo(0.85, 0.1));

    }

}