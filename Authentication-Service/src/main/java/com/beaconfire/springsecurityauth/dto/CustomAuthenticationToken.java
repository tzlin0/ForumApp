package com.beaconfire.springsecurityauth.dto;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private Long userId;
    private boolean emailVerified;


    public CustomAuthenticationToken(Object principal, Object credentials, Long userId, boolean emailVerified) {
        super(principal, credentials);
        this.userId = userId;
        this.emailVerified = emailVerified;
    }

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }
}
