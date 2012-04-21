package net.palace.worktest.bank.account.command;

import net.palace.worktest.bank.Money;

public class CreateAccountCommand {

    String accountRef;
    Money amount;

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
