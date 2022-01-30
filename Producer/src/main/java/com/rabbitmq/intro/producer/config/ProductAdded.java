package com.rabbitmq.intro.producer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAdded {

  private String exchangeName;
  private String queueName;
}
