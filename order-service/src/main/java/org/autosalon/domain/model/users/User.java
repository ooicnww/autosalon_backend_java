package org.autosalon.domain.model.users;

import java.util.UUID;

public abstract class User{
    private UUID id;
    private String name;
    private String email;

    public User(String name, String email){
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
    }
    public User(UUID id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}