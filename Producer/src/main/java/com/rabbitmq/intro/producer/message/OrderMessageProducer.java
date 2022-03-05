package com.rabbitmq.intro.producer.message;

import com.rabbitmq.intro.model.order.Order;
import com.rabbitmq.intro.producer.config.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public void send(Order order) {
        rabbitTemplate.convertAndSend(
            rabbitProperties.getOrderPlaced().getExchangeName(),
            "", // routing key is ignored in fanout exchange
            order);
    }


//    @Scheduled(fixedRate = 2000)
    public void sendPeriodically() {
        Order order = Order.randomOrder();
        rabbitTemplate.convertAndSend(
                rabbitProperties.getOrderPlaced().getExchangeName(),
                "", // routing key is ignored in fanout exchange
                order);
    }


}
