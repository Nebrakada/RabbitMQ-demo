package com.rabbitmq.intro.producer.message;

import com.rabbitmq.intro.model.product.Product;
import com.rabbitmq.intro.producer.config.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAddedMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    /**
     * Example of Direct Exchange message sending method
     * routing key is ignored so might be empty (default exchange)
     *
     * @param product product is serialized to json thanks to Jackson2JsonMessageConverter in RabbitConfig class
     *                and send to exchange on broker using autoconfigured rabbitTemplate
     */
    public void send(Product product) {
        rabbitTemplate.convertAndSend(
                rabbitProperties.getProductAdded().getExchangeName(),
                "",
                product);
    }
}
