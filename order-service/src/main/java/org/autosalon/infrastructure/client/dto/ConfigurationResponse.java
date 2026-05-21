package org.autosalon.infrastructure.client.dto;

import java.util.UUID;

public record ConfigurationResponse(
        UUID id,
        int totalPrice
) {
}