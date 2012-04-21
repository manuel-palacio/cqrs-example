package net.palace.worktest.bank;

/**
 * Main business service interface for managing monetary accounts.
 *
 * @version 1.0
 */
public interface AccountService {
    /**
     * Create a new account with an initial balance.
     *
     * @param accountRef the account reference
     * @param amount the account balance
     * @throws InfrastructureException on unrecoverable infrastructure errors
     */
    void createAccount(String accountRef, Money amount);

    /**
     * Get the balance for a given account.
     *
     * @param accountRef the account reference
     * @return the account balance
     * @throws AccountNotFoundException if the specified account does not exist
     * @throws InfrastructureException on unrecoverable infrastructure errors
     */
    Money getBalance(String accountRef) throws AccountNotFoundException;
}
