package com.fiapx.usermanagement.core.domain.services.FindUserByUsernameUseCase;

import com.fiapx.usermanagement.core.application.exceptions.ResourceNotFoundException;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindUserByUsernameUseCase implements IFindUserByUsernameUseCase {
    @Autowired
    private IUserRepository userRepository;

    public User execute(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(User.class, String.format("Username: %s", username))
        );
    }
}
