package com.example.userservice.security.jwt;

import com.example.userservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserInfoDetail implements UserDetails {

    private final String id;
    private final String userName;
    private final String password;
    private final GrantedAuthority authority;

    public UserInfoDetail(User user) {
        this.id = String.valueOf(user.getId());
        this.userName = user.getUsername();
        this.password = user.getPassword();
        if ("ROLE_ADMIN".equals(user.getRole())) {
            this.authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else {
            this.authority = new SimpleGrantedAuthority("ROLE_USER");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getUserId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
