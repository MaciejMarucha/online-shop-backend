package com.sklep.shop.controller;

import com.sklep.shop.model.*;
import com.sklep.shop.service.DetailService;
import com.sklep.shop.service.OrderService;
import com.sklep.shop.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/account/orders")
public class OrderController {

    private final TokenService tokenService;
    private final OrderService orderService;
    private final DetailService detailService;
    private ModelMapper modelMapper;

    public OrderController(TokenService tokenService, OrderService orderService, DetailService detailService, ModelMapper modelMapper) {
        this.detailService = detailService;
        this.tokenService = tokenService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<?> returnOrders(@RequestBody User user, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        List<Order> allByUserId = orderService.findAllByUserId(user.getId());
//        for(Order x : allByUserId){
//            x.getDetails()
//        }
        return new ResponseEntity<>(allByUserId, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> returnAllOrders(@RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        List<Order> allOrders = orderService.findAll();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getDetails(@RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        List<Detail> detailList = detailService.findAll();
        List<DetailDto> collect = detailList.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @PostMapping("/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        if (orderService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.findById(id).get();
        if (order.getStatus() == Status.COMPLETED) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        order.setStatus(Status.COMPLETED);
        orderService.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private DetailDto convertToDto(Detail detail) {
        DetailDto detailDto = modelMapper.map(detail, DetailDto.class);
        return detailDto;
    }

}
