package com.acepero13.ocr.receiptrecognizer.model.vo;

import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidPrice;
import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidResult;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void moneyAreEqual() {
        var m1 = Money.of("12.6");
        var m2 = Money.of("12.6");

        assertThat(m1, equalTo(m2));
    }

    @Test
    void moneyHasAValueAndCurrency() {
        var m1 = Money.of("12, 6");
        assertThat(m1.currency(), equalTo(Currency.EURO));
        assertThat(m1.value(), closeTo(12.6, 0.1));
    }

    @Test
    void wrongMoneyRaisesException() {
        var m1 = Money.of("invalid");
        assertThrows(InvalidPrice.class, m1::value);
        assertThat(m1, equalTo(Money.invalid()));
    }

}