package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.users.*;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {

    public UserJpa toJpa(User user) {
        UserJpa jpa = new UserJpa();

        jpa.setId(user.getId());
        jpa.setName(user.getName());
        jpa.setEmail(user.getEmail());

        if (user instanceof Client) {
            jpa.setType(UserType.CLIENT);
        } else if (user instanceof Manager) {
            jpa.setType(UserType.MANAGER);
        } else if (user instanceof SysAdmin) {
            jpa.setType(UserType.SYS_ADMIN);
        } else if (user instanceof WareAdmin) {
            jpa.setType(UserType.WARE_ADMIN);
        }

        jpa.setRemoved(false);

        return jpa;
    }

    public User toDomain(UserJpa jpa) {
        return switch (jpa.getType()) {
            case CLIENT -> new Client(jpa.getId(), jpa.getName(), jpa.getEmail());
            case MANAGER -> new Manager(jpa.getId(), jpa.getName(), jpa.getEmail());
            case SYS_ADMIN -> new SysAdmin(jpa.getId(), jpa.getName(), jpa.getEmail());
            case WARE_ADMIN -> new WareAdmin(jpa.getId(), jpa.getName(), jpa.getEmail());
        };
    }
}