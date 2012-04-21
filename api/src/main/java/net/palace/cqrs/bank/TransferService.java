package net.palace.cqrs.bank;

import java.util.List;

/**
 * Main business service interface for managing monetary transactions.
 *
 * @version 1.0
 */
public interface TransferService {
    /**
     * Executes a synchronous monetary transaction request as a single unit of work.
     *
     * @param transferRequest the request describing the participating accounts
     * @throws IllegalArgumentException if the request is incomplete: If the entries are less than two
     * or other key attributes are missing
     * @throws AccountNotFoundException if a specified account does not exist
     * @throws AccountClosedException if a participating account is closed
     * @throws InsufficientFundsException if a participating account is overdrawn
     * @throws InfrastructureException on non-recoverable infrastructure errors
     */
    void transferFunds(TransferFundsRequest transferRequest)
            throws InsufficientFundsException, AccountNotFoundException, AccountClosedException;

    /**
     * Find all transaction for a given account.
     *
     * @param accountRef the account reference
     * @return list of  transactions
     * @throws AccountNotFoundException if the specified account does not exist
     *  @throws InfrastructureException on non-recoverable infrastructure errors
     */
    List<Transaction> findTransactions(String accountRef) throws AccountNotFoundException;
}
