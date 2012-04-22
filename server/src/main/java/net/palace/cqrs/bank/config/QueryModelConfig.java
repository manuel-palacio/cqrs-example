package net.palace.cqrs.bank.config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.MultiMap;
import net.palace.cqrs.bank.Money;
import net.palace.cqrs.bank.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class QueryModelConfig {

    @Bean(name = "transactions")
    public MultiMap<String, Transaction> transactions() {
        return Hazelcast.getMultiMap("transactions");
    }

    @Bean(name = "accounts")
    public Map<String, Money> accounts() {
        return Hazelcast.getMap("accounts");
    }
}
