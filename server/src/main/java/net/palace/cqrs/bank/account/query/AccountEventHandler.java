package net.palace.cqrs.bank.account.query;

import net.palace.cqrs.bank.Money;
import net.palace.cqrs.bank.account.AccountBalanceUpdatedEvent;
import net.palace.cqrs.bank.account.AccountCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


@Component
public class AccountEventHandler {

    @Resource(name = "accounts")
    private Map<String, Money> accounts;


    @EventHandler
    public void onAccountCreated(AccountCreatedEvent event) {
        accounts.put(event.getAccountRef(), event.getAmount());
    }

    @EventHandler
    public void onAccountBalanceUpdated(AccountBalanceUpdatedEvent accountBalanceUpdatedEvent) {
        accounts.put(accountBalanceUpdatedEvent.getAccountRef(), accountBalanceUpdatedEvent.getBalance());
    }
}
