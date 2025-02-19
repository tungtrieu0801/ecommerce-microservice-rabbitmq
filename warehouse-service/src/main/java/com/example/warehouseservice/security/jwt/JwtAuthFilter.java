package com.example.warehouseservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final CustomUserDetailsService userDetailsService;

    @Value("${permission.service}")
    private String permissionService;

    @Value("${keycloak.realm.access}")
    private String keyCloakRealmAccess;

    @Value("${keycloak.resource.access}")
    private String keycloakResourceAccess;

    @Value("${keycloak.realm.access.role}")
    private String keycloakRealmAccessRole;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.replace("Bearer ", "");

                String[] tokenParts = token.split("\\.");
                if (tokenParts.length == 3) {

                    String payload = new String(Base64.getDecoder().decode(tokenParts[1]));

                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

                    String username = (String) claims.get("preferred_username");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } else {
                    logger.warn("Invalid token format");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } catch (Exception e) {
                logger.error("Token validation error: {}", e.getMessage(), e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
