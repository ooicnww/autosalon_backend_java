package org.autosalon.persistence.entityJpa.user;

import jakarta.persistence.*;
import org.autosalon.domain.model.users.UserType;
import org.autosalon.persistence.entityJpa.car.BaseJpa;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpa extends BaseJpa {

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}