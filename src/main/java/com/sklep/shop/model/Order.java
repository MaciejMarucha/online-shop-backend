package com.sklep.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @NotBlank
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;

    @NotNull
    @Min(100000000)
    @Max(999999999)
    private int phone;

    @OneToMany(mappedBy = "order")
    private List<Detail> details;
    private LocalDateTime localDateTime;

    private Status status;
    @NotNull
    private double toPay;

    public Order() { ;
        this.localDateTime = LocalDateTime.now();
        this.status = Status.UNCOMPLETED;
    }
}
