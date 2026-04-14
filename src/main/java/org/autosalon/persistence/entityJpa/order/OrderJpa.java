package org.autosalon.persistence.entityJpa.order;

import jakarta.persistence.*;
import org.autosalon.domain.model.entities.order.OrderStatus;
import org.autosalon.domain.model.enums.OrderType;
import org.autosalon.persistence.entityJpa.car.BaseJpa;
import org.autosalon.persistence.entityJpa.car.CarConfigurationJpa;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpa extends BaseJpa {

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserJpa client;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private UserJpa manager;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarJpa car;

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    private CarConfigurationJpa configuration;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public UserJpa getClient() {
        return client;
    }

    public void setClient(UserJpa client) {
        this.client = client;
    }

    public UserJpa getManager() {
        return manager;
    }

    public void setManager(UserJpa manager) {
        this.manager = manager;
    }

    public CarConfigurationJpa getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CarConfigurationJpa configuration) {
        this.configuration = configuration;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public CarJpa getCar() {
        return car;
    }

    public void setCar(CarJpa car) {
        this.car = car;
    }

}