package com.fiapx.usermanagement.core.domain.services.UpdatePasswordUseCase;

import com.fiapx.usermanagement.core.application.auth.utils.PasswordEncoder;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.repositories.IUserRepository;
import com.fiapx.usermanagement.core.domain.services.FindUserByUsernameUseCase.IFindUserByUsernameUseCase;
import com.fiapx.usermanagement.core.domain.services.ValidatePasswordUseCase.IValidatePasswordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UpdatePasswordUseCase implements IUpdatePasswordUseCase {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IValidatePasswordUseCase validatePasswordUseCase;

    @Autowired
    private IFindUserByUsernameUseCase findUserByUsernameUseCase;

    public void execute(String newPassword, String username){
        User user = findUserByUsernameUseCase.execute(username);
        validatePasswordUseCase.execute(newPassword);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        user.setLastPasswordUpdate(now);
        user.setEncryptedPassword(PasswordEncoder.hashPassword(newPassword));

        userRepository.save(user);
    }
}
