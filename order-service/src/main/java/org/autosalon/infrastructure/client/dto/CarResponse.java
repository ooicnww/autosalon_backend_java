package org.autosalon.infrastructure.client.dto;

import java.util.UUID;

public record CarResponse(
        UUID id,
        boolean available
) {
}