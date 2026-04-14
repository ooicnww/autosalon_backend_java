package org.autosalon.domain.model.users;

import java.util.UUID;

public class WareAdmin extends User {

    public WareAdmin(String name, String email) {
        super(name, email);
    }

    public WareAdmin(UUID id, String name, String email) {
        super(id, name, email);
    }


}