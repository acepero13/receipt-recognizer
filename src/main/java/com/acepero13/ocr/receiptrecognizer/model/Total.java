package com.acepero13.ocr.receiptrecognizer.model;

import com.acepero13.ocr.receiptrecognizer.model.vo.Money;

/**
 * This record is used to represent the total of the receipt.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public record Total(Money price) implements Billable {
}
