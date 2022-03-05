package com.rabbitmq.intro.producer;

import com.rabbitmq.intro.model.Product;
import com.rabbitmq.intro.model.ProductAddedDTO;
import com.rabbitmq.intro.producer.message.ProductAddedMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

  private final ProductAddedMessageProducer productAddedMessageProducer;

  @CrossOrigin
  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> addProduct(@RequestBody ProductAddedDTO productAddedDTO) {

    System.out.println("sending product to broker: " + productAddedDTO.toProduct());
    productAddedMessageProducer.send(productAddedDTO.toProduct());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{quantity}")
  public ResponseEntity<Void> addRandomProduct(@PathVariable int quantity) {

    for (int i = 0; i < quantity; i++) {
      Product product = Product.randomProduct();
      System.out.println("sending product to broker: " + product);
      productAddedMessageProducer.send(product);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
