package net.palace.cqrs.bank;

/**
 * Exception thrown when a closed account is referenced in a
 * monetary transfer transaction.
 *
 * @version 1.0
 */
public class AccountClosedException extends BusinessException {
    private String accountRef;

    public AccountClosedException(String accountRef) {
        super("Account is closed '" + accountRef + "'");
        this.accountRef = accountRef;
    }

    public String getAccountRef() {
        return accountRef;
    }
}
