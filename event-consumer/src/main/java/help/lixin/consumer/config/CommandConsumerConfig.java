package help.lixin.consumer.config;

import help.lixin.consumer.service.AccountAddCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConsumerConfig {
    @Bean
    public AccountAddCommandHandler accountAddCommandHandler() {
        // 会在kafka里创建一个topic:commandChannel123456789
        return new AccountAddCommandHandler("commandChannel123456789");
    }

    @Bean
    public CommandDispatcher commandDispatcher(CommandDispatcherFactory commandDispatcherFactory, //
                                               AccountAddCommandHandler target) {
        return commandDispatcherFactory.make("commandDispatcheId123456789", target.getCommandHandlers());
    }
}
