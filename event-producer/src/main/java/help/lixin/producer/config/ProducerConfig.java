package help.lixin.producer.config;

import help.lixin.producer.service.AccountAddCommandReplyService;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public AccountAddCommandReplyService accountAddCommandReplyService(MessageConsumer messageConsumer) {
        AccountAddCommandReplyService accountAddCommandReplyService = new AccountAddCommandReplyService();
        accountAddCommandReplyService.setMessageConsumer(messageConsumer);
        accountAddCommandReplyService.subscribeToReplyChannel();
        return accountAddCommandReplyService;
    }
}
