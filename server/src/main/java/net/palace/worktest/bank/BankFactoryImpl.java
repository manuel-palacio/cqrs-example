package net.palace.worktest.bank;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Creates instances of the account and transfer service using the Spring framework
 * @see "http://www.springsource.org/"
 */
class BankFactoryImpl implements BankFactory {

    private final ApplicationContext applicationContext = new AnnotationConfigApplicationContext("net.palace.worktest.bank.config");

    @Override
    public AccountService getAccountService() {
        return applicationContext.getBean(AccountService.class);
    }

    @Override
    public TransferService getTransferService() {
        return applicationContext.getBean(TransferService.class);
    }

    @Override
    public void setupInitialData() {

    }
}
