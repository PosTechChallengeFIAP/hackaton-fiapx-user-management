package com.fiapx.usermanagement.core.domain.services.CreateUserUseCase;

import com.fiapx.usermanagement.core.application.auth.utils.PasswordEncoder;
import com.fiapx.usermanagement.core.application.exceptions.ResourceNotFoundException;
import com.fiapx.usermanagement.core.application.exceptions.UsernameAlreadyInUseException;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.model.UserRequest;
import com.fiapx.usermanagement.core.domain.repositories.IUserRepository;
import com.fiapx.usermanagement.core.domain.services.FindUserByUsernameUseCase.IFindUserByUsernameUseCase;
import com.fiapx.usermanagement.core.domain.services.ValidatePasswordUseCase.IValidatePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IValidatePasswordUseCase validatePasswordUseCase;

    @Autowired
    private IFindUserByUsernameUseCase findUserByUsernameUseCase;

    public User execute(UserRequest newUser){
        try {
            findUserByUsernameUseCase.execute(newUser.getUsername());
            throw new UsernameAlreadyInUseException(newUser.getUsername());

        }catch (ResourceNotFoundException ex){
            validatePasswordUseCase.execute(newUser.getPassword());

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            User user = new User();
            user.setActive(true);
            user.setUsername(newUser.getUsername());
            user.setCreatedAt(now);
            user.setLastPasswordUpdate(now);
            user.setEncryptedPassword(PasswordEncoder.hashPassword(newUser.getPassword()));

            return userRepository.save(user);
        }
    }
}
