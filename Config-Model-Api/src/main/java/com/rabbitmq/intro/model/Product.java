package com.rabbitmq.intro.model;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Product {

    private static final Faker faker = new Faker();
    private String name;
    private BigDecimal price;
    private Category category;

    public static Product randomProduct() {
        return Product.builder()
                .name(faker.commerce().productName())
                .price(new BigDecimal(faker.commerce().price(1, 1000)))
                .category(List.of(Category.values()).get(new Random().nextInt(3)))
                .build();
    }

    ProductAddedDTO toProductAddedDTO() {
        return new ProductAddedDTO(name, price, category);
    }

    @Override
    public String toString() {
        return "Product: " + name + ", price: " + price + ", category: " + category;
    }
}
