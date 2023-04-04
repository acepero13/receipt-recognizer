package com.acepero13.ocr.receiptrecognizer.model;

import java.util.Date;
import java.util.List;

public record Receipt(List<Billable> purchasedItems, Date date) implements Billable {

    @Override
    public double price() {
        return purchasedItems.stream()
                .filter(s -> s instanceof Total)
                .reduce((previous, current) -> current)
                .map(Total.class::cast)
                .map(Total::price)
                .orElse(0.0);
    }
}
