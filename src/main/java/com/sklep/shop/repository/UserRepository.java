package com.sklep.shop.repository;

import com.sklep.shop.model.UserRole;
import com.sklep.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    List<User> findByUserRole(UserRole userRole);
}
