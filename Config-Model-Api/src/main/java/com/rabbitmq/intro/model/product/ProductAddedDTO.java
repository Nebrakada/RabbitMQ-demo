package com.rabbitmq.intro.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductAddedDTO {
  private String name;
  private BigDecimal price;
  private Category category;

  public Product toProduct() {
    return new Product(name, price, category);
  }
}
