package com.gamsung.backend.global.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserDetailsConfig implements UserDetails {
    private Long userId;
    private String userEmail;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsConfig(Long userId,String userEmail, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.authorities = authorities;
    }



    public Long getUserId() {
        return userId;
    }
    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
