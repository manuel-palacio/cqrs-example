package net.palace.worktest.bank;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Value object representing a monetary transfer transaction.
 *
 * @version 1.0
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String transactionRef;

    private final String transactionType;

    private final Date bookingDate;

    private final List<TransactionLeg> legs;

    public Transaction(String transactionRef, String transactionType,
                       Date bookingDate, List<TransactionLeg> legs) {
        this.transactionRef = transactionRef;
        this.transactionType = transactionType;
        this.bookingDate = bookingDate;
        this.legs = legs;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public List<TransactionLeg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    public Date getBookingDate() {
        return new Date(bookingDate.getTime());
    }
}
