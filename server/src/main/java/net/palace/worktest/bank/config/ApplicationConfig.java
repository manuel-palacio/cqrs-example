package net.palace.worktest.bank.config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.MultiMap;
import net.palace.worktest.bank.Money;
import net.palace.worktest.bank.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.Map;

@Configuration
@ImportResource("classpath*:application-context.xml")
public class ApplicationConfig {

    @Bean(name = "transactions")
    public MultiMap<String, Transaction> transactions() {
        return Hazelcast.getMultiMap("transactions");
    }

    @Bean(name = "accounts")
    public Map<String, Money> accounts() {
        return Hazelcast.getMap("accounts");
    }
}
