package net.palace.cqrs.bank.account;


import net.palace.cqrs.bank.AccountNotFoundException;
import net.palace.cqrs.bank.AccountService;
import net.palace.cqrs.bank.Money;
import net.palace.cqrs.bank.ReadModelFacade;
import net.palace.cqrs.bank.account.command.CreateAccountCommand;
import org.axonframework.commandhandling.template.CommandTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CommandTemplate commandTemplate;

    @Autowired
    ReadModelFacade readModelFacade;


    @Override
    public void createAccount(String accountRef, Money amount) {
        commandTemplate.send(new CreateAccountCommand(accountRef, amount));
    }

    @Override
    public Money getBalance(String accountRef) throws AccountNotFoundException {
        return readModelFacade.getAccountBalance(accountRef);
    }
}
