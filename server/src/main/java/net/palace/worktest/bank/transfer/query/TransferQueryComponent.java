package net.palace.worktest.bank.transfer.query;


import ch.lambdaj.group.Group;
import com.hazelcast.core.MultiMap;
import net.palace.worktest.bank.Transaction;
import net.palace.worktest.bank.TransactionLeg;
import net.palace.worktest.bank.TransferFundsRequest;
import net.palace.worktest.bank.transfer.TransferCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static ch.lambdaj.Lambda.*;

@Component
public class TransferQueryComponent {

    @Resource(name = "transactions")
    MultiMap<String, Transaction> transactions;


    @EventHandler
    public void transferCreated(TransferCreatedEvent event) {
        TransferFundsRequest transferFundsRequest = event.getTransferFundsRequest();

        Group<TransactionLeg> group = group(transferFundsRequest.getLegs(), by(on(TransactionLeg.class).getAccountRef()));

        for (final String accountRef : group.keySet()) {

            transactions.put(accountRef, new Transaction(transferFundsRequest.getTransactionRef(),
                    transferFundsRequest.getTransactionType(), transferFundsRequest.getBookingDate(),
                    group.find(accountRef)));
        }
    }
}
