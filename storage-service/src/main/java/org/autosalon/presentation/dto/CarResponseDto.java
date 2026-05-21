package org.autosalon.presentation.dto;

import java.util.UUID;

public record CarResponseDto(
        UUID id,
        boolean available
) {
}