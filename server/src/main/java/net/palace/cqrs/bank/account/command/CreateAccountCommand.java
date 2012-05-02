package net.palace.cqrs.bank.account.command;

import net.palace.cqrs.bank.Money;

public class CreateAccountCommand {

    private String accountRef;

    private Money amount;

    public CreateAccountCommand(String accountRef, Money amount) {
        this.accountRef = accountRef;
        this.amount = amount;
    }

    public String getAccountRef() {
        return accountRef;
    }

    public Money getAmount() {
        return amount;
    }
}
