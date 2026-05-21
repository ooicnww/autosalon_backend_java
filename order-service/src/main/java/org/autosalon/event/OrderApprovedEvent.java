package org.autosalon.event;

import java.util.UUID;

public record OrderApprovedEvent(
        UUID orderId
) {
}