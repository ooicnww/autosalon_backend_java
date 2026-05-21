package org.autosalon.domain.model.entities.testDrive;

import org.autosalon.domain.model.users.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestRequest {

    private UUID id;
    private Client client;
    private UUID carId;
    private LocalDateTime dateTime;

    public TestRequest(
            Client client,
            UUID carId,
            LocalDateTime dateTime
    ) {
        this.id = UUID.randomUUID();
        this.client = client;
        this.carId = carId;
        this.dateTime = dateTime;
    }

    public TestRequest(
            UUID id,
            Client client,
            UUID carId,
            LocalDateTime dateTime
    ) {
        this.id = id;
        this.client = client;
        this.carId = carId;
        this.dateTime = dateTime;
    }

    public UUID getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public UUID getCarId() {
        return carId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}