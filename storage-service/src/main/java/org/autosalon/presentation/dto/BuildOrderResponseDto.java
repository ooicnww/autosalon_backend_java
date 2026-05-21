package org.autosalon.presentation.dto;

import org.autosalon.domain.build.BuildOrderStatus;

import java.time.Instant;
import java.util.UUID;

public record BuildOrderResponseDto(
        UUID id,
        UUID sourceOrderId,
        Instant createdAt,
        Instant updatedAt,
        BuildOrderStatus status,
        boolean removed
) {
}