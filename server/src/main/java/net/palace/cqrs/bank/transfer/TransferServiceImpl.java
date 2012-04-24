package net.palace.cqrs.bank.transfer;

import net.palace.cqrs.bank.*;
import net.palace.cqrs.bank.account.command.UpdateAccountsBalanceCommand;
import net.palace.cqrs.bank.transfer.command.TransferCommand;
import org.axonframework.commandhandling.template.CommandTemplate;
import org.axonframework.repository.AggregateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TransferServiceImpl implements TransferService {


    @Autowired
    private CommandTemplate commandTemplate;

    @Autowired
    ReadModelFacade readModelFacade;


    @Override
    @Transactional
    public void transferFunds(TransferFundsRequest transferRequest) throws InsufficientFundsException,
            AccountNotFoundException, AccountClosedException {

        try {
            //first try to update all account balances
            commandTemplate.sendAndWait(new UpdateAccountsBalanceCommand(transferRequest));
        } catch (InterruptedException e) {
            throw new InfrastructureException(e);
        } catch (AggregateNotFoundException e) {
            throw new AccountNotFoundException();
        }
        //then save the transfer event
        commandTemplate.send(new TransferCommand(transferRequest));
    }


    @Override
    public List<Transaction> findTransactions(String accountRef) throws AccountNotFoundException {
        return readModelFacade.findTransactions(accountRef);
    }
}
