package org.autosalon.domain.model.entities.order;

import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.util.UUID;

public class CustomOrder extends Order {

    private UUID configurationId;

    public CustomOrder(
            Client client,
            Manager manager,
            UUID configurationId
    ) {
        super(client, manager);
        this.configurationId = configurationId;
    }

    public CustomOrder(
            UUID id,
            Client client,
            Manager manager,
            UUID configurationId,
            OrderStatus status
    ) {
        super(id, client, manager, status);
        this.configurationId = configurationId;
    }

    public UUID getConfigurationId() {
        return configurationId;
    }

    public void waitForDelivery() {

        if (this.getStatus() == OrderStatus.PAID) {
            this.setStatus(OrderStatus.WAITING_FOR_DELIVERY);
        }
    }

    public void readyForSell() {

        if (this.getStatus() == OrderStatus.WAITING_FOR_DELIVERY) {
            this.setStatus(OrderStatus.READY_FOR_SELL);
        }
    }
}