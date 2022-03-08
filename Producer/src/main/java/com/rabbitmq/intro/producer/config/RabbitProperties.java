package com.rabbitmq.intro.producer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Getter
@Setter
public class RabbitProperties {

  private ProductAdded productAdded = new ProductAdded();
  private OrderPlaced orderPlaced = new OrderPlaced();
  private CategorizedOrder categorizedOrder = new CategorizedOrder();
  private ProductWithDeadLetter productWithDeadLetter = new ProductWithDeadLetter();

}
