package net.palace.cqrs.bank.account;


import net.palace.cqrs.bank.AccountNotFoundException;
import net.palace.cqrs.bank.AccountService;
import net.palace.cqrs.bank.Money;
import net.palace.cqrs.bank.account.command.CreateAccountCommand;
import org.axonframework.commandhandling.template.CommandTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CommandTemplate commandTemplate;

    @Resource(name = "accounts")
    private Map<String, Money> accounts;


    @Override
    public void createAccount(String accountRef, Money amount) {
        commandTemplate.send(new CreateAccountCommand(accountRef, amount));
    }

    @Override
    public Money getBalance(String accountRef) throws AccountNotFoundException {
        return accounts.get(accountRef);
    }
}
