package org.autosalon;

import org.autosalon.domain.model.enums.*;
import org.autosalon.presentation.dto.CarModelRequestDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarModelControllerIT extends BaseIT {

    @Test
    void createModel() {
        var dto = new CarModelRequestDto(
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

        var response = restTemplate.postForEntity("/models", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getModels() {
        var response = restTemplate.getForEntity("/models", String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}