package org.autosalon.presentation.dto;

import org.autosalon.domain.model.users.UserType;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        UserType type
) {}