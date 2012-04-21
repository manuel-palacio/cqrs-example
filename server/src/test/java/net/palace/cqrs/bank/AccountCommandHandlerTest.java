package net.palace.cqrs.bank;

import net.palace.cqrs.bank.account.AccountCreatedEvent;
import net.palace.cqrs.bank.account.command.Account;
import net.palace.cqrs.bank.account.command.AccountCommandHandler;
import net.palace.cqrs.bank.account.command.CreateAccountCommand;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class AccountCommandHandlerTest {

    private FixtureConfiguration fixture;

       @Before
       public void setUp() throws Exception {
           fixture = Fixtures.newGivenWhenThenFixture();
           AccountCommandHandler commandHandler = new AccountCommandHandler();
           commandHandler.setOrderRepository(fixture.createGenericRepository(Account.class));
           fixture.registerAnnotatedCommandHandler(commandHandler);
       }

       @Test
       public void testCreateOrder() {
           fixture.given()
                   .when(new CreateAccountCommand("123", new Money(BigDecimal.TEN, Currency.getInstance("EUR"))))
                   .expectEvents(new AccountCreatedEvent(new Money(BigDecimal.TEN, Currency.getInstance("EUR"))));
       }
}
