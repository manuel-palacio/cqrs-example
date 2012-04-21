package net.palace.worktest.bank.transfer;

import com.hazelcast.core.MultiMap;
import net.palace.worktest.bank.*;
import net.palace.worktest.bank.account.command.UpdateAccountBalanceCommand;
import net.palace.worktest.bank.transfer.command.TransferCommand;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.repository.AggregateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransferServiceImpl implements TransferService {


    @Autowired
    private CommandBus commandBus;

    @Resource(name = "transactions")
    private MultiMap<String, Transaction> transactions;


    @Override
    public void transferFunds(TransferFundsRequest transferRequest) throws InsufficientFundsException,
            AccountNotFoundException, AccountClosedException {

        commandBus.dispatch(new UpdateAccountBalanceCommand(transferRequest), new CommandCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (throwable instanceof AggregateNotFoundException) {
                    throw new AccountNotFoundException();
                }
                if (throwable instanceof InsufficientFundsException) {
                    throw (InsufficientFundsException) throwable;
                }

                if (throwable instanceof IllegalTransferRequestException) {
                    throw (IllegalTransferRequestException) throwable;
                }
            }
        });

        commandBus.dispatch(new TransferCommand(transferRequest));
    }


    @Override
    public List<Transaction> findTransactions(String accountRef) throws AccountNotFoundException {
        if (transactions.get(accountRef) == null) {
            throw new AccountNotFoundException(accountRef);
        }
        return new ArrayList<Transaction>(transactions.get(accountRef));
    }
}
