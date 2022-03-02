package com.sklep.shop.controller;

import com.sklep.shop.model.*;
import com.sklep.shop.service.TokenService;
import com.sklep.shop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private ModelMapper modelMapper;

    public UserController(UserService userService, TokenService tokenService, ModelMapper modelMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userService.existByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("The user already exists!");
        }
        userService.save(user);
        return ResponseEntity.ok("Added user!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        if (!userService.existsByEmailAndPassword(userRequest)) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.getUser(userRequest.getEmail(), userRequest.getPassword());


        if (tokenService.existsByUser(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logged!");
        }
        Token token = tokenService.gernerateNewToken();

        token.setUser(user);
        tokenService.save(token);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/account/users")
    public ResponseEntity<?> getUsers(@RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        List<User> userList = userService.findByUserRole(UserRole.USER);
        List<UserDto> collect = userList.stream().map(this::convertToUserDto).collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    public UserDto convertToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
