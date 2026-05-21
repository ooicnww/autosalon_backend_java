package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.users.*;
import org.autosalon.presentation.dto.UserRequestDto;
import org.autosalon.presentation.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDtoMapper {

    public User toDomain(UserRequestDto dto) {
        return switch (dto.type()) {
            case CLIENT -> new Client(dto.name(), dto.email());
            case MANAGER -> new Manager(dto.name(), dto.email());
            case SYS_ADMIN -> new SysAdmin(dto.name(), dto.email());
            case WARE_ADMIN -> new WareAdmin(dto.name(), dto.email());
        };
    }

    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user instanceof Client ? UserType.CLIENT :
                        user instanceof Manager ? UserType.MANAGER :
                        user instanceof SysAdmin ? UserType.SYS_ADMIN :
                        UserType.WARE_ADMIN
        );
    }
}