package com.sklep.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhotoDto {
    private Long id;

    @NotBlank
    @JsonIgnore
    private String name;

    @JsonIgnore
    private byte[] file;

    @JsonIgnore
    private Product product;

}
