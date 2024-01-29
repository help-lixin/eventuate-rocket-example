package help.lixin.consumer.service;

import help.lixin.domain.AccountDebited;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

public class AccountEventConsumer {
    private final String aggregateType;

    public AccountEventConsumer(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                // 聚合根对象:Account
                .forAggregateType(aggregateType)
                // 事件处理
                .onEvent(AccountDebited.class, this::handleAccountDebited)
                //
                .build();
    }

    public void handleAccountDebited(DomainEventEnvelope<AccountDebited> event) {
        System.out.println(event);
    }
}
