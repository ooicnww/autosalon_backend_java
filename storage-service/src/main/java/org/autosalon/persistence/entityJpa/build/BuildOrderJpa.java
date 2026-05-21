package org.autosalon.persistence.entityJpa.build;

import jakarta.persistence.*;
import org.autosalon.domain.build.BuildOrderStatus;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "build_orders")
public class BuildOrderJpa {

    @Id
    private UUID id;

    private UUID sourceOrderId;

    private Instant createdAt;

    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private BuildOrderStatus status;

    private boolean removed;

    public BuildOrderJpa() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(UUID sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BuildOrderStatus getStatus() {
        return status;
    }

    public void setStatus(BuildOrderStatus status) {
        this.status = status;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}