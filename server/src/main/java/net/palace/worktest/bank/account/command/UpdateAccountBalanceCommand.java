package net.palace.worktest.bank.account.command;

import net.palace.worktest.bank.IllegalTransferRequestException;
import net.palace.worktest.bank.TransactionLeg;
import net.palace.worktest.bank.TransferFundsRequest;

import java.math.BigDecimal;
import java.util.List;

import static ch.lambdaj.Lambda.collect;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sum;

public class UpdateAccountBalanceCommand {
    private TransferFundsRequest transferRequest;

    public UpdateAccountBalanceCommand(TransferFundsRequest transferRequest) {

        this.transferRequest = transferRequest;

        validateTransferIsBetweenAtLeast2Accounts(transferRequest.getLegs());
        validateThatSumOfAllCreditsAndDebitsIsZero(transferRequest.getLegs());
    }

    private void validateTransferIsBetweenAtLeast2Accounts(List<TransactionLeg> transactionLegs) {
        List<String> accountRefs = collect(transactionLegs, on(TransactionLeg.class).getAccountRef());
        if (accountRefs.size() < 2) {
            throw new IllegalTransferRequestException("Transfer must be done between at least two accounts");
        }
    }

    private void validateThatSumOfAllCreditsAndDebitsIsZero(List<TransactionLeg> transactionLegs) {

        BigDecimal sumAllLegs = sum(transactionLegs, on(TransactionLeg.class).getAmount().getAmount());
        if (sumAllLegs.byteValueExact() != 0) {
            throw new IllegalTransferRequestException("Luca has a problem with this transfer funds request");
        }
    }

    public TransferFundsRequest getTransferRequest() {
        return transferRequest;
    }
}
