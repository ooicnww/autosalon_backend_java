package org.autosalon.domain.model.entities.testDrive;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.order.OrderStatus;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class TestRequest{
    private UUID id;
    private Client client;
    private Car car;
    private LocalDateTime dateTime;

    public TestRequest(Client client, Car car, LocalDateTime dateTime) {
        this.id = UUID.randomUUID();
        this.client = client;
        this.car = car;
        this.dateTime = dateTime;
    }

    public TestRequest(UUID id, Client client, Car car, LocalDateTime dateTime) {
        this.id = id;
        this.client = client;
        this.car = car;
        this.dateTime = dateTime;
    }

    public UUID getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }


    public Car getCar() {
        return car;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}