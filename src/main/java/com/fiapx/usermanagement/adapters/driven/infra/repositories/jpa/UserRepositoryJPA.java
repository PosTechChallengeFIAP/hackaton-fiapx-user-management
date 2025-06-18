package com.fiapx.usermanagement.adapters.driven.infra.repositories.jpa;

import com.fiapx.usermanagement.core.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM fiap_user WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
