package com.rabbitmq.intro.consumer.message;

import com.rabbitmq.intro.model.order.Order;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CategorizedOrderMessageConsumer {

  @RabbitListener(queues = {"${rabbitmq.categorized-order.category-a}"})
  public void getCategoryAOrders(Order order) {
    System.out.println("received category A order: " + order);
  }

  @RabbitListener(queues = {"${rabbitmq.categorized-order.premium-customers}"})
  public void getPremiumCustomerOrders(Order order) {
    System.out.println("received premium customer order: " + order);
  }

  @RabbitListener(queues = {"${rabbitmq.categorized-order.cheap-products-cat-c}"})
  public void getCheapCategoryCOrders(Order order) {
    System.out.println("received cheap category C order: " + order);
  }

  @RabbitHandler(isDefault = true)
  public void getDefault(Object object) {
    System.out.println("received default msg: " + object);
  }
}
