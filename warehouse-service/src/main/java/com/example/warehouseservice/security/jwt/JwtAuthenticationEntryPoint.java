package com.example.warehouseservice.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String timeStamp = LocalDateTime.now().toString();
        String path = request.getServletPath();

        String jsonResponseBuilder = "{" +
                "\"timestamp\": \"" + timeStamp + "\", " +
                "\"status\": 401, " +
                "\"error\": \"Unauthorized\", " +
                "\"message\": \"Unauthorized\", " +
                "\"path\": \"" + path + "\"" +
                "}";

        byte[] responseToSend = jsonResponseBuilder.getBytes();
        response.setHeader("Content-Type", "application/json");
        response.setStatus(401);
        response.getOutputStream().write(responseToSend);
    }
}