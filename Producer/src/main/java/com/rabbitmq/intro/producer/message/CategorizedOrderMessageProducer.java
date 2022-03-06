package com.rabbitmq.intro.producer.message;

import com.rabbitmq.intro.model.order.Order;
import com.rabbitmq.intro.model.product.Category;
import com.rabbitmq.intro.producer.config.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CategorizedOrderMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    private static final BigDecimal TWO_HUNDRED_THRESHOLD = new BigDecimal("200");
    private static final BigDecimal SIX_HUNDRED_THRESHOLD = new BigDecimal("600");

    public void send(Order order) {

        String routingKey = prepareRoutingKey(order);
        System.out.println("Sending categorized order with routing key: " + routingKey);

        rabbitTemplate.convertAndSend(
                rabbitProperties.getCategorizedOrder().getExchangeName(),
                routingKey, // routing key USED in topic exchange
                order);
    }

//    @Scheduled(fixedRate = 3000)
    public void sendPeriodically() {
        Order order = Order.randomOrder();
        String routingKey = prepareRoutingKey(order);
        System.out.println("Sending categorized order with routing key: " + routingKey);

        rabbitTemplate.convertAndSend(
                rabbitProperties.getCategorizedOrder().getExchangeName(),
                routingKey,
                order);
    }


    private String prepareRoutingKey(Order order) {
        Category category = order.getProduct().getCategory();
        String customerSegment = order.getCustomer().getCustomerSegment().getName();
        String priceRange = getPriceRange(order.getProduct().getPrice());
        return category + "." + customerSegment + "." + priceRange;
    }

    private String getPriceRange(BigDecimal price) {
        String priceRange = "high";
        if (price.compareTo(TWO_HUNDRED_THRESHOLD) < 0) {
            priceRange = "low";
        } else if (price.compareTo(SIX_HUNDRED_THRESHOLD) < 0) {
            priceRange = "medium";
        }
        return priceRange;
    }


}
