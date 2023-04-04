package com.acepero13.ocr.receiptrecognizer.model;

import com.acepero13.ocr.receiptrecognizer.model.vo.Money;

import java.util.Date;
import java.util.List;

public record Receipt(List<Billable> purchasedItems, Date date) implements Billable {

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
