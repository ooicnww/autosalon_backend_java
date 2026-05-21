package org.autosalon.persistence.entityJpa.order;

import jakarta.persistence.*;
import org.autosalon.domain.model.entities.order.OrderStatus;
import org.autosalon.domain.model.enums.OrderType;
import org.autosalon.persistence.entityJpa.BaseJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;

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

    @Column(name = "car_id")
    private UUID carId;

    @Column(name = "configuration_id")
    private UUID configurationId;

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

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public UUID getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(UUID configurationId) {
        this.configurationId = configurationId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}