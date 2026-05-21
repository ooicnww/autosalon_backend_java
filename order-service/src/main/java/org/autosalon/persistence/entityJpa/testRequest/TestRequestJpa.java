package org.autosalon.persistence.entityJpa.testRequest;

import jakarta.persistence.*;
import org.autosalon.persistence.entityJpa.BaseJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "test_requests")
public class TestRequestJpa extends BaseJpa {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserJpa client;

    @Column(name = "car_id")
    private UUID carId;

    private LocalDateTime dateTime;

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserJpa getClient() {
        return client;
    }

    public void setClient(UserJpa client) {
        this.client = client;
    }
}