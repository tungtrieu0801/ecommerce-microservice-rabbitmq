package com.example.userservice.service.Impl;

import com.example.userservice.constants.ErrorMessage;
import com.example.userservice.entity.User;
import com.example.userservice.payload.request.LoginRequest;
import com.example.userservice.payload.request.SignUpRequest;
import com.example.userservice.payload.response.LoginResponse;
import com.example.userservice.payload.response.SignUpResponse;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import com.example.userservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class {@link AuthServiceImpl} an implementation of AuthService.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepo, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<?> signIn(LoginRequest loginRequest) {

        User user = userRepo.findByUsername(loginRequest.getUsername()).orElse(null);

        assert user != null;
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String tokens = jwtUtils.generateToken(authentication);

                LoginResponse signInResponse = LoginResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .token(tokens)
                        .typeToken("Bear")
                        .avatar(user.getAvatar())
                        .build();
                return ResponseEntity.ok(signInResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessage.ERROR);
    }

    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {

        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("This username is already in use");
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("This email is already in use");
        }

        try {
            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .build();
            userRepo.save(user);

            return ResponseEntity.ok(SignUpResponse.builder()
                    .fullName(user.getUsername())
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername()).build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}