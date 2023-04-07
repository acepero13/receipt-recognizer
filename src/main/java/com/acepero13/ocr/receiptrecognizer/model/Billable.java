package com.acepero13.ocr.receiptrecognizer.model;

import com.acepero13.ocr.receiptrecognizer.model.vo.Money;
/**
 * This interface is used to represent a billable object
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public interface Billable {
    /**
     * This method is used to get the price of the billable object.
     *
     * @return The price of the billable object.
     */
    Money price();
}
