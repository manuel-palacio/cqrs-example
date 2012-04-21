package net.palace.cqrs.bank.account;

import net.palace.cqrs.bank.Money;
import org.axonframework.domain.DomainEvent;

public class AccountCreatedEvent extends DomainEvent {

    private Money amount;

    public AccountCreatedEvent(Money amount) {
        this.amount = amount;
    }

    public Money getAmount() {
        return amount;
    }

    public String getAccountRef() {
        return getAggregateIdentifier().asString();
    }

}
