package org.autosalon.domain.model.entities.order;

import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.util.UUID;

public class CustomOrder extends Order{
    private CarConfiguration configuration;

    public CustomOrder(Client client, Manager manager, CarConfiguration configuration) {
        super(client, manager);
        this.configuration = configuration;
    }

    public CustomOrder(UUID id, Client client, Manager manager, CarConfiguration configuration, OrderStatus status) {
        super(id, client, manager, status);
        this.configuration = configuration;
    }

    public CarConfiguration getConfiguration() {
        return configuration;
    }

    public void waitForDelivery(){
        if (this.getStatus() == OrderStatus.PAID){
            this.setStatus(OrderStatus.WAITING_FOR_DELIVERY);
        }
    }
    public void readyForSell(){
        if (this.getStatus() == OrderStatus.WAITING_FOR_DELIVERY){
            this.setStatus(OrderStatus.READY_FOR_SELL);
        }
    }

}