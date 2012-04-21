package net.palace.cqrs.bank;

/**
 * Base type for recoverable business exceptions.
 *
 * @version 1.0
 */
public abstract class BusinessException extends RuntimeException {
    protected BusinessException() {
    }

    protected BusinessException(String message) {
        super(message);
    }
}
