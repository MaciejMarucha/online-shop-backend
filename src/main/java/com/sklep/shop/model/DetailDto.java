package com.sklep.shop.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DetailDto {
    private Long id;

    private Order order;
    private Product product;

    @NotNull
    @Min(1)
    private int amount;

    private double inTotal;
}
