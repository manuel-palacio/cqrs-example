package net.palace.cqrs.bank;

/**
 * Exception thrown on unrecoverable infrastructure exceptions on the server side.
 *
 * @version 1.0
 */
public class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfrastructureException(Throwable cause) {
        super(cause);
    }
}
