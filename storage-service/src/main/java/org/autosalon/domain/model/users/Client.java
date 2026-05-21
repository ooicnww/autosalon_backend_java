package org.autosalon.domain.model.users;

import java.util.UUID;

public class Client extends User {

    public Client(String name, String email) {
        super(name, email);
    }

    public Client(UUID id, String name, String email) {
        super(id, name, email);
    }

}