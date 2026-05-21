package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.users.*;
import org.autosalon.presentation.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResponseDtoMapper {

    @Mapping(target = "type", expression = "java(mapType(user))")
    UserResponseDto toDto(User user);

    default UserType mapType(User user) {
        if (user instanceof Client) return UserType.CLIENT;
        if (user instanceof Manager) return UserType.MANAGER;
        if (user instanceof SysAdmin) return UserType.SYS_ADMIN;
        return UserType.WARE_ADMIN;
    }
}