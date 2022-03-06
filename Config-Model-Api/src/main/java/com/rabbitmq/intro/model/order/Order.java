package com.rabbitmq.intro.model.order;

import com.github.javafaker.Faker;
import com.rabbitmq.intro.model.customer.Customer;
import com.rabbitmq.intro.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Order {

    private static final Faker faker = new Faker();
    private Product product;
    private int quantity;
    private Customer customer;

    OrderPlacedDTO toOrderDTO() {
        return new OrderPlacedDTO(product, quantity, customer);
    }

    public static Order randomOrder() {
        Product product = Product.randomProduct();
        Customer customer = Customer.randomCustomer();
        int quantity = faker.number().numberBetween(1, 10);
        return new Order(product, quantity, customer);
    }
}
