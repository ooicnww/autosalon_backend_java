package org.autosalon.presentation.dto;

import org.autosalon.domain.model.entities.car.CarComponentType;

import java.util.Set;
import java.util.UUID;

public record CarComponentDto(
        UUID id,
        CarComponentType type,
        String name,
        int price,
        Set<UUID> suitableModels
) {}