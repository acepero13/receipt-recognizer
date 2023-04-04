package com.acepero13.ocr.receiptrecognizer.model.vo;

import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidPrice;

/**
 * This class is used to represent a price.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public record Money(double value, Currency currency) {
    /**
     * This method is used to parse a string to a Money object.
     *
     * @param str The string to parse.
     * @return The Money object.
     */
    public static Money of(String str) {
        try {
            double parsed = Double.parseDouble(str.replace(",", ".").replaceAll("\\s+", ""));
            return new Money(parsed, Currency.EURO);
        } catch (NumberFormatException exception) {
            return Money.invalid();
        }
    }

    /**
     * This method is used to get the value of the price.
     *
     * @return The value of the price.
     * @throws InvalidPrice If the price is not valid.
     */
    @Override
    public double value() {
        if (value == Double.MIN_VALUE) {
            throw new InvalidPrice("The price could not be parsed");
        }
        return value;
    }

    public static Money invalid() {
        return new Money(Double.MIN_VALUE, Currency.NO_CURRENCY);
    }
}
