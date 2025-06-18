package com.fiapx.usermanagement.core.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "fiap_user")
public class User {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Timestamp lastPasswordUpdate;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
