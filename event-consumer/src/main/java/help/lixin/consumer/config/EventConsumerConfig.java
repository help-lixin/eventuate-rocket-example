package help.lixin.consumer.config;

import help.lixin.consumer.service.AccountEventConsumer;
import help.lixin.domain.Account;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfig {

    @Bean
    public AccountEventConsumer accountEventConsumer() {
        return new AccountEventConsumer(Account.class.getName().replaceAll("\\.","_"));
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(
            // 定义事件分发工厂,实际内部是持有一个MessageConsumer
            DomainEventDispatcherFactory domainEventDispatcherFactory,
            // 引用上面的:AccountEventConsumer
            AccountEventConsumer target) {
        return domainEventDispatcherFactory.make("eventDispatcherId", target.domainEventHandlers());
    } // end
}
