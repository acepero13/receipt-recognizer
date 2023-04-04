package com.acepero13.ocr.receiptrecognizer.core.exceptions;
/**
 * Exception thrown when the result from the OCR is not valid
 */
public class InvalidResult extends RuntimeException{
    public InvalidResult(){
        super("Invalid result. OCR was not able to read the text. Try to take a better picture");
    }
}
