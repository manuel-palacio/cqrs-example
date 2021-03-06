package net.palace.cqrs.bank.account;

import net.palace.cqrs.bank.Money;
import org.axonframework.domain.DomainEvent;


public class AccountBalanceUpdatedEvent extends DomainEvent {

    private Money balance;
    private final String accountRef;

    public AccountBalanceUpdatedEvent(String accountRef, Money balance) {
        this.accountRef = accountRef;
        this.balance = balance;
    }

    public String getAccountRef() {
        return accountRef;
    }

    public Money getBalance() {
        return balance;
    }
}
