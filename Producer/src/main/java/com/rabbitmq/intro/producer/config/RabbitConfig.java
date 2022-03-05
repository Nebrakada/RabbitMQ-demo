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
}
