package com.fiapx.usermanagement.core.application.auth.service;

import com.fiapx.usermanagement.core.application.auth.config.AuthConfig;
import com.fiapx.usermanagement.core.application.auth.config.SecurityConfig;
import com.fiapx.usermanagement.core.application.auth.model.UserDetails;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.services.FindUserByUsernameUseCase.IFindUserByUsernameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService, IUserDetailsService {
    @Autowired
    private IFindUserByUsernameUseCase findUserByUsernameUseCase;

    @Autowired
    private AuthConfig authConfig;

    public UserDetails loadUserByUsername(String username) {
        User user = findUserByUsernameUseCase.execute(username);
        return UserDetails.fromUserEntity(user, authConfig.getPasswordExpireDays());
    }
}