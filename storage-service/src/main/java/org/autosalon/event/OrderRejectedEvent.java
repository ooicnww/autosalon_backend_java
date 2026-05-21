package org.autosalon.event;

import java.util.UUID;

public record OrderRejectedEvent(
        UUID orderId
) {
}