package com.rabbitmq.intro.model.customer;

import com.github.javafaker.Faker;
import lombok.*;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Customer {

    private static final Faker faker = new Faker();

    private String name;
    private CustomerSegment customerSegment;

    public static Customer randomCustomer() {
        return Customer.builder()
                .name(faker.name().name())
                .customerSegment(List.of(CustomerSegment.values()).get(new Random().nextInt(2)))
                .build();
    }
}
