package net.palace.cqrs.bank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * A monetary money class that couples an amount with a currency. This class is immutable,
 * just like most numeric wrapper classes. <p>The amount value is represented by
 * <code>java.math.BigDecimal</code> and the currency is represended by <code>java.util.Currency</code>.
 *
 * @version 1.0
 */
public class Money implements Serializable, Comparable<Money> {
    /**
     * The monetary amount.
     */
    private BigDecimal amount;

    /**
     * ISO-4701 currency code.
     */
    private Currency currency;

    /**
     * No-arg constructor for marshalling.
     */
    protected Money() {
    }

    /**
     * Creates a new Money object but not intended to be used directly.
     *
     * @param amount the decimal amount
     * @param currency the currency
     * @throws NullPointerException if value or currency are null
     */
    public Money(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new NullPointerException("value is null");
        }
        if (currency == null) {
            throw new NullPointerException("currency is null");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    protected void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    protected void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Return the underlying monetary value as BigDecimal.
     *
     * @return the monetary value
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Compares this money object with another instance. The money objects are
     * compared by their underlying long value.
     * <p/>
     * {@inheritDoc}
     */
    public int compareTo(Money o) {
        return getAmount().compareTo(o.getAmount());
    }

    /**
     * Compares two money objects for equality. The money objects are
     * compared by their underlying bigdecimal value and currency ISO code.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;

        if (!amount.equals(money.amount)) {
            return false;
        }
        if (!currency.equals(money.currency)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Money");
        sb.append("{amount=").append(amount);
        sb.append(", currency=").append(currency);
        sb.append('}');
        return sb.toString();
    }
}
