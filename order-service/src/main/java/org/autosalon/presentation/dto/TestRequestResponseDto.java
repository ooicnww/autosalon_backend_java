package org.autosalon.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestRequestResponseDto(
        UUID id,
        UUID clientId,
        UUID carId,
        LocalDateTime dateTime
) {}