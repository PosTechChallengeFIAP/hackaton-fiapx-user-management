package com.fiapx.usermanagement.core.domain.services.FindUserByUsernameUseCase;

import com.fiapx.usermanagement.core.domain.entities.User;

public interface IFindUserByUsernameUseCase {
    User execute(String username);
}

