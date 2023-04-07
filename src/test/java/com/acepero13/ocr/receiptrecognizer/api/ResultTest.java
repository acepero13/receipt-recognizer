package com.acepero13.ocr.receiptrecognizer.api;

import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {
    @Test void  emptyResultIsInvalid(){
        var result = Result.of("");
        assertFalse(result.isValid());
    }

    @Test void nullResultIsInvalid(){
        var result = Result.of(null);
        assertFalse(result.isValid());
        assertTrue(result.isInValid());
    }

    @Test void validResultIsValid(){
        var result = Result.of("Hello");
        assertTrue(result.isValid());
        assertFalse(result.isInValid());
    }

    @Test void validResultHasText(){
        var result = Result.of("Hello");
        assertEquals("Hello", result.text());
    }

    @Test void invalidResultThrowsException(){
        var result = Result.of("");
        assertThrows(InvalidResult.class, result::text);
    }

    @Test void invalidResultHasText(){
        var result = Result.of("");
        assertEquals("Invalid OCR", result.toString());
    }

    @Test void toStringValidResult(){
        var result = Result.of("Hello");
        assertEquals("Hello", result.toString());
    }

    @Test void toStringInvalidResult(){
        var result = Result.of("");
        assertEquals("Invalid OCR", result.toString());
    }

}