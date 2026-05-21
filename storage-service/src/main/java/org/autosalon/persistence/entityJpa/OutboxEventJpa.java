package org.autosalon.persistence.entityJpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
public class OutboxEventJpa {

    @Id
    private UUID id;


    @Column(nullable = false)
    private String topic;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private boolean processed;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public OutboxEventJpa() {}

    public OutboxEventJpa(UUID id, String topic, String payload) {
        this.id = id;
        this.topic = topic;
        this.payload = payload;
        this.processed = false;
        this.createdAt = Instant.now();
    }


    public UUID getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }


}
