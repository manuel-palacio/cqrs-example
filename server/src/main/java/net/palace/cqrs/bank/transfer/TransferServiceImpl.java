package net.palace.cqrs.bank.transfer;

import com.hazelcast.core.MultiMap;
import net.palace.cqrs.bank.*;
import net.palace.cqrs.bank.account.command.UpdateAccountBalanceCommand;
import net.palace.cqrs.bank.transfer.command.TransferCommand;
import org.axonframework.commandhandling.template.CommandTemplate;
import org.axonframework.repository.AggregateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferServiceImpl implements TransferService {


    @Autowired
    private CommandTemplate commandTemplate;

    @Resource(name = "transactions")
    private MultiMap<String, Transaction> transactions;


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
        if (transactions.get(accountRef) == null) {
            throw new AccountNotFoundException(accountRef);
        }
        return new ArrayList<Transaction>(transactions.get(accountRef));
    }
}
