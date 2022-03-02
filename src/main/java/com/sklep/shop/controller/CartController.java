package com.sklep.shop.controller;

import com.sklep.shop.model.*;
import com.sklep.shop.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/account/cart")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CartController {
    private final AddToCartService addToCartService;
    private final TokenService tokenService;
    private final OrderService orderService;
    private final DetailService detailService;
    private final ProductService productService;

    public CartController(AddToCartService addToCartService, TokenService tokenService, OrderService orderService, DetailService detailService, ProductService productService) {
        this.addToCartService = addToCartService;
        this.tokenService = tokenService;
        this.orderService = orderService;
        this.detailService = detailService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<List<AddToCart>> returnCart(@RequestBody User user, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        List<AddToCart> list = addToCartService.findByUserId(user.getId());
        return new ResponseEntity<List<AddToCart>>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddToCart> deleteOneCart(@RequestHeader("pin") String pin, @PathVariable Long id) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        if (addToCartService.findById(id).isEmpty()) {
            return new ResponseEntity<AddToCart>(HttpStatus.NOT_FOUND);
        }
        addToCartService.deleteById(id);
        return new ResponseEntity<AddToCart>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(@RequestBody List<AddToCart> addToCartList, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        addToCartService.deleteByUser(addToCartList.get(0).getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody Order order, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }

        List<Detail> details = order.getDetails();
        for(Detail x : details){
            x.setOrder(order);
            Product productById = productService.findById(x.getProduct().getId()).get();
            productById.setAmountInWareHouse(productById.getAmountInWareHouse()-x.getAmount());
            productService.save(productById);
        }

        //odejmij w bazie danych od ilosci w magazynie
        order.setDetails(details);
        orderService.save(order);
        detailService.saveAll(order.getDetails());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
