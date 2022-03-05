package com.rabbitmq.intro.consumer.message;

import com.rabbitmq.intro.model.product.Product;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"${rabbitmq.product-added}"})
public class ProductAddedMessageConsumer {

    @RabbitHandler
    public void getProductCreated(Product product) {
        System.out.println("received msg: " + product);
    }

    @RabbitHandler(isDefault = true)
    public void getDefault(Object object) {
        System.out.println("received default msg: " + object);
    }
}
