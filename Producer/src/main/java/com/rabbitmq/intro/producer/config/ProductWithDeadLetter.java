package com.rabbitmq.intro.producer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithDeadLetter {
  private String productExchangeName;
  private String productQueueName;
  private String deadLetterExchangeName;
  private String deadLetterQueueName;
  private int messageTTL;
}
