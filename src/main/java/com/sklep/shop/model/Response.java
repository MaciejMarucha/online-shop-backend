package com.sklep.shop.model;

import lombok.Data;

@Data
public class Response {
    private User user;
    private String role;

    public Response() {
    }
}
