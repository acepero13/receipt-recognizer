package com.acepero13.ocr.receiptrecognizer.api;

import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidResult;
/**
 * Result of the OCR
 */
final class Result {
    private Result() {

    }

    /**
     * Creates a new result
     * @param text the text of the result
     * @return the result
     */
    static RecognitionResult of(String text) {
        return text == null || text.isBlank()
                ? invalid()
                : new Valid(text);
    }

    /**
     * Creates a new invalid result
     * @return the invalid result
     */
    static RecognitionResult invalid() {
        return new RecognitionResult() {
            @Override
            public boolean isValid() {
                return false;
            }

            @Override
            public String text() {
                throw new InvalidResult();
            }

            @Override
            public boolean isInValid() {
                return true;
            }

            @Override
            public String toString() {
                return "Invalid OCR";
            }
        };
    }

    private record Valid(String text) implements RecognitionResult {

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public boolean isInValid() {
            return false;
        }

        @Override
        public String toString() {
            return text;
        }
    }


}
