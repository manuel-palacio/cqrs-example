package net.palace.cqrs.bank;

import com.hazelcast.core.MultiMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ReadModelFacade {

    @Resource(name = "accounts")
    private Map<String, Money> accounts;

    @Resource(name = "transactions")
    private MultiMap<String, Transaction> transactions;


    public Money getAccountBalance(String accountRef) {
        return accounts.get(accountRef);
    }

    public List<Transaction> findTransactions(String accountRef) {
        if (transactions.get(accountRef) == null) {
            throw new AccountNotFoundException(accountRef);
        }
        return new ArrayList<Transaction>(transactions.get(accountRef));
    }
}
