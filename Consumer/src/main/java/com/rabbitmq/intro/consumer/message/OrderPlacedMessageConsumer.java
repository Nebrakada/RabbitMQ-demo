package com.rabbitmq.intro.consumer.message;

import com.rabbitmq.intro.model.order.Order;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacedMessageConsumer {

  @RabbitListener(queues = {"${rabbitmq.order-placed.sales}"})
  @RabbitHandler
  public void getOrderForSales(Order order) {
    System.out.println("SALES received order: " + order);
  }

  @RabbitListener(queues = {"${rabbitmq.order-placed.warehouse}"})
  public void getOrderForWarehouse(Order order) {
    System.out.println("WAREHOUSE received order: " + order);
  }

  @RabbitListener(queues = {"${rabbitmq.order-placed.logs}"})
  public void getOrderForLogs(Order order) {
    System.out.println("LOGS received order: " + order);
  }

  @RabbitHandler(isDefault = true)
  public void getDefault(Object object) {
    System.out.println("received default msg: " + object);
  }
}
