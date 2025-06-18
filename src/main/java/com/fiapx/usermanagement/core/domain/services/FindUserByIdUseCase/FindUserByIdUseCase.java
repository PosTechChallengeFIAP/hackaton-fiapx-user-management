package com.fiapx.usermanagement.core.domain.services.FindUserByIdUseCase;

import com.fiapx.usermanagement.core.application.exceptions.ResourceNotFoundException;
import com.fiapx.usermanagement.core.application.exceptions.ValidationException;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindUserByIdUseCase implements IFindUserByIdUseCase {
    @Autowired
    private IUserRepository userRepository;

    public User execute(String id) {
        try {
            UUID uuid = UUID.fromString(id);

            return userRepository.findById(uuid)
                    .orElseThrow(() -> new ResourceNotFoundException(User.class));
        }catch (IllegalArgumentException ex){
            throw new ValidationException("Invalid ID. Cannot find resource.", ex);
        }
    }
}
