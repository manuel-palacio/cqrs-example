package net.palace.worktest.bank.transfer.command;


import net.palace.worktest.bank.TransactionLeg;
import net.palace.worktest.bank.TransferFundsRequest;
import net.palace.worktest.bank.transfer.TransferCreatedEvent;
import org.axonframework.domain.AggregateIdentifier;
import org.axonframework.domain.StringAggregateIdentifier;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;

import java.util.Date;
import java.util.List;

public class Transfer extends AbstractAnnotatedAggregateRoot {


    private String transactionRef;
    private Date bookingDate;
    private List<TransactionLeg> legs;

    public Transfer(TransferFundsRequest transferFundsRequest) {
        super(new StringAggregateIdentifier(transferFundsRequest.getTransactionRef()));
        apply(new TransferCreatedEvent(transferFundsRequest));
    }

    public Transfer(AggregateIdentifier identifier) {
        super(identifier);
    }

    @EventHandler
    private void onCreate(TransferCreatedEvent event) {
        TransferFundsRequest transferFundsRequest = event.getTransferFundsRequest();
        this.transactionRef = transferFundsRequest.getTransactionRef();
        this.bookingDate = transferFundsRequest.getBookingDate();
        this.legs = transferFundsRequest.getLegs();
    }
}
