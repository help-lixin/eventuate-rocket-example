package io.eventuate.tram.consumer.common;

import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.common.MessageImpl;
import io.eventuate.tram.messaging.common.MessageInterceptor;
import io.eventuate.tram.messaging.common.SubscriberIdAndMessage;
import io.eventuate.tram.messaging.consumer.MessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DecoratedMessageHandlerFactoryTest {
    public static void main(String[] args) {
        MessageInterceptor[] interceptors = new MessageInterceptor[]{};

        MessageHandlerDecorator prePostHandlerMessageHandlerDecorator = new PrePostHandlerMessageHandlerDecorator(interceptors);
        DuplicateMessageDetector noopDuplicateMessageDetector = new NoopDuplicateMessageDetector();
        MessageHandlerDecorator duplicateDetectingMessageHandlerDecorator = new DuplicateDetectingMessageHandlerDecorator(noopDuplicateMessageDetector);
        MessageHandlerDecorator prePostReceiveMessageHandlerDecorator = new PrePostReceiveMessageHandlerDecorator(interceptors);

        List<MessageHandlerDecorator> messageHandlerDecorators = new ArrayList<>(3);
        messageHandlerDecorators.add(prePostHandlerMessageHandlerDecorator);
        messageHandlerDecorators.add(duplicateDetectingMessageHandlerDecorator);
        messageHandlerDecorators.add(prePostReceiveMessageHandlerDecorator);

        MessageHandler messageHandler = new MessageHandler() {
            @Override
            public void accept(Message message) {
                System.out.println("*********************************************");
                System.out.println(message);
                System.out.println("*********************************************");
            }
        };

        Consumer<SubscriberIdAndMessage> messageHandlerDecorate = new Consumer<SubscriberIdAndMessage>() {
            @Override
            public void accept(SubscriberIdAndMessage subscriberIdAndMessage) {
                messageHandler.accept(subscriberIdAndMessage.getMessage());
            }
        };

        Message message = new MessageImpl();
        message.setHeader(Message.ID, "1");
        message.setHeader(Message.DESTINATION, "test-");
        message.setPayload("hello world");

        MessageHandlerDecoratorChain chain = buildChain(messageHandlerDecorators, messageHandlerDecorate);
        chain.invokeNext(new SubscriberIdAndMessage("topicGroup", message));
    }


    public static MessageHandlerDecoratorChain buildChain(List<MessageHandlerDecorator> handlers, Consumer<SubscriberIdAndMessage> consumer) {
        if (handlers.isEmpty()) {
            return consumer::accept;
        } else {
            MessageHandlerDecorator head = handlers.get(0);
            List<MessageHandlerDecorator> tail = handlers.subList(1, handlers.size());

            MessageHandlerDecoratorChain chain = new MessageHandlerDecoratorChain() {
                @Override
                public void invokeNext(SubscriberIdAndMessage subscriberIdAndMessage) {
                    head.accept(subscriberIdAndMessage, buildChain(tail, consumer));
                }
            };
            return chain;
        }
    }
}
