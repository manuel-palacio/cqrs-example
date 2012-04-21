package net.palace.worktest.bank.transfer.command;

import net.palace.worktest.bank.TransferFundsRequest;

public class TransferCommand {

    private TransferFundsRequest transferRequest;

    public TransferCommand(TransferFundsRequest transferRequest) {

        this.transferRequest = transferRequest;
    }


    public TransferFundsRequest getTransferRequest() {
        return transferRequest;
    }
}
