package net.palace.worktest.bank.transfer;

import net.palace.worktest.bank.TransferFundsRequest;
import org.axonframework.domain.DomainEvent;


public class TransferCreatedEvent extends DomainEvent {

    private TransferFundsRequest transferFundsRequest;

    public TransferCreatedEvent(TransferFundsRequest transferFundsRequest) {
        this.transferFundsRequest = transferFundsRequest;
    }

    public TransferFundsRequest getTransferFundsRequest() {
        return transferFundsRequest;
    }
}
