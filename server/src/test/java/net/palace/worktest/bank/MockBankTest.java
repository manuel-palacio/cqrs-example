package net.palace.worktest.bank;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static net.palace.worktest.bank.MoneyUtils.toMoney;

/**
 * Simple mock unit test demonstrating basic functionality of the bank system.
 *
 * @version 1.0
 */
public class MockBankTest {
    static final String SAVINGS_ACCOUNT_1 = "MyAccounts:A:EUR";

    static final String SPENDING_ACCOUNT_1 = "MyAccounts:B:EUR";

    private AccountService accountService;

    private TransferService transferService;

    @Before
    public void setupMocks() throws Exception {
        accountService = Mockito.mock(AccountService.class);

        Mockito.when(accountService.getBalance(SAVINGS_ACCOUNT_1))
                .thenReturn(toMoney("1000.00 EUR"))
                .thenReturn(toMoney("984.50 EUR"));
        Mockito.when(accountService.getBalance(SPENDING_ACCOUNT_1))
                .thenReturn(toMoney("0.00 EUR"))
                .thenReturn(toMoney("15.50 EUR"));

        transferService = Mockito.mock(TransferService.class);

        Mockito.doNothing().when(transferService).transferFunds(Mockito.any(TransferFundsRequest.class));

        List<TransactionLeg> t1Legs = new ArrayList<TransactionLeg>();
        t1Legs.add(new TransactionLeg(SAVINGS_ACCOUNT_1, toMoney("-5.00 EUR")));
        t1Legs.add(new TransactionLeg(SPENDING_ACCOUNT_1, toMoney("5.00 EUR")));

        Mockito.when(transferService.findTransactions(SAVINGS_ACCOUNT_1))
                .thenReturn(Collections.singletonList(new Transaction("T1", "testing", new Date(), t1Legs)));

        List<TransactionLeg> t2Legs = new ArrayList<TransactionLeg>();
        t2Legs.add(new TransactionLeg(SAVINGS_ACCOUNT_1, toMoney("-10.50 EUR")));
        t2Legs.add(new TransactionLeg(SPENDING_ACCOUNT_1, toMoney("10.50 EUR")));

        Mockito.when(transferService.findTransactions(SPENDING_ACCOUNT_1))
                .thenReturn(Collections.singletonList(new Transaction("T2", "testing", new Date(), t2Legs)));
    }

    @Test
    public void transferMoney() {
        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1),
                toMoney("1000.00 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1),
                toMoney("0.00 EUR"));

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

        Assert.assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1),
                toMoney("984.50 EUR"));
        Assert.assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1),
                toMoney("15.50 EUR"));

        List<Transaction> t1 = transferService.findTransactions(SAVINGS_ACCOUNT_1);
        Assert.assertNotNull(t1);
        Assert.assertEquals(t1.size(), 1);
        Assert.assertEquals(t1.iterator().next().getLegs().size(), 2);

        List<Transaction> t2 = transferService.findTransactions(SPENDING_ACCOUNT_1);
        Assert.assertNotNull(t2);
        Assert.assertEquals(t2.size(), 1);
        Assert.assertEquals(t2.iterator().next().getLegs().size(), 2);
    }
}
