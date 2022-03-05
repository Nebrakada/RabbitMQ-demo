package com.rabbitmq.intro.model.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomerSegment {
  STANDARD("standard"),
  PREMIUM("premium");

  private final String name;

  CustomerSegment from(String name) {
    for (CustomerSegment customerSegment : CustomerSegment.values()) {
      if (customerSegment.getName().equals(name)) {
        return customerSegment;
      }
    }
    throw new IllegalArgumentException("wrong segment name");
  }

  @Override
  public String toString() {
    return name;
  }
}
