package org.autosalon.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestRequestRequestDto(
        UUID clientId,
        UUID carId,
        LocalDateTime dateTime
) {}