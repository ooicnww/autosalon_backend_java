package org.autosalon.domain.model.users;

import java.util.UUID;

public class Manager extends User {

    public Manager(String name, String email) {
        super(name, email);
    }

    public Manager(UUID id, String name, String email) {
        super(id, name, email);
    }

}