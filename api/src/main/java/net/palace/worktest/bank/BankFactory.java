package net.palace.worktest.bank;

/**
 * Defines the contract for a factory that can provide Bank business service
 * object instances.
 *
 * @version 1.0
 */
public interface BankFactory {
    /**
     * @return an instance of the AccountService providing account management
     */
    AccountService getAccountService();

    /**
     * @return an instance of the TransferService providing account transfers
     */
    TransferService getTransferService();

    /**
     * Setup the initial state of the persistent store for testing.
     */
    void setupInitialData();
}
