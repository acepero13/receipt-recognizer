package com.acepero13.ocr.receiptrecognizer.model;

import com.acepero13.ocr.receiptrecognizer.model.vo.Money;

import java.util.Date;
import java.util.List;

/**
 * This record is used to represent a receipt.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public record Receipt(List<Billable> purchasedItems, Date date) implements Billable {

    /**
     * This method is used to get the price of the receipt.
     *
     * @return The price of the receipt.
     */
    @Override
    public Money price() {
        return purchasedItems.stream()
                .filter(s -> s instanceof Total)
                .reduce((previous, current) -> current)
                .map(Total.class::cast)
                .map(Total::price)
                .orElse(Money.invalid());
    }
}
