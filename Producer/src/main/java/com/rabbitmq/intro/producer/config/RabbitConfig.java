package com.rabbitmq.intro.producer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final AmqpAdmin amqpAdmin;
    private final RabbitProperties rabbitProperties;

    @Bean
    @Qualifier("myRabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void createSchema() {
        createSchemaForCreateProduct(amqpAdmin);
        createSchemaForOrderPlaced(amqpAdmin);
        createSchemaForCategorizedOrder(amqpAdmin);
    }

    private void createSchemaForCreateProduct(AmqpAdmin amqpAdmin) {
        DirectExchange productAddedExchange = new DirectExchange(rabbitProperties.getProductAdded().getExchangeName());
        amqpAdmin.declareExchange(productAddedExchange);

        Queue productAddedQueue = new Queue(rabbitProperties.getProductAdded().getQueueName(), true, false, false);
        amqpAdmin.declareQueue(productAddedQueue);

        Binding productAddedBinding = new Binding(
                productAddedQueue.getName(),
                Binding.DestinationType.QUEUE,
                productAddedExchange.getName(),
                "",
                null);
        amqpAdmin.declareBinding(productAddedBinding);
    }

    private void createSchemaForOrderPlaced(AmqpAdmin amqpAdmin) {
        FanoutExchange orderPlacedExchange = new FanoutExchange(rabbitProperties.getOrderPlaced().getExchangeName());
        amqpAdmin.declareExchange(orderPlacedExchange);

        Queue orderPlacedQueue;
        Binding orderPlacedBinding;
        for (String queueName : rabbitProperties.getOrderPlaced().getQueueNames()) {
            orderPlacedQueue = new Queue(
                    queueName,
                    true, false, false);
            amqpAdmin.declareQueue(orderPlacedQueue);

            orderPlacedBinding = new Binding(
                    orderPlacedQueue.getName(),
                    Binding.DestinationType.QUEUE,
                    orderPlacedExchange.getName(),
                    "",
                    null);
            amqpAdmin.declareBinding(orderPlacedBinding);
        }
    }

    private void createSchemaForCategorizedOrder(AmqpAdmin amqpAdmin) {
        TopicExchange categorizedOrderExchange = new TopicExchange(rabbitProperties.getCategorizedOrder().getExchangeName());
        amqpAdmin.declareExchange(categorizedOrderExchange);

        Queue categorizedOrderQueue;
        Binding categorizedOrderBinding;

        List<String> queueNames = rabbitProperties.getCategorizedOrder().getQueueNames();
        List<String> routingKeys = rabbitProperties.getCategorizedOrder().getRoutingKeys();

        if (queueNames.size() != routingKeys.size()) {
            throw new IllegalArgumentException("Queue names must have corresponding routing keys");
        }

        for (int i = 0; i < queueNames.size(); i++) {
            String queueName = queueNames.get(i);
            String routingKey = routingKeys.get(i);

            categorizedOrderQueue = new Queue(
                    queueName,
                    true, false, false);
            amqpAdmin.declareQueue(categorizedOrderQueue);

            categorizedOrderBinding = new Binding(
                    categorizedOrderQueue.getName(),
                    Binding.DestinationType.QUEUE,
                    categorizedOrderExchange.getName(),
                    routingKey,
                    null);
            amqpAdmin.declareBinding(categorizedOrderBinding);

            System.out.println("===========================================");
            System.out.println("Created Queue [ " + queueName + " ], with routing key: [ " + routingKey
                    + " ], bound to exchange: [ " + categorizedOrderExchange + " ]");
            System.out.println("===========================================");
        }
    }

}
