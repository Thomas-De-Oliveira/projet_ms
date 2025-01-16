package com.projet.auth_service.service;

import com.projet.auth_service.entity.Auth;

public interface AuthService {
    void register(Auth user);

    String login(Auth user);
}