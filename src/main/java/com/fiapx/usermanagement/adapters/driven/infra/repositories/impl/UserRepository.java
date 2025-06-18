package com.fiapx.usermanagement.adapters.driven.infra.repositories.impl;

import com.fiapx.usermanagement.adapters.driven.infra.repositories.jpa.UserRepositoryJPA;
import com.fiapx.usermanagement.core.domain.entities.User;
import com.fiapx.usermanagement.core.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    UserRepositoryJPA userRepositoryJPA;

    @Override
    public User save(User requestToSave) {
        return userRepositoryJPA.save(requestToSave);
    }

    @Override
    public List<User> findAll() {
        return userRepositoryJPA.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepositoryJPA.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryJPA.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepositoryJPA.delete(user);
    }
}
