package com.rabbitmq.intro.producer;

import com.rabbitmq.intro.model.order.OrderPlacedDTO;
import com.rabbitmq.intro.producer.message.OrderMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderMessageProducer orderMessageProducer;

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createProduct(@RequestBody OrderPlacedDTO orderPlacedDTO) {

    System.out.println("sending placed order to broker: " + orderPlacedDTO.toOrder());
    orderMessageProducer.send(orderPlacedDTO.toOrder());

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
