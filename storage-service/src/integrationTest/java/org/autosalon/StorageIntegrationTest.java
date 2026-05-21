package org.autosalon;

import org.autosalon.domain.build.BuildOrder;
import org.autosalon.domain.build.BuildOrderStatus;
import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;
import org.autosalon.domain.repositories.IBuildOrderRepository;
import org.autosalon.event.OrderSentForApprovalEvent;
import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.repository.CarJpaRepository;
import org.autosalon.persistence.repository.CarModelJpaRepository;
import org.autosalon.persistence.repository.OutboxJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class StorageIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("testdb")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @Container
    static KafkaContainer kafka =
            new KafkaContainer(
                    DockerImageName.parse(
                            "confluentinc/cp-kafka:7.4.0"
                    )
            );

    @DynamicPropertySource
    static void properties(
            DynamicPropertyRegistry registry
    ) {

        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
        );

        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );

        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );

        registry.add(
                "spring.kafka.bootstrap-servers",
                kafka::getBootstrapServers
        );
    }

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private IBuildOrderRepository buildOrderRepository;

    @Autowired
    private CarJpaRepository carRepository;

    @Autowired
    private OutboxJpaRepository outboxRepository;

    @Autowired
    private CarModelJpaRepository modelRepository;

    @Test
    void kafkaEventShouldCreateBuildOrder() throws Exception {

        UUID orderId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        CarModelJpa model = new CarModelJpa();

        model.setId(UUID.randomUUID());

        model.setBrand("BMW");
        model.setModelName("M5");

        model.setBodyType(BodyType.SEDAN);

        model.setFuelType(FuelType.PETROL);

        model.setTransmissionType(
                TransmissionType.AUTOMATIC
        );

        model.setDriveType(DriveType.ALL_WHEEL);

        model.setBasePrice(100000);

        model.setEnginePower(600);

        model.setEngineCapacity(4400);

        modelRepository.saveAndFlush(model);

        CarJpa car = new CarJpa();

        car.setId(carId);
        car.setModel(model);
        car.setColor("black");
        car.setAvailable(true);
        car.setPrice(100000);

        carRepository.saveAndFlush(car);

        OrderSentForApprovalEvent event =
                new OrderSentForApprovalEvent(
                        orderId,
                        carId,
                        null,
                        UUID.randomUUID().toString()
                );

        kafkaTemplate.send(
                "order-sent-for-approval",
                event
        );

        Thread.sleep(5000);

        BuildOrder buildOrder =
                buildOrderRepository
                        .findAll()
                        .get(0);

        assertEquals(
                BuildOrderStatus.ASSEMBLED,
                buildOrder.getStatus()
        );

        assertFalse(buildOrder.isRemoved());
    }

    @Test
    void kafkaEventShouldCreateOutboxEvent() throws Exception {

        UUID orderId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        CarModelJpa model = new CarModelJpa();

        model.setId(UUID.randomUUID());

        model.setBrand("Audi");
        model.setModelName("RS6");

        model.setBodyType(BodyType.WAGON);

        model.setFuelType(FuelType.PETROL);

        model.setTransmissionType(
                TransmissionType.AUTOMATIC
        );

        model.setDriveType(DriveType.FRONT);

        model.setBasePrice(120000);

        model.setEnginePower(630);

        model.setEngineCapacity(4000);

        modelRepository.saveAndFlush(model);

        CarJpa car = new CarJpa();

        car.setId(carId);
        car.setModel(model);
        car.setColor("white");
        car.setAvailable(true);
        car.setPrice(120000);

        carRepository.saveAndFlush(car);

        OrderSentForApprovalEvent event =
                new OrderSentForApprovalEvent(
                        orderId,
                        carId,
                        null,
                        UUID.randomUUID().toString()
                );

        kafkaTemplate.send(
                "order-sent-for-approval",
                event
        );

        Thread.sleep(5000);

        var events =
                outboxRepository.findAll();

        assertFalse(events.isEmpty());

        OutboxEventJpa outboxEvent =
                events.get(0);

        assertEquals(
                "order-approved",
                outboxEvent.getTopic()
        );

        assertNotNull(
                outboxEvent.getPayload()
        );
    }
}