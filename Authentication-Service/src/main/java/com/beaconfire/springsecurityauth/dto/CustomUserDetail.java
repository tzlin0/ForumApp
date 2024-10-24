package com.beaconfire.springsecurityauth.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class CustomUserDetail implements UserDetails {

    private Long userId;
    private String email;
    private String username;
    private String password;
    private boolean emailVerified;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // replaced accountNonLocked with true


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetailBuilder builder() {
        return new CustomUserDetailBuilder();
    }

    public static class CustomUserDetailBuilder {
        private Long userId;
        private String password;
        private String email;
        private String username;
        private boolean emailVerified;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;

        CustomUserDetailBuilder() {
        }

        public CustomUserDetailBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public CustomUserDetailBuilder username(String username) {
            this.username = username;
            return this;
        }

        public CustomUserDetailBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomUserDetailBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CustomUserDetailBuilder emailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
            return this;
        }

        public CustomUserDetailBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserDetailBuilder accountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public CustomUserDetailBuilder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public CustomUserDetailBuilder credentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public CustomUserDetailBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUserDetail build() {
            return new CustomUserDetail(userId,email,  password, emailVerified, authorities, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
        }

        public String toString() {
            return "CustomUserDetail.CustomUserDetailBuilder(userId=" + this.userId + ", password=" + this.password + ", emailVerified=" + this.emailVerified + ", authorities=" + this.authorities + ", accountNonExpired=" + this.accountNonExpired + ", accountNonLocked=" + this.accountNonLocked + ", credentialsNonExpired=" + this.credentialsNonExpired + ", enabled=" + this.enabled + ")";
        }
    }

    private CustomUserDetail(Long userId, String email, String password, boolean emailVerified, Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.email = email;
        this.username = email;
        this.password = password;
        this.emailVerified = emailVerified;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
