package com.sklep.shop.controller;

import com.sklep.shop.model.*;
import com.sklep.shop.service.PhotoService;
import com.sklep.shop.service.ProductService;
import com.sklep.shop.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/account/products")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {
    private final ProductService productService;
    private final TokenService tokenService;
    private final PhotoService photoService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, TokenService tokenService, PhotoService photoService, ModelMapper modelMapper) {
        this.productService = productService;
        this.tokenService = tokenService;
        this.photoService = photoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getProducts(@RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        List<Product> productList = productService.findAll();
//        List<ProductDto> productDtoList = productList.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody Product product, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        //sprawdz czy produkt jest w bd
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody ProductDto productDto, @RequestHeader("pin") String pin) {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }
        System.out.println();
        System.out.println(productDto.toString());
        System.out.println();
        Product product = convertToEntity(productDto);

        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(@RequestParam(value = "file") MultipartFile file, @RequestHeader("pin") String pin) throws IOException {
        if (!tokenService.exist(pin)) {
            return ResponseEntity.notFound().build();
        }
        Token byPin = tokenService.findByPin(pin);
        UserRole userRole = byPin.getUser().getUserRole();
        if (userRole == UserRole.USER) {
            return ResponseEntity.notFound().build();
        }

        photoService.save(file);
        Photo photo = photoService.findByName(file.getOriginalFilename()).get();

        return new ResponseEntity<>(photo.getId(), HttpStatus.CREATED);
    }

    public Product convertToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return product;
    }


}
