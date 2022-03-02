package com.sklep.shop;

import com.sklep.shop.model.Photo;
import com.sklep.shop.model.Product;
import com.sklep.shop.model.User;
import com.sklep.shop.model.UserRole;
import com.sklep.shop.repository.ProductRepository;
import com.sklep.shop.repository.UserRepository;
import com.sklep.shop.service.PhotoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Paths;


@SpringBootApplication
public class ShopApplication {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {

            Photo photo1 = new Photo("Chleb");
            Photo photo2 = new Photo("Masło");
            Photo photo3 = new Photo("Jaja");
            Photo photo4 = new Photo("Mleko");
            Photo photo5 = new Photo("Mąka");
            Photo photo6 = new Photo("Kasza");

            photo1.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a1.jpg").toURI())));
            photo2.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a2.jpg").toURI())));
            photo3.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a3.jpg").toURI())));
            photo4.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a4.jpg").toURI())));
            photo5.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a5.jpg").toURI())));
            photo6.setFile(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("a6.jpg").toURI())));

            photoService.save(photo1);
            photoService.save(photo2);
            photoService.save(photo3);
            photoService.save(photo4);
            photoService.save(photo5);
            photoService.save(photo6);

            Product product1 = new Product(1L, "Chleb", 100, 3.00, photo1);
            Product product2 = new Product(2L, "Masło", 100, 4.99, photo2);
            Product product3 = new Product(3L, "Mleko", 300, 2.19, photo3);
            Product product4 = new Product(4L, "Jaja", 400, 5.99, photo4);
            Product product5 = new Product(5L, "Mąka", 100, 1.99, photo5);
            Product product6 = new Product(6L, "Kasza", 100, 2.50, photo6);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);

            User user1 = new User(1L, "user@wp.pl", "user", "user", "user", UserRole.USER);
            User user2 = new User(2L, "admin@wp.pl", "admin", "admin", "admin", UserRole.ADMIN);

            userRepository.save(user1);
            userRepository.save(user2);
        };
    }
}
