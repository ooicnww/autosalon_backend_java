package org.autosalon;

import org.autosalon.domain.model.enums.OrderType;
import org.autosalon.presentation.dto.OrderRequestDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerIT extends BaseIT {

    UUID clientId = UUID.fromString("11111111-1111-1111-1111-111111111111");
    UUID managerId = UUID.fromString("99999999-9999-9999-9999-999999999999");
    UUID carId = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Test
    void createOrder() {
        var dto = new OrderRequestDto(
                clientId,
                managerId,
                carId,
                null,
                OrderType.EXISTED
        );

        var response = restTemplate.postForEntity("/orders/existed", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getOrders() {
        var response = restTemplate.getForEntity("/orders", String.class);

        assertEquals(200, response.getStatusCode().value());
    }
}