package net.palace.worktest.bank;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Money utility class with static methods for artithmetic operations
 * on {@link Money} objects. All money objects in a monetary transaction
 * must have the same currency.
 *
 * @version 1.0
 */
public abstract class MoneyUtils {
    private MoneyUtils() {
    }

    /**
     * Creates a new Money object with the righ money subtracted from the left.
     * The specified money amounts must have the same currency.
     *
     * @param left the left money
     * @param right the right money to subtract from left, that can be null, in which case this method directly
     * returns the left amount in a new money object
     * @return the new money object
     * @throws CurrencyMismatchException if the money objects have different currencies
     */
    public static Money substract(Money left, Money right) throws CurrencyMismatchException {
        if (right == null) {
            if (left == null) {
                throw new NullPointerException("left is null");
            }
            return new Money(left.getAmount(), left.getCurrency());
        }
        assertSameCurrency(left, right);
        BigDecimal newAmount = left.getAmount().subtract(right.getAmount());
        return new Money(newAmount, left.getCurrency());
    }

    private static final DecimalFormatSymbols DFS = DecimalFormatSymbols.getInstance();

    static {
        DFS.setDecimalSeparator('.');
    }

    /**
     * Pattern mathing: (-)12345.67 SEK
     */
    private final static Pattern MONEY_PATTERN = Pattern.compile("([\\-]?[0-9]+)(?:\\.([0-9]*))? ([A-Z]{3})");

    /**
     * Parses a monetary string to a Money object.
     *
     * @param moneyString the money string
     * @return the Money object
     * @throws IllegalArgumentException if the string format is invalid
     * @see #toMoney(String, String)
     */
    public static Money toMoney(String moneyString) throws IllegalArgumentException {
        Matcher matcher = MONEY_PATTERN.matcher(moneyString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Illegal money format: " + moneyString);
        }
        String money = new StringBuilder().append(matcher.group(1))
                .append(".").append(matcher.group(2)).toString();
        return toMoney(money, matcher.group(3));
    }

    /**
     * Parses a monetary string to a Money object.
     *
     * @param value the money value
     * @param currencyCode the ISO-4217 currency code
     * @return the Money object
     * @throws IllegalArgumentException if the string format is invalid
     * @see #toMoney(String, String)
     * @see Currency
     */
    public static Money toMoney(String value, String currencyCode) throws IllegalArgumentException {
        BigDecimal d = new BigDecimal(value);
        Currency c = Currency.getInstance(currencyCode);
        if (d.scale() != c.getDefaultFractionDigits()) {
            throw new IllegalArgumentException("Wrong number of fraction digits for currency : "
                    + d.scale()
                    + " != " + c.getDefaultFractionDigits() + " for: " + value);
        }
        return new Money(d, c);
    }

    public static boolean isSameCurrency(Money left, Money right) {
        assertNotNull(left, right);
        return left.getCurrency().equals(right.getCurrency());
    }

    private static void assertSameCurrency(Money left, Money right) {
        assertNotNull(left, right);
        if (!isSameCurrency(left, right)) {
            throw new CurrencyMismatchException(
                    left.getCurrency() + " doesn't match the expected currency: " + right);
        }
    }

    private static void assertNotNull(Object left, Object right) {
        if (left == null) {
            throw new NullPointerException("left money is null");
        }
        if (right == null) {
            throw new NullPointerException("right money is null");
        }
    }

    public static class CurrencyMismatchException extends IllegalArgumentException {
        public CurrencyMismatchException(String message) {
            super(message);
        }
    }
}
