package help.lixin.producer.service;

import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.messaging.consumer.MessageHandler;

import java.util.Collections;

public class AccountAddCommandReplyService {

    private MessageConsumer messageConsumer;

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public MessageConsumer getMessageConsumer() {
        return messageConsumer;
    }

    public void subscribeToReplyChannel() {
        // subscriberId123456789
        String subscriberId = "subscriberId123456789";
        // 向kakfa创建topic:replyChannel-123456789
        messageConsumer.subscribe(subscriberId, Collections.singleton("replyChannel-123456789"), new MessageHandler() {
            @Override
            public void accept(Message message) {
                System.out.println("==========================================");
                System.out.println("Got message = " + message);
            }
        });
    }
}
