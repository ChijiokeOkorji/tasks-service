package com.example.tasks.domain.auth.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication authenticate(String token);
}
