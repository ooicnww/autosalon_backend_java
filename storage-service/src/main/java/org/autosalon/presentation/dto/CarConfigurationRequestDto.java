package org.autosalon.presentation.dto;

import org.autosalon.domain.model.entities.car.CarComponentType;

import java.util.Map;
import java.util.UUID;

public record CarConfigurationRequestDto(

        UUID modelId,

        Map<CarComponentType, UUID> components

) {}