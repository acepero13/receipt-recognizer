package com.acepero13.ocr.receiptrecognizer.model.vo;

import com.acepero13.ocr.receiptrecognizer.core.exceptions.InvalidPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test void moneyContainingAlphaCharacterIsSanitized(){
        var m1 = Money.of("12,6a");
        assertThat(m1, equalTo(new Money(12.6, Currency.EURO)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12,6", "12,6a", "12.6", "12,6a", "+12.6", "-12.6", "*12.6", "/12.6", "| 12.6", "12...6", "012.6"})
    void moneyCanBeParsed(String str){
        var money = Money.of(str);
        assertThat(money, equalTo(new Money(12.6, Currency.EURO)));
    }


    @ParameterizedTest
    @ValueSource(strings = {"12,6,6", "12,6.6"})
    void moneyCanBeParsedAddingOneDecimalPoint(String str){
        var money = Money.of(str);
        assertThat(money, equalTo(new Money(126.6, Currency.EURO)));
    }
}