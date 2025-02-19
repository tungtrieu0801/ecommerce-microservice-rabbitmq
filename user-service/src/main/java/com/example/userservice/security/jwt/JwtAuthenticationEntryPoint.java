package com.example.userservice.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@link JwtAuthenticationEntryPoint} is a custom implementation of the {@link AuthenticationEntryPoint} interface.
 * This class handles authentication errors by responding with a 401 Unauthorized status and a structured JSON error message.
 * <p>
 * It is typically used in JWT-based authentication systems where a user attempts to access a protected resource without a valid token.
 * <p>
 * The JSON response includes details about the error:
 * - timestamp: The time when the error occurred.
 * - status: The HTTP status code (401 Unauthorized).
 * - error: The error description (Unauthorized).
 * - message: A brief message describing the error (Unauthorized).
 * - path: The path of the resource the user attempted to access.
 *
 * @see HttpServletRequest
 * @see HttpServletResponse
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**
     * Handles the HTTP response when authentication fails.
     * <p>
     * This method is invoked when a user attempts to access a protected resource without proper authentication
     * (e.g., a missing or invalid JWT token). It sends a JSON response with a 401 Unauthorized status and additional error details.
     *
     * @param request       The {@link HttpServletRequest} that triggered the exception. It is used to extract the requested path.
     * @param response      The {@link HttpServletResponse} to send the error response with a 401 status.
     * @param authException The {@link AuthenticationException} thrown when authentication fails. It contains details about the error.
     * @throws IOException  If an error occurs while writing the response body to the output stream.
     * @throws ServletException If an error occurs during servlet processing.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
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
