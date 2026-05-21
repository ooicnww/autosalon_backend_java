package org.autosalon.infrastructure.kafka;

import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.event.OrderApprovedEvent;
import org.autosalon.event.OrderRejectedEvent;
import org.autosalon.domain.repositories.IOrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderResultListener {

    private final IOrderRepository repository;

    public OrderResultListener(IOrderRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "order-approved",
            groupId = "order-group"
    )
    public void approved(OrderApprovedEvent event) {

        Order order = repository.findById(event.orderId()).orElseThrow();

        if (order instanceof ExistedCarOrder existed) {
            existed.readyForSell();
        }

        if (order instanceof CustomOrder custom) {
            custom.waitForDelivery();
        }

        repository.save(order);
    }

    @KafkaListener(
            topics = "order-rejected",
            groupId = "order-group"
    )
    public void rejected(OrderRejectedEvent event) {
        Order order = repository.findById(event.orderId()).orElseThrow();
        order.cancel();
        repository.save(order);
    }
}