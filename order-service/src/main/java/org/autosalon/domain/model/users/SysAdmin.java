package org.autosalon.domain.model.users;

import java.util.UUID;

public class SysAdmin extends User {

    public SysAdmin(String name, String email) {
        super(name, email);
    }

    public SysAdmin(UUID id, String name, String email) {
        super(id, name, email);
    }


}