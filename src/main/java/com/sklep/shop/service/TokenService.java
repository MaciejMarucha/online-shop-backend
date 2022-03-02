package com.sklep.shop.service;

import com.sklep.shop.model.Token;
import com.sklep.shop.model.User;
import com.sklep.shop.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void save(Token token) {
        tokenRepository.save(token);
    }

    public boolean logout(Token token) {
        if (!tokenRepository.existsByPin(token.getPin())) {
            return false;
        }
        Long id = tokenRepository.findByPin(token.getPin()).get().getId();
        tokenRepository.deleteById(id);
        return true;
    }

    public Token gernerateNewToken() {
        Token token = new Token();
        Random random = new Random();
        long liczba = random.nextLong();
        char znak = (char) (random.nextInt(26) + 'a');
        String pin = Long.toString(liczba) + znak;
        token.setPin(pin);
        return token;
    }

    public boolean exist(String token) {
        return tokenRepository.existsByPin(token);
    }

    public Token findByPin(String pin) {
        Optional<Token> byPin = tokenRepository.findByPin(pin);
        if (byPin.isPresent()) {
            return byPin.get();
        } else return null;
    }

    public Boolean existsByUser(User user) {
        return tokenRepository.existsByUser(user);
    }
}
