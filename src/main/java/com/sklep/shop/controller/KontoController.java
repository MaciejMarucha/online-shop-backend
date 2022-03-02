package com.sklep.shop.controller;

import com.sklep.shop.model.*;
import com.sklep.shop.service.AddToCartService;
import com.sklep.shop.service.PhotoService;
import com.sklep.shop.service.ProductService;
import com.sklep.shop.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/account")
public class KontoController {

    private final TokenService tokenService;
    private final AddToCartService addToCartService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final PhotoService photoService;

    public KontoController(TokenService tokenService, AddToCartService addToCartService, ProductService productService, ModelMapper modelMapper, PhotoService photoService) {
        this.tokenService = tokenService;
        this.addToCartService = addToCartService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.photoService = photoService;
    }

    @GetMapping("/getproducts")
    public ResponseEntity<?> getProducts(@RequestHeader("pin") String pin){
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }

        List<Product> productList = productService.findAll();
        List<ProductDto> productDtoList = productList.stream().map(this::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/photos")
    public ResponseEntity<?> getPhotos(@RequestHeader("pin") String pin){
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        List<Photo> photoList = photoService.findAll();

        return new ResponseEntity<>(photoList, HttpStatus.OK);
    }

    @PostMapping
    public boolean logout(@RequestBody Token tokenResponseReq) {
        if (tokenService.logout(tokenResponseReq)) {
            addToCartService.deleteByUser(tokenResponseReq.getUser());
            return true;
        }
        return false;
    }

    @PostMapping("/addtocart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCart nowyaddToCart) {

        int amountInWareHouse = productService.findById(nowyaddToCart.getProduct().getId()).get().getAmountInWareHouse();
        if (nowyaddToCart.getAmount() > amountInWareHouse) {
            return new ResponseEntity<>("There are not so many products in warehouse!", HttpStatus.BAD_REQUEST);
        }

        if (addToCartService.findByProductName(nowyaddToCart.getProduct().getName()).isEmpty()) {
            addToCartService.save(nowyaddToCart);
        } else {
            AddToCart inDb = addToCartService.findByProductName(nowyaddToCart.getProduct().getName()).get();
            inDb.setAmount(inDb.getAmount() + nowyaddToCart.getAmount());
            addToCartService.save(inDb);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserId(@RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        User byPinUser = byPin.getUser();

        Response response = new Response();
        response.setUser(byPinUser);

        if(byPinUser.getUserRole() == UserRole.ADMIN){
            response.setRole("ADMIN");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setRole("USER");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

}
