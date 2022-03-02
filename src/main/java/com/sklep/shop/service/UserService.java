package com.sklep.shop.service;

import com.sklep.shop.model.User;
import com.sklep.shop.model.UserRequest;
import com.sklep.shop.model.UserRole;
import com.sklep.shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getUser(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        User newUser;
        if (user.isPresent()) {
            newUser = user.get();
            return newUser;
        } else {
            return null;
        }
    }

    public boolean existsByEmailAndPassword(UserRequest userRequest) {
        return userRepository.existsByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByUserRole(UserRole userRole) {
        return userRepository.findByUserRole(userRole);
    }
}
