package net.palace.worktest.bank.account.command;

import net.palace.worktest.bank.IllegalTransferRequestException;
import net.palace.worktest.bank.InsufficientFundsException;
import net.palace.worktest.bank.Money;
import net.palace.worktest.bank.account.AccountBalanceUpdatedEvent;
import net.palace.worktest.bank.account.AccountCreatedEvent;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

import java.math.BigDecimal;
import java.util.Currency;

public class Account extends AbstractAnnotatedAggregateRoot {

    private String accountRef;

    private BigDecimal balance;

    private Currency currency;

    public Account(AggregateIdentifier identifier) {
        super(identifier);
    }

    public Account(String accountRef, Money amount) {
        super(new StringAggregateIdentifier(accountRef));
        apply(new AccountCreatedEvent(amount));
    }

    public String getAccountRef() {
        return accountRef;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @EventHandler
    private void onAccountCreated(AccountCreatedEvent event) {
        this.balance = event.getAmount().getAmount();
        this.accountRef = event.getAccountRef();
        this.currency = event.getAmount().getCurrency();
    }

    @EventHandler
    private void onAccountBalanceUpdated(AccountBalanceUpdatedEvent accountBalanceUpdatedEvent) {
        this.balance = accountBalanceUpdatedEvent.getBalance().getAmount();
    }

    public void updateBalance(Money amount) {
        BigDecimal newBalance = balance.add(amount.getAmount());
        if (newBalance.signum() == -1) {
            throw new InsufficientFundsException(accountRef);
        }
        if (!amount.getCurrency().equals(currency)) {
            throw new IllegalTransferRequestException();
        }
        apply(new AccountBalanceUpdatedEvent(accountRef, new Money(newBalance, currency)));
    }
}
