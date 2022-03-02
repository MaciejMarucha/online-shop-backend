package com.sklep.shop.repository;

import com.sklep.shop.model.AddToCart;
import com.sklep.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddToCartRepository extends JpaRepository<AddToCart, Long> {
    List<AddToCart> findByUserId(Long id);

    void deleteByUser(User user);

    Optional<AddToCart> findByProductName(String name);
}
