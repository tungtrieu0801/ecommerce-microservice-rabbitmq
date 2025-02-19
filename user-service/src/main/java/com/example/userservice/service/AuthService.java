package com.example.userservice.service;

import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> signIn(LoginRequest loginRequest);

    ResponseEntity<?> signUp(SignUpRequest signUpRequest);

}
