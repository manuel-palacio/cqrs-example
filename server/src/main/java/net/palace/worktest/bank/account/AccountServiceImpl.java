package net.palace.worktest.bank.account;


import net.palace.worktest.bank.AccountNotFoundException;
import net.palace.worktest.bank.AccountService;
import net.palace.worktest.bank.Money;
import net.palace.worktest.bank.account.command.CreateAccountCommand;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CommandBus commandBus;

    @Resource(name = "accounts")
    private Map<String, Money> accounts;


    @Override
    public void createAccount(String accountRef, Money amount) {
        commandBus.dispatch(new CreateAccountCommand(accountRef, amount));
    }

    @Override
    public Money getBalance(String accountRef) throws AccountNotFoundException {
        return accounts.get(accountRef);
    }
}
