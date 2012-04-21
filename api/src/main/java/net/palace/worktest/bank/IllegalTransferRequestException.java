package net.palace.worktest.bank;

/**
 * Exception thrown if key proprerties are missing or invalid in a monetary tranfer request.
 *
 * @version 1.0
 */
public class IllegalTransferRequestException extends BusinessException {
    public IllegalTransferRequestException() {
    }

    public IllegalTransferRequestException(String message) {
        super(message);
    }
}
