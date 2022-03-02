package com.sklep.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotNull
    @Min(1)
    private int amountInWareHouse;

    @JsonIgnore
    @OneToOne
    private Photo photo;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Detail> detail;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<AddToCart> addToCartList;

    public Product() {
    }

    public Product(Long id, String name, int amountInWareHouse, double price, Photo photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amountInWareHouse = amountInWareHouse;
        this.photo = photo;
    }
}
