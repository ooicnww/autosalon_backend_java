package org.autosalon.domain.model.entities.order;

import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.util.UUID;

public class ExistedCarOrder extends Order {

    private UUID carId;

    public ExistedCarOrder(
            Client client,
            Manager manager,
            UUID carId
    ) {
        super(client, manager);
        this.carId = carId;
    }

    public ExistedCarOrder(
            UUID id,
            Client client,
            Manager manager,
            UUID carId,
            OrderStatus status
    ) {
        super(id, client, manager, status);
        this.carId = carId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void readyForSell() {

        if (this.getStatus() == OrderStatus.PAID) {
            this.setStatus(OrderStatus.READY_FOR_SELL);
        }
    }
}