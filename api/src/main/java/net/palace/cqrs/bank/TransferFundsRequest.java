package net.palace.cqrs.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Request object describing a monetary transaction between two or more accounts. A request
 * must have at least two entries or legs. Different money currencies are allowed in the
 * request legs, as long as the total balance for the legs with the same currency is zero.
 * <p/>
 * This class uses a nested builder state machine to provide a clear building path with
 * compile-time safety and coding guidance.
 *
 * @version 1.0
 */
public final class TransferFundsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Returns a new builder for creating a transfer request.
     *
     * @return the builder to use
     */
    public static ReferenceStep builder() {
        return new Builder();
    }

    public interface ReferenceStep {
        /**
         * @param transactionRef client defined transaction reference
         * @return next build step
         */
        ReferenceStep transactionRef(String transactionRef);

        /**
         * @param transactionType the transaction type for reporting, grouping etc
         * @return next build step
         */
        AccountStep transactionType(String transactionType);
    }

    public interface AccountStep {
        /**
         * @param accountRef the account reference
         * @return next build step
         */
        AmountStep accountRef(String accountRef);
    }

    public interface AmountStep {
        /**
         * @param money the transfer amount
         * @return final build step
         */
        BuildStep amount(Money money);
    }

    public interface BuildStep {
        AmountStep accountRef(String accountRef);

        BuildStep bookingDate(Date date);

        TransferFundsRequest build();
    }

    private static final class Builder implements ReferenceStep, AccountStep, AmountStep, BuildStep {
        private final TransferFundsRequest request = new TransferFundsRequest();

        private String accountRef;

        @Override
        public ReferenceStep transactionRef(String transactionRef) {
            request.transactionRef = transactionRef;
            return this;
        }

        @Override
        public AccountStep transactionType(String transactionType) {
            request.transactionType = transactionType;
            return this;
        }

        @Override
        public AmountStep accountRef(String accountRef) {
            this.accountRef = accountRef;
            return this;
        }

        @Override
        public BuildStep amount(Money money) {
            request.legs.add(new TransactionLeg(accountRef, money));
            return this;
        }

        @Override
        public BuildStep bookingDate(Date date) {
            request.bookingDate = date;
            return this;
        }

        @Override
        public TransferFundsRequest build() {
            if (request.legs.size() < 2) {
                throw new IllegalArgumentException("Expected at least two legs");
            }
            return request;
        }
    }

    private String transactionRef;

    private String transactionType;

    private final List<TransactionLeg> legs = new ArrayList<TransactionLeg>();

    private Date bookingDate = new Date();

    private TransferFundsRequest() {
    }

    public List<TransactionLeg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    public Date getBookingDate() {
        return new Date(bookingDate.getTime());
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
