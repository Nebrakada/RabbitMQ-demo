package com.rabbitmq.intro.model.order;

import com.rabbitmq.intro.model.customer.Customer;
import com.rabbitmq.intro.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderPlacedDTO {
    private Product product;
    private int quantity;
    private Customer customer;


    public Order toOrder() {
        return new Order(product, quantity, customer);
    }
}
