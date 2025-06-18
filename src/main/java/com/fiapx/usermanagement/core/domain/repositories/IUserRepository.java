package com.fiapx.usermanagement.core.domain.repositories;

import com.fiapx.usermanagement.core.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    User save(User request);
    List<User> findAll();
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    void delete(User request);
}
