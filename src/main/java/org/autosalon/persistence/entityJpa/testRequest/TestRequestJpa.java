package org.autosalon.persistence.entityJpa.testRequest;

import jakarta.persistence.*;
import org.autosalon.persistence.entityJpa.car.BaseJpa;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "test_requests")
public class TestRequestJpa extends BaseJpa {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserJpa client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarJpa car;

    private LocalDateTime dateTime;


    public CarJpa getCar() {
        return car;
    }

    public void setCar(CarJpa car) {
        this.car = car;
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