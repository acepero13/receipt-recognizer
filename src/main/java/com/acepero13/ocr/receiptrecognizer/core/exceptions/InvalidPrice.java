package com.acepero13.ocr.receiptrecognizer.core.exceptions;
/**
 * Exception thrown when the price is not valid
 */
public class InvalidPrice extends RuntimeException{
    public InvalidPrice(String msg) {
        super(msg);
    }
}
