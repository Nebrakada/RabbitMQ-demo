package com.rabbitmq.intro.producer.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategorizedOrder {
  private String exchangeName;
  private List<String> queueNames = new ArrayList<>();
  private List<String> routingKeys = new ArrayList<>();
}
