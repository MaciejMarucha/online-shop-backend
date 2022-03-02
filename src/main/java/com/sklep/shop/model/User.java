package com.sklep.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    @JsonIgnore
    @Transient
    @OneToOne(mappedBy = "user")
    private Token token;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AddToCart> cartList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orderList;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(Long idid, String email, String password, String firstName, String lastName, UserRole userRole) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
    }
}
