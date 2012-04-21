package net.palace.worktest.bank.transfer.command;

import net.palace.worktest.bank.account.command.Account;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferCommandHandler {

    private Repository<Transfer> transferRepository;
    private Repository<Account> accountRepository;

    @CommandHandler
    public void createTransfer(TransferCommand command) {

            transferRepository.add(new Transfer(command.getTransferRequest()));

    }

    @Autowired
    public void setTransferRepository(EventSourcingRepository<Transfer> genericRepository) {
        this.transferRepository = genericRepository;
    }

    @Autowired
    public void setAccountRepository(EventSourcingRepository<Account> genericRepository) {
        this.accountRepository = genericRepository;
    }
}
