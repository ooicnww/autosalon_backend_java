package org.autosalon.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autosalon.event.OrderSentForApprovalEvent;
import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.autosalon.persistence.repository.OutboxJpaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxPublisher {

    private final OutboxJpaRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxPublisher(OutboxJpaRepository repository, KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    public void publish() {

        var events = repository.findByProcessedFalse();

        for (OutboxEventJpa event : events) {
            try {

                OrderSentForApprovalEvent payload = objectMapper.readValue(event.getPayload(), OrderSentForApprovalEvent.class);

                kafkaTemplate.send(event.getTopic(), payload);
                event.setProcessed(true);
                repository.save(event);

                System.out.println("OUTBOX EVENT SENT: " + event.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}