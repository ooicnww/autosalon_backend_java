package org.autosalon.mapper.mapperDto;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.users.User;
import org.autosalon.domain.model.users.UserType;
import org.autosalon.presentation.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-03T00:42:28+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class UserResponseDtoMapperImpl implements UserResponseDtoMapper {

    @Override
    public UserResponseDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserType type = mapType(user);

        UserResponseDto userResponseDto = new UserResponseDto( id, name, email, type );

        return userResponseDto;
    }
}
