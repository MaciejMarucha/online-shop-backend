package com.sklep.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "carts")
public class AddToCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Min(1)
    @NotNull
    private int amount;

    @ManyToOne
    private User user;

    public AddToCart() {
    }

    public AddToCart(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }
}
