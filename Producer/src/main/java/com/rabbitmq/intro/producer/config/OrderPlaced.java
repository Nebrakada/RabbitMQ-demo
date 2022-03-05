package com.rabbitmq.intro.producer.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderPlaced {
  private String exchangeName;
  private List<String> queueNames = new ArrayList<>();
}
