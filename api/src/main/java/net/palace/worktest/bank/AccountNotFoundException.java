package net.palace.worktest.bank;

/**
 * Exception thrown if an account does not exist.
 *
 * @version 1.0
 */
public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
