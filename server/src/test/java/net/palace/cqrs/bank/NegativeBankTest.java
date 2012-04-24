package net.palace.cqrs.bank;


import net.palace.cqrs.bank.config.ApplicationConfig;
import net.palace.cqrs.bank.config.QueryModelConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static net.palace.cqrs.bank.MoneyUtils.toMoney;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, QueryModelConfig.class}, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NegativeBankTest {

    static final String SAVINGS_ACCOUNT_1 = "MyAccounts:A:EUR";

    static final String SPENDING_ACCOUNT_1 = "MyAccounts:B:EUR";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;


    @Test(expected = InsufficientFundsException.class)
    public void cannot_overdraw_account() {

        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T3").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-5000.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("5000.00 EUR"))
                .build());

        assertTrue(transferService.findTransactions(SAVINGS_ACCOUNT_1).isEmpty());
        assertTrue(transferService.findTransactions(SPENDING_ACCOUNT_1).isEmpty());

    }

    @Test(expected = IllegalTransferRequestException.class)
    public void accounting_principle_holds() {

        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T3").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-50.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("51.00 EUR"))
                .build());

        assertTrue(transferService.findTransactions(SAVINGS_ACCOUNT_1).isEmpty());
        assertTrue(transferService.findTransactions(SPENDING_ACCOUNT_1).isEmpty());


    }

    @Test(expected = AccountNotFoundException.class)
    public void transfer_to_non_existing_account_fails() {

        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T3").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-1000.00 EUR"))
                .accountRef("foo").amount(toMoney("1000.00 EUR"))
                .build());

        assertTrue(transferService.findTransactions(SAVINGS_ACCOUNT_1).isEmpty());

    }

    @Test(expected = IllegalTransferRequestException.class)
    public void legs_for_account_have_to_be_in_same_currency() {

        accountService.createAccount(SAVINGS_ACCOUNT_1, toMoney("1000.00 EUR"));
        accountService.createAccount(SPENDING_ACCOUNT_1, toMoney("0.00 EUR"));

        assertEquals(accountService.getBalance(SAVINGS_ACCOUNT_1), toMoney("1000.00 EUR"));
        assertEquals(accountService.getBalance(SPENDING_ACCOUNT_1), toMoney("0.00 EUR"));

        transferService.transferFunds(TransferFundsRequest.builder()
                .transactionRef("T3").transactionType("testing")
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-5.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("5.00 EUR"))
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-10.50 SEK"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("10.50 EUR"))
                .accountRef(SAVINGS_ACCOUNT_1).amount(toMoney("-2.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("1.00 EUR"))
                .accountRef(SPENDING_ACCOUNT_1).amount(toMoney("1.00 SEK"))
                .build());

        assertTrue(transferService.findTransactions(SAVINGS_ACCOUNT_1).isEmpty());

    }

    @Test(expected = AccountNotFoundException.class)
    public void request_transaction_for_non_existing_account_fails() {
        transferService.findTransactions("foo");
    }

}
