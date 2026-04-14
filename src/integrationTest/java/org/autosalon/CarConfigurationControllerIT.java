package org.autosalon;

import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.presentation.dto.CarConfigurationRequestDto;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarConfigurationControllerIT extends BaseIT {

    UUID modelId = UUID.fromString("33333333-3333-3333-3333-333333333333");

    @Test
    void createConfiguration() {
        UUID modelId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        var dto = new CarConfigurationRequestDto(
                modelId,
                Map.of(
                        CarComponentType.WHEEL, UUID.fromString("aaaaaaaa-0000-0000-0000-000000000001"),
                        CarComponentType.TRANSMISSION, UUID.fromString("aaaaaaaa-0000-0000-0000-000000000002"),
                        CarComponentType.STEERING, UUID.fromString("aaaaaaaa-0000-0000-0000-000000000003"),
                        CarComponentType.INTERIOR, UUID.fromString("aaaaaaaa-0000-0000-0000-000000000004")
                )
        );

        var response = restTemplate.postForEntity("/configurations", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}