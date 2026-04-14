package org.autosalon;

import org.autosalon.presentation.dto.TestRequestRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TestDriveControllerIT extends BaseIT {

    UUID clientId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    UUID carId = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Test
    void createTestDrive() {
        var dto = new TestRequestRequestDto(clientId, carId, LocalDateTime.now());

        var response = restTemplate.postForEntity("/test-drives", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getAllTestDrives() {
        var response = restTemplate.getForEntity("/test-drives", String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void failIfClientNotExists() {
        var dto = new TestRequestRequestDto(
                UUID.randomUUID(),
                carId,
                LocalDateTime.now()
        );

        var response = restTemplate.postForEntity("/test-drives", dto, String.class);

        assertTrue(response.getStatusCode().is5xxServerError());
    }
}