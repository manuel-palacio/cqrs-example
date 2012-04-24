package net.palace.cqrs.bank.transfer;

import net.palace.cqrs.bank.*;
import net.palace.cqrs.bank.account.command.UpdateAccountBalanceCommand;
import net.palace.cqrs.bank.transfer.command.TransferCommand;
import org.axonframework.commandhandling.template.CommandTemplate;
import org.axonframework.repository.AggregateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferServiceImpl implements TransferService {


    @Autowired
    private CommandTemplate commandTemplate;

    @Autowired
    ReadModelFacade readModelFacade;


    @Override
    public void transferFunds(TransferFundsRequest transferRequest) throws InsufficientFundsException,
            AccountNotFoundException, AccountClosedException {

        try {
            commandTemplate.sendAndWait(new UpdateAccountBalanceCommand(transferRequest));
        } catch (InterruptedException e) {
            throw new InfrastructureException(e);
        } catch (AggregateNotFoundException e) {
            throw new AccountNotFoundException();
        }

        commandTemplate.send(new TransferCommand(transferRequest));
    }


    @Override
    public List<Transaction> findTransactions(String accountRef) throws AccountNotFoundException {
        return readModelFacade.findTransactions(accountRef);
    }
}
