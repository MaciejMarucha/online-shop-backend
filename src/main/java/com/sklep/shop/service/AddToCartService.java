package com.sklep.shop.service;

import com.sklep.shop.model.AddToCart;
import com.sklep.shop.model.User;
import com.sklep.shop.repository.AddToCartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AddToCartService {

    private final AddToCartRepository addToCartRepository;

    public AddToCartService(AddToCartRepository addToCartRepository) {
        this.addToCartRepository = addToCartRepository;
    }

    public void save(AddToCart addToCart) {
        addToCartRepository.save(addToCart);
    }

    public List<AddToCart> findByUserId(Long id) {
        return addToCartRepository.findByUserId(id);
    }

    @Transactional
    public void deleteByUser(User user) {
        addToCartRepository.deleteByUser(user);
    }

    public void deleteById(Long id) {
        addToCartRepository.deleteById(id);
    }

    public Optional<AddToCart> findById(Long id) {
        return addToCartRepository.findById(id);
    }

    public Optional<AddToCart> findByProductName(String name) {
        return addToCartRepository.findByProductName(name);
    }


}
