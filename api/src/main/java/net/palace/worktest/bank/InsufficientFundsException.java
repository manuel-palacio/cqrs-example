package net.palace.worktest.bank;

/**
 * Business exception thrown if an account has insufficient funds.
 *
 * @version 1.0
 */
public class InsufficientFundsException extends BusinessException {
    private String accountRef;

    public InsufficientFundsException(String accountRef) {
        super("Insufficient funds for account " + accountRef);
        this.accountRef = accountRef;
    }

    public String getAccountRef() {
        return accountRef;
    }
}
