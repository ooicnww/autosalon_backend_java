package org.autosalon.presentation.dto;

import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;

import java.util.Map;
import java.util.UUID;

public record CarModelResponseDto(
        UUID id,
        String brand,
        String modelName,
        BodyType bodyType,
        FuelType fuelType,
        TransmissionType transmissionType,
        DriveType driveType,
        int basePrice,
        Map<CarComponentType, CarComponentDto> defaultComponents,
        int enginePower,
        int engineCapacity
) {}