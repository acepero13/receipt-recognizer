package com.acepero13.ocr.receiptrecognizer.api;

/**
 * This interface is used to convert an image to a string.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public interface RecognitionResult {
   /**
     * This method is used to check if the result is valid.
     *
     * @return True if the result is valid, false otherwise.
     */
    boolean isValid();

    /**
     * This method is used to get the text of the result.
     *
     * @return The text of the result.
     * @throws {@link com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidResult} If the result is not valid.
     */
    String text();

    /**
     * This method is used to create a valid result.
     *
     * @param text The text of the result.
     * @return The result.
     */
    static RecognitionResult of(String text){
        return Result.of(text);
    }


    /**
     * This method is used to check if the result is invalid.
     *
     * @return False if the result is valid, true otherwise.
     */
    boolean isInValid();
}
