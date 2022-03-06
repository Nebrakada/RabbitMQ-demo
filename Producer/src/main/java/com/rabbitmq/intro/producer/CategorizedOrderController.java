package com.rabbitmq.intro.producer;

import com.rabbitmq.intro.model.order.Order;
import com.rabbitmq.intro.model.order.OrderPlacedDTO;
import com.rabbitmq.intro.producer.message.CategorizedOrderMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorized-orders")
public class CategorizedOrderController {

  private final CategorizedOrderMessageProducer categorizedOrderMessageProducer;

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createProduct(@RequestBody OrderPlacedDTO orderPlacedDTO) {
    // validate

    // send to broker
    System.out.println("sending categorized order to broker: " + orderPlacedDTO.toOrder());
    categorizedOrderMessageProducer.send(orderPlacedDTO.toOrder());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{quantity}")
  public ResponseEntity<Void> addRandomCategorizedOrder(@PathVariable int quantity) {

    for (int i = 0; i < quantity; i++) {
      Order order = Order.randomOrder();
      System.out.println("sending categorized order to broker: " + order);
      categorizedOrderMessageProducer.send(order);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
