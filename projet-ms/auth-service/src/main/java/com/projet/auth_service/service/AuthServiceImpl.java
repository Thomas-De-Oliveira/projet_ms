package com.projet.auth_service.service;

import com.projet.auth_service.entity.Auth;
import com.projet.auth_service.repository.AuthRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String SECRET_KEY = "secretkzqkvugeuisofgijopsgehiopejiopgefrqhjsdfgkdsfgksguysguysdfgksdfjfsdqfsdhjey";

    public AuthServiceImpl(AuthRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(Auth user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String login(Auth user) {
        Auth foundUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return Jwts.builder()
                .setSubject(foundUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.)
                .compact();
    }
}
