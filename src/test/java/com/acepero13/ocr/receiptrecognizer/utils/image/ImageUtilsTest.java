package com.acepero13.ocr.receiptrecognizer.utils.image;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {
    @Test void saveImageNullPath(){
        assertThrows(NullPointerException.class, () -> ImageUtils.saveImage(null, null));
    }

    @Test void saveImageInvalidPath(){
        assertThrows(IllegalArgumentException.class, () -> ImageUtils.saveImage(new BufferedImage(0,0,0), null));
    }

}