package com.fiapx.usermanagement.core.domain.services.CreateUserUseCase;

import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.model.UserRequest;

public interface ICreateUserUseCase {
    User execute(UserRequest newUser);
}
