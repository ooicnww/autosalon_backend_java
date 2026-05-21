package org.autosalon.presentation.dto;

import org.autosalon.domain.model.entities.car.CarComponentType;

import java.util.Map;
import java.util.UUID;

public record CarConfigurationResponseDto(

        UUID id,

        UUID modelId,

        Map<CarComponentType, CarComponentDto> components,

        int totalPrice

) {}