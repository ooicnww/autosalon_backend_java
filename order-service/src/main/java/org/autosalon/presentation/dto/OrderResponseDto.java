package org.autosalon.presentation.dto;

import org.autosalon.domain.model.entities.order.OrderStatus;

import java.util.UUID;

public record OrderResponseDto(
        UUID id,
        UUID clientId,
        UUID managerId,
        UUID carId,
        UUID configurationId,
        OrderStatus status
) {}