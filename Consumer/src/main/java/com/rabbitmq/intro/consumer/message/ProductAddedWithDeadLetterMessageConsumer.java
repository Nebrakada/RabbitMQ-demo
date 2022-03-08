package com.rabbitmq.intro.consumer.message;

import com.rabbitmq.intro.model.product.Product;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductAddedWithDeadLetterMessageConsumer {

    @RabbitListener(queues = {"${rabbitmq.product-with-dead-letter.product-queue-name}"})
    public void getProductCreated(Product product, Message message) {
        System.out.println("received msg: " + product);

        printMessageHeaders(message);

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new AmqpRejectAndDontRequeueException("product price is lower than zero");
        }

    }

    @RabbitListener(queues = {"${rabbitmq.product-with-dead-letter.dead-letter-queue}"})
    public void getDroppedProductMessages(Product product, Message message) {
        System.out.println("DLQ received message: " + product);
        printMessageHeaders(message);
    }

    private void printMessageHeaders(Message message) {
        System.out.println("Message headers:");
        message.getMessageProperties().getHeaders()
                .forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
