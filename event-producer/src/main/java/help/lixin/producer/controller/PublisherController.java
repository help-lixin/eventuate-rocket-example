package help.lixin.producer.controller;


import help.lixin.domain.Account;
import help.lixin.domain.AccountAddCommand;
import help.lixin.domain.AccountDebited;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.messaging.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Random;

@RestController
public class PublisherController {

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private CommandProducer commandProducer;

    @Autowired
    private MessageProducer messageProducer;


    @GetMapping("/event")
    public String event() {
        String aggregateType = Account.class.getName().replaceAll("\\.","_");
        Long aggregateId = System.currentTimeMillis();
        Long uniqueId = aggregateId;
        DomainEvent domainEvent = new AccountDebited(uniqueId);
        // 发布事件.
        domainEventPublisher.publish(
                //
                aggregateType,
                //
                uniqueId,
                //
                Collections.singletonList(domainEvent));
        return "SUCCESS";
    }

    @GetMapping("/cmd")
    public String cmd() {
        Random random = new Random();
        int val = random.nextInt();

        AccountAddCommand command = new AccountAddCommand();
        command.setMoney(new BigDecimal(val));

        return commandProducer.send( //
                "commandChannel123456789",
                command,
                // 回复端的命令消息id
                // replyChannel-123456789
                "replyChannel-123456789",
                Collections.emptyMap());
    }
}
