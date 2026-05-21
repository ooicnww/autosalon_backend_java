package org.autosalon.domain.build;

import java.time.Instant;
import java.util.UUID;

public class BuildOrder {

    private UUID id;
    private UUID sourceOrderId;
    private Instant createdAt;
    private Instant updatedAt;
    private BuildOrderStatus status;
    private boolean removed;

    public BuildOrder(
            UUID id,
            UUID sourceOrderId,
            Instant createdAt,
            Instant updatedAt,
            BuildOrderStatus status,
            boolean removed
    ) {
        this.id = id;
        this.sourceOrderId = sourceOrderId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.removed = removed;
    }


    public UUID getId() {
        return id;
    }

    public UUID getSourceOrderId() {
        return sourceOrderId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public BuildOrderStatus getStatus() {
        return status;
    }

    public boolean isRemoved() {
        return removed;
    }
}
