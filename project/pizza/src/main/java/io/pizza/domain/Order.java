package io.pizza.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Order {
	private String productName;
	private Long orderedOn;
}
