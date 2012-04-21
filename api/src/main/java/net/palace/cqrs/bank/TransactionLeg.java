package net.palace.cqrs.bank;

import java.io.Serializable;

/**
 * Value object representing a single monetary transaction leg towards an account.
 *
 * @version 1.0
 */
public class TransactionLeg implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String accountRef;

    private final Money amount;

    public TransactionLeg(String accountRef, Money amount) {
        this.accountRef = accountRef;
        this.amount = amount;
    }

    public Money getAmount() {
        return amount;
    }

    public String getAccountRef() {
        return accountRef;
    }
}

