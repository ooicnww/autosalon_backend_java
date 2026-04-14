package org.autosalon;

import org.autosalon.presentation.dto.CarDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarControllerIT extends BaseIT {

    UUID modelId = UUID.fromString("33333333-3333-3333-3333-333333333333");

    @Test
    void createCar() {
        var dto = new CarDto(null, modelId, "black", true, 1000);

        var response = restTemplate.postForEntity("/cars", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getCars() {
        var response = restTemplate.getForEntity("/cars", String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}