package net.palace.worktest.bank;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.palace.worktest.bank.MoneyUtils.toMoney;


public class BankTest {
    static final String SAVINGS_ACCOUNT_1 = "MyAccounts:A:EUR";

    static final String SPENDING_ACCOUNT_1 = "MyAccounts:B:EUR";

    static final String SAVINGS_ACCOUNT_2 = "MyAccounts:C:SEK";

    static final String SPENDING_ACCOUNT_2 = "MyAccounts:D:SEK";

    private AccountService accountService;

    private TransferService transferService;

    @Before
    public void setupTest() throws Exception {
        BankFactory bankFactory = (BankFactory) Class.forName(
                "net.palace.worktest.bank.BankFactoryImpl").newInstance();

        accountService = bankFactory.getAccountService();
        transferService = bankFactory.getTransferService();

        bankFactory.setupInitialData();
    }

    @Test
    public void transferMoney() {
        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T1").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-5.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("5.00 EUR"))
                .build());

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T2").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-10.50 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("10.50 EUR"))
                .build());

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("984.50 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("15.50 EUR"));

        List<Transaction> t1 = transferService.findTransactions(SAVINGS_ACCOUNT_1);
        Assert.assertNotNull(t1);
        Assert.assertEquals(t1.size(), 2);
        Assert.assertEquals(t1.iterator().next().getLegs().size(), 1);

        List<Transaction> t2 = transferService.findTransactions(SPENDING_ACCOUNT_1);
        Assert.assertNotNull(t2);
        Assert.assertEquals(t2.size(), 2);
        Assert.assertEquals(t2.iterator().next().getLegs().size(), 1);
    }

    @Test
    public void transferMoneyUsingMultiLeggedTransaction() {
        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T3").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-5.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("5.00 EUR"))
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-10.50 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("10.50 EUR"))
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-2.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("1.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("1.00 EUR"))
                .build());

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1),
                toMoney("982.50 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1),
                toMoney("17.50 EUR"));
    }

    @Test
    public void transferMoneyUsingMultiLeggedAndMultiCurrencyTransactions() {
        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));
        accountService.createAccount(SAVINGS_ACCOUNT_2, toMoney("1000.00 SEK"));
        accountService.createAccount(SPENDING_ACCOUNT_2, toMoney("0.00 SEK"));

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_2), toMoney("1000.00 SEK"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_2), toMoney("0.00 SEK"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T4").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-5.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("5.00 EUR"))
                .accountRef(SAVINGS_ACCOUNT_2).amount(toMoney("-10.50 SEK"))
                .accountRef(SPENDING_ACCOUNT_2).amount(toMoney("10.50 SEK"))
                .build());

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("995.00 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("5.00 EUR"));
        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_2), toMoney("989.50 SEK"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_2), toMoney("10.50 SEK"));
    }
}
