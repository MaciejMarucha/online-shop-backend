package com.sklep.shop.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotNull
    @Min(1)
    private int amountInWareHouse;
    private PhotoDto photo;

    public ProductDto(Long id, String name, double price, int amountInWareHouse, PhotoDto photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amountInWareHouse = amountInWareHouse;
        this.photo = photo;
    }

    public ProductDto() {
    }
}
