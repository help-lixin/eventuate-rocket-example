package help.lixin.consumer.service;

import help.lixin.domain.AccountAddCommand;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class AccountAddCommandHandler {
    private String commandChannel;


    public AccountAddCommandHandler(String commandChannel) {
        this.commandChannel = commandChannel;
    }

    public CommandHandlers getCommandHandlers() {
        return CommandHandlersBuilder.fromChannel(commandChannel) //
                .onMessage(AccountAddCommand.class, this::handler) //
                .build();

    }

    public Message handler(CommandMessage<AccountAddCommand> cm) {
        System.out.println("customerId=" + cm.getCommand().getMoney());
        System.out.println("cm=" + cm);
        System.out.println("*******************开始,消费延迟10秒*********************************");
        return withSuccess();
    }
}
