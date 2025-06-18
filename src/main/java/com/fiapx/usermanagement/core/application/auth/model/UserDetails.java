package com.fiapx.usermanagement.core.application.auth.model;

import com.fiapx.usermanagement.core.domain.entities.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    private boolean active;
    private boolean expired;
    private List<SimpleGrantedAuthority> authorities;

    private UserDetails(){}

    public static UserDetails fromUserEntity(User user, int daysToExpire){
        UserDetails userDetails = new UserDetails();
        userDetails.username = user.getUsername();
        userDetails.password = user.getEncryptedPassword();
        userDetails.active = user.isActive();

        LocalDateTime expireIn = user.getLastPasswordUpdate().toLocalDateTime().plusDays(daysToExpire);

        userDetails.expired = expireIn.isBefore(LocalDateTime.now());

        userDetails.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return userDetails;
    }

    @Override
    public boolean isAccountNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
