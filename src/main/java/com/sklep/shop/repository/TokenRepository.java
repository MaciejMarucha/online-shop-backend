package com.sklep.shop.repository;

import com.sklep.shop.model.Token;
import com.sklep.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByPin(String pin);
    Boolean existsByPin(String token);
    Boolean existsByUser(User user);


}
