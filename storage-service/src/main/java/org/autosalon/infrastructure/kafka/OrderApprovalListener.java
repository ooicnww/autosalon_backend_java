package org.autosalon.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.event.OrderApprovedEvent;
import org.autosalon.event.OrderRejectedEvent;
import org.autosalon.event.OrderSentForApprovalEvent;
import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.autosalon.persistence.entityJpa.build.BuildOrderJpa;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.repository.BuildOrderJpaRepository;
import org.autosalon.persistence.repository.CarJpaRepository;
import org.autosalon.persistence.repository.OutboxJpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.autosalon.domain.build.BuildOrderStatus.*;

@Component
public class OrderApprovalListener {

    private final CarJpaRepository carRepository;
    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final BuildOrderJpaRepository buildOrderJpaRepository;

    public OrderApprovalListener(CarJpaRepository carRepository, OutboxJpaRepository outboxRepository, ObjectMapper objectMapper, BuildOrderJpaRepository buildOrderJpaRepository) {
        this.carRepository = carRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.buildOrderJpaRepository = buildOrderJpaRepository;
    }

    @KafkaListener(
            topics = "order-sent-for-approval",
            groupId = "storage-group"
    )
    public void listen(OrderSentForApprovalEvent event) {
        System.out.println("EVENT RECEIVED: " + event);

        if (buildOrderJpaRepository.existsBySourceOrderId(event.orderId())) {
            System.out.println("EVENT ALREADY PROCESSED");
             return;
        }

        BuildOrderJpa buildOrder = new BuildOrderJpa();

        buildOrder.setId(UUID.randomUUID());
        buildOrder.setSourceOrderId(event.orderId());
        buildOrder.setCreatedAt(Instant.now());
        buildOrder.setUpdatedAt(Instant.now());
        buildOrder.setRemoved(false);

        buildOrder.setStatus(CREATED);
        buildOrder.setUpdatedAt(Instant.now());
        buildOrderJpaRepository.save(buildOrder);

        if (event.carId() != null) {

            Optional<CarJpa> car = carRepository.findById(event.carId());

            if (car.isEmpty() || !car.get().isAvailable()) {

                buildOrder.setStatus(FAIL);
                buildOrder.setUpdatedAt(Instant.now());
                buildOrderJpaRepository.save(buildOrder);

                OrderRejectedEvent rejectedEvent = new OrderRejectedEvent(event.orderId());
                saveOutboxEvent("order-rejected", rejectedEvent);
                return;
            }

            car.get().setAvailable(false);
            carRepository.save(car.get());

            buildOrder.setStatus(ASSEMBLED);
            buildOrder.setUpdatedAt(Instant.now());
            buildOrderJpaRepository.save(buildOrder);

            OrderApprovedEvent approvedEvent = new OrderApprovedEvent(event.orderId());

            saveOutboxEvent("order-approved", approvedEvent);
        }

        if (event.configurationId() != null) {

            buildOrder.setStatus(ASSEMBLED);
            buildOrder.setUpdatedAt(Instant.now());
            buildOrderJpaRepository.save(buildOrder);

            OrderApprovedEvent approvedEvent = new OrderApprovedEvent(event.orderId());
            saveOutboxEvent("order-approved", approvedEvent);
        }
    }

    private void saveOutboxEvent(String topic, Object event) {

        try {

            String payload = objectMapper.writeValueAsString(event);

            OutboxEventJpa outbox = new OutboxEventJpa(UUID.randomUUID(), topic, payload);

            outboxRepository.save(outbox);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }
}
