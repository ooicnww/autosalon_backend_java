package org.autosalon.domain.model.entities.order;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.util.UUID;

public class ExistedCarOrder extends Order{
    private Car car;

    public ExistedCarOrder(Client client, Manager manager, Car car){
        super(client, manager);
        this.car = car;
    }

    public ExistedCarOrder(UUID id, Client client, Manager manager, Car car, OrderStatus status){
        super(id, client, manager, status);
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void readyForSell(){
        if (this.getStatus() == OrderStatus.PAID){
            this.setStatus(OrderStatus.READY_FOR_SELL);
        }
    }
}