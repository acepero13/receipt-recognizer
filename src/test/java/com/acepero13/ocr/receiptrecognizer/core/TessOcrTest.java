package com.acepero13.ocr.receiptrecognizer.core;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class TessOcrTest {
    @Test

    void canReadImage() throws TesseractException {
        var converter = TessOcr.ofGerman();
        String text = converter.convertImageToString(Path.of("src/test/resources/sample.png")).text();
        var expected = """
                Der „schnelle” braune Fuchs springt
                über den faulen Hund. Le renard brun
                «rapide» saute par-dessus le chien
                paresseux. La volpe marrone rapida
                salta sopra il cane pigro. EI zorro
                marrön räpido salta sobre el perro
                perezoso. A raposa marrom räpida
                salta sobre 0 cäo preguicoso.
                """;

        assertThat(text, equalTo(expected));
    }

    @Test void canReadGasReceipt() throws TesseractException, IOException {
        var converter = TessOcr.ofGerman();
        Path imagePath = Path.of("src/test/resources/dm1.jpeg");
        BufferedImage image = ImageIO.read(imagePath.toFile());
        String text = converter.convertImageToString(image).text();

        assertThat(text, containsString("SUMME EUR 27,66"));
    }
}