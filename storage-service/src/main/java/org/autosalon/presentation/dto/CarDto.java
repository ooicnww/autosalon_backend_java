package org.autosalon.presentation.dto;

import java.util.UUID;

public record CarDto(
        UUID id,
        UUID modelId,
        String color,
        boolean available,
        int price
) {}