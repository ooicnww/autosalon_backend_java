package org.autosalon.domain.model.entities.order;

public enum OrderStatus{
    CREATED,
    APPROVED_BY_MANAGER,
    WAITING_FOR_PAYMENT,
    WAITING_FOR_DELIVERY,
    PAID,
    READY_FOR_SELL,
    COMPLETED,
    CANDELED
}