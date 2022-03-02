package com.sklep.shop.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private final UserRole userRole = UserRole.USER;
}
