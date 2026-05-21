package org.autosalon.presentation.dto;

import org.autosalon.domain.model.users.UserType;

public record UserRequestDto(
        String name,
        String email,
        UserType type
) {}