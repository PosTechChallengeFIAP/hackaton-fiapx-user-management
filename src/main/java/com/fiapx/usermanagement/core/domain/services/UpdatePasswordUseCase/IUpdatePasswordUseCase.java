package com.fiapx.usermanagement.core.domain.services.UpdatePasswordUseCase;

import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.model.UserRequest;

public interface IUpdatePasswordUseCase {
    void execute(String newPassword, String username);
}
