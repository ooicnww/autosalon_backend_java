package org.autosalon.presentation.dto;

import org.autosalon.domain.model.enums.OrderType;

import java.util.UUID;

public record OrderRequestDto(
        UUID clientId,
        UUID managerId,
        UUID carId,
        UUID configurationId,
        OrderType type
) {}