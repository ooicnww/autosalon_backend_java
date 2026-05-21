package org.autosalon.domain.model.entities.order;

import org.autosalon.domain.exceptions.LockedActionException;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Order {
    private UUID id;
    private Client client;
    private Manager manager;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(Client client, Manager manager){
        this.id = UUID.randomUUID();
        this.client = client;
        this.manager = manager;
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public Order(UUID id, Client client, Manager manager, OrderStatus status){
        this.id = id;
        this.client = client;
        this.manager = manager;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


    public Client getClient() {
        return client;
    }

    public Manager getManager() {
        return manager;
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    protected void setStatus(OrderStatus status){
        this.status =  status;
    }

    public void approve(){
        if (status != OrderStatus.CREATED){
            throw new LockedActionException("Заказ не создан");
        }
        status = OrderStatus.APPROVED_BY_MANAGER;
    }
    public void waitForPayment(){
        if (status != OrderStatus.APPROVED_BY_MANAGER){
            throw new LockedActionException("Заказ не подтвержден");
        }
        status = OrderStatus.WAITING_FOR_PAYMENT;
    }
    public void cancel(){
        if (status != OrderStatus.WAITING_FOR_PAYMENT){
            throw new LockedActionException("Заказ нельзя отменить");
        }
        status = OrderStatus.CANDELED;
    }
    public void paid(){
        if (status != OrderStatus.WAITING_FOR_PAYMENT){
           throw new LockedActionException("Заказ нельзя оплатить");
        }
        status = OrderStatus.PAID;
    }
    public void complete(){
        if (this.getStatus() != OrderStatus.READY_FOR_SELL){
            throw new LockedActionException("Заказ нельзя закрыть");
        }
        this.setStatus(OrderStatus.COMPLETED);

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}