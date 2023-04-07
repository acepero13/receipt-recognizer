package com.acepero13.ocr.receiptrecognizer.parser;

import com.acepero13.ocr.receiptrecognizer.model.Billable;
import com.acepero13.ocr.receiptrecognizer.model.Item;
import com.acepero13.ocr.receiptrecognizer.model.Total;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class ExpressionTableTest {
    @Test void createsItem(){
        String line = "Bier 0,33l 1,50 2";

        Item result = createBillableFor(line);
        assertThat(result.price().value(), equalTo(1.5));
        assertThat(result.quantity(), equalTo(2));
        assertThat(result.name(), equalTo("Bier 0,33l"));
    }

    @Test void createsTotal(){
        String line = "Summe 1,50";

        Total result = createBillableFor(line);
        assertThat(result.price().value(), equalTo(1.5));
    }

    @Test void createsTotalWithMoreSpaces(){
        String line = "Summe 1,50  ";

        Total result = createBillableFor(line);
        assertThat(result.price().value(), equalTo(1.5));
    }

    @Test void createsTotalFromBruto(){
        String line = "Brutto 1,50";

        Total result = createBillableFor(line);
        assertThat(result.price().value(), equalTo(1.5));
    }

    @Test void ignoresNetto(){
        String line = "Netto 1,50";

        Optional<Billable> opResult = ExpressionTable.of(line);
        assertTrue(opResult.isEmpty());
    }

    @Test void ignoresNettoUmsatz(){
        String line = "nettoumsatz 1,50";

        Optional<Billable> opResult = ExpressionTable.of(line);
        assertTrue(opResult.isEmpty());
    }

    @Test void createsTotalFromBrutoUmsatz(){
        String line = "bruttoumsatz 1,50";

        Total result = createBillableFor(line);
        assertThat(result.price().value(), equalTo(1.5));
    }


    private static <T extends Billable> T createBillableFor(String line) {
        Optional<Billable> opResult = ExpressionTable.of(line);

        if(opResult.isEmpty()) {
            fail("Cannot be empty");
        }
        Billable billable = opResult.get();
        return (T) billable;
    }

}