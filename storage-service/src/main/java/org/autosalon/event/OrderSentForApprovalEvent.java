package org.autosalon.event;

import java.util.UUID;

public record OrderSentForApprovalEvent(
        UUID orderId,
        UUID carId,
        UUID configurationId,
        String traceId
) {
}