package com.fiapx.usermanagement.core.domain.services.FindUserByIdUseCase;

import com.fiapx.usermanagement.core.domain.entities.User;

public interface IFindUserByIdUseCase {
    User execute(String id);
}
