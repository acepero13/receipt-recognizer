package com.acepero13.ocr.receiptrecognizer.model;

/**
 * This record is used to represent an item that can be purchased.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */

public record Item(String name, double price, int quantity) implements Billable {
}
