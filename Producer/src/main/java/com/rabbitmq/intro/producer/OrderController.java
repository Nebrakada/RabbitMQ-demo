package com.rabbitmq.intro.producer;

import com.rabbitmq.intro.model.order.Order;
import com.rabbitmq.intro.model.order.OrderPlacedDTO;
import com.rabbitmq.intro.producer.message.OrderMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{quantity}")
    public ResponseEntity<Void> addRandomOrder(@PathVariable int quantity) {

        for (int i = 0; i < quantity; i++) {
            Order order = Order.randomOrder();
            System.out.println("sending order to broker: " + order);
            orderMessageProducer.send(order);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
