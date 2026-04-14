package org.autosalon;

import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarModelRequestDto;
import org.autosalon.presentation.dto.CarModelResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarComponentControllerIT extends BaseIT {

    @Test
    void createModelWithManualDefaults() {

        var modelDto = new CarModelRequestDto(
                "BMW",
                "320i",
                BodyType.SEDAN,
                FuelType.PETROL,
                TransmissionType.AUTOMATIC,
                DriveType.REAR,
                3000000,
                Map.of(),
                184,
                2000
        );

        var createModelResponse =
                restTemplate.postForEntity("/models", modelDto, String.class);

        assertTrue(createModelResponse.getStatusCode().is2xxSuccessful());

        var modelsResponse =
                restTemplate.getForEntity("/models", CarModelResponseDto[].class);

        UUID modelId = modelsResponse.getBody()[0].id();

        var componentDto = new CarComponentDto(
                null,
                CarComponentType.WHEEL,
                "M-Sport",
                100000,
                Set.of(modelId)
        );

        restTemplate.postForEntity("/components", componentDto, String.class);

        restTemplate.postForEntity(
                "/models/" + modelId + "/default-components",
                Map.of(
                        CarComponentType.WHEEL,
                        UUID.fromString("aaaaaaaa-0000-0000-0000-000000000001")
                ),
                String.class
        );
    }
    @Test
    void getComponents() {
        var response = restTemplate.getForEntity("/components", String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}