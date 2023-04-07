package com.acepero13.ocr.receiptrecognizer.parser;

import com.acepero13.ocr.receiptrecognizer.model.Billable;
import com.acepero13.ocr.receiptrecognizer.model.Item;
import com.acepero13.ocr.receiptrecognizer.model.Total;
import com.acepero13.ocr.receiptrecognizer.model.vo.Money;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enum is used to represent the different expressions that can be found in a receipt.
 * It also contains the regex pattern to match the expression and a factory to create the object.
 *
 * @author Acepero13
 * @version 1.0
 * @since 1.0
 */
public enum ExpressionTable {
    //(.+?)\s+(\d+(?:,\d+)?)\s+(\d+)$

    NETO("(?i).*\\b(netto|nettoumsatz)\\b.*", Constants.SKIP),
    BARZAHLUNG("(?i).*\\b(barzahlung)\\b.*", Constants.SKIP),
    ITEM("(.+?)\\s+(\\d+,\\d+?)\\s+(\\d+)$", (Matcher m) -> new Item(m.group(1), toMoney(m.group(2)), Integer.parseInt(m.group(3)))),
    TOTAL("(?i)summe\\s*.*?(\\d+\\s*,\\s*\\d+)", (Matcher m) -> new Total(toMoney(m.group(1)))),

    TOTAL_FROM_BRUTO("(?i)\\b(?:brutto|bruttoumsatz)\\b\\s+(\\+|\\*)*(\\d+[,.]\\d+)", (Matcher m) -> new Total(toMoney(m.group(2))));

    private static Money toMoney(String str) {
        return Money.of(str);

    }

    private final String regexPattern;
    private final Function<Matcher, Billable> factory;

    ExpressionTable(String regexPattern, Function<Matcher, Billable> factory) {
        this.regexPattern = regexPattern;
        this.factory = factory;
    }

    /**
     * This method is used to parse a line to a Billable object.
     *
     * @param line The line to parse.
     * @return The Billable object.
     */
    public static Optional<Billable> of(String line) {
        return Arrays.stream(values())
                .map(expressionRegex -> extractMatcherFrom(sanitizeLine(line), expressionRegex))
                .filter(ExpressionMatcher::isAMatch)
                .findFirst()
                .map(ExpressionMatcher::parse);
    }

    /**
     * In some cases, the OCR engine can't read the pipe character.
     *
     * @param line The line to sanitize.
     * @return The sanitized line.
     */
    private static String sanitizeLine(String line) {
        return line.replaceAll("\\|", "1");
    }

    private static ExpressionMatcher extractMatcherFrom(String line, ExpressionTable expressionRegex) {
        Pattern pattern = Pattern.compile(expressionRegex.regexPattern);
        return new ExpressionMatcher(pattern.matcher(line), expressionRegex);
    }

    private record ExpressionMatcher(Matcher matcher, ExpressionTable regex) {
        private boolean isAMatch() {
            return matcher.find();
        }

        public Billable parse() {
            return regex.factory.apply(matcher);
        }
    }

    private static class Constants {
        public static final Function<Matcher, Billable> SKIP = (Matcher m) -> null;
    }
}
