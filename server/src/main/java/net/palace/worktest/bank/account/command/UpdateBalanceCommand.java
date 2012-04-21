package net.palace.worktest.bank.account.command;

import net.palace.worktest.bank.TransferFundsRequest;

public class UpdateBalanceCommand {
    private TransferFundsRequest transferRequest;

    public UpdateBalanceCommand(TransferFundsRequest transferRequest) {

        this.transferRequest = transferRequest;
    }

    public TransferFundsRequest getTransferRequest() {
        return transferRequest;
    }
}
