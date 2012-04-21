package net.palace.cqrs.bank.transfer.command;

import net.palace.cqrs.bank.TransferFundsRequest;

public class TransferCommand {

    private TransferFundsRequest transferRequest;

    public TransferCommand(TransferFundsRequest transferRequest) {

        this.transferRequest = transferRequest;
    }


    public TransferFundsRequest getTransferRequest() {
        return transferRequest;
    }
}
