package org.autosalon;

import org.autosalon.application.OrderService;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.entities.order.OrderStatus;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;
import org.autosalon.domain.model.users.UserType;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.autosalon.persistence.repository.OutboxJpaRepository;
import org.autosalon.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class OrderIntegrationTest {

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
    private OrderService orderService;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private OutboxJpaRepository outboxRepository;

    @Autowired
    private UserJpaRepository userRepository;

    @Test
    void payOrderShouldCreateOutboxEvent() {

        UUID orderId = UUID.randomUUID();

        Client client =
                new Client(
                        UUID.randomUUID(),
                        "client",
                        "client@test.com"
                );

        Manager manager =
                new Manager(
                        UUID.randomUUID(),
                        "manager",
                        "manager@test.com"
                );

        UserJpa clientJpa = new UserJpa();
        clientJpa.setId(client.getId());
        clientJpa.setName(client.getName());
        clientJpa.setEmail(client.getEmail());
        clientJpa.setType(UserType.CLIENT);

        UserJpa managerJpa = new UserJpa();
        managerJpa.setId(manager.getId());
        managerJpa.setName(manager.getName());
        managerJpa.setEmail(manager.getEmail());
        managerJpa.setType(UserType.MANAGER);

        userRepository.save(clientJpa);
        userRepository.save(managerJpa);

        ExistedCarOrder order =
                new ExistedCarOrder(
                        orderId,
                        client,
                        manager,
                        UUID.randomUUID(),
                        OrderStatus.WAITING_FOR_PAYMENT
                );

        orderRepository.save(order);

        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", client.getId().toString())
                .build();

        var auth =
                new JwtAuthenticationToken(
                        jwt,
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_CLIENT"
                                )
                        )
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        orderService.payOrder(orderId);

        Order savedOrder =
                orderRepository.findById(orderId)
                        .orElseThrow();

        assertEquals(
                OrderStatus.PAID,
                savedOrder.getStatus()
        );

        var events =
                outboxRepository.findAll();

        assertFalse(events.isEmpty());

        OutboxEventJpa event = events.get(0);

        assertEquals(
                "order-sent-for-approval",
                event.getTopic()
        );

        assertFalse(event.isProcessed());

        assertNotNull(event.getPayload());
    }

    @Test
    void readyForSellShouldUpdateStatus() {

        Client client =
                new Client(
                        UUID.randomUUID(),
                        "client",
                        "client@test.com"
                );

        Manager manager =
                new Manager(
                        UUID.randomUUID(),
                        "manager",
                        "manager@test.com"
                );

        ExistedCarOrder order =
                new ExistedCarOrder(
                        UUID.randomUUID(),
                        client,
                        manager,
                        UUID.randomUUID(),
                        OrderStatus.PAID
                );

        order.readyForSell();

        assertEquals(
                OrderStatus.READY_FOR_SELL,
                order.getStatus()
        );
    }

    @Test
    void outboxEventShouldBeCreatedAfterPayment() {

        UUID orderId = UUID.randomUUID();

        Client client =
                new Client(
                        UUID.randomUUID(),
                        "client",
                        "client@test.com"
                );

        Manager manager =
                new Manager(
                        UUID.randomUUID(),
                        "manager",
                        "manager@test.com"
                );

        UserJpa clientJpa = new UserJpa();
        clientJpa.setId(client.getId());
        clientJpa.setName(client.getName());
        clientJpa.setEmail(client.getEmail());
        clientJpa.setType(UserType.CLIENT);

        UserJpa managerJpa = new UserJpa();
        managerJpa.setId(manager.getId());
        managerJpa.setName(manager.getName());
        managerJpa.setEmail(manager.getEmail());
        managerJpa.setType(UserType.MANAGER);

        userRepository.save(clientJpa);
        userRepository.save(managerJpa);

        ExistedCarOrder order =
                new ExistedCarOrder(
                        orderId,
                        client,
                        manager,
                        UUID.randomUUID(),
                        OrderStatus.WAITING_FOR_PAYMENT
                );

        orderRepository.save(order);

        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", client.getId().toString())
                .build();

        var auth =
                new JwtAuthenticationToken(
                        jwt,
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_CLIENT"
                                )
                        )
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        orderService.payOrder(orderId);

        List<OutboxEventJpa> events =
                outboxRepository.findAll();

        assertEquals(1, events.size());

        OutboxEventJpa event = events.get(0);

        assertEquals(
                "order-sent-for-approval",
                event.getTopic()
        );
    }

    @Test
    void outboxEventShouldContainPayload() {

        UUID orderId = UUID.randomUUID();

        Client client =
                new Client(
                        UUID.randomUUID(),
                        "client",
                        "client@test.com"
                );

        Manager manager =
                new Manager(
                        UUID.randomUUID(),
                        "manager",
                        "manager@test.com"
                );

        UserJpa clientJpa = new UserJpa();
        clientJpa.setId(client.getId());
        clientJpa.setName(client.getName());
        clientJpa.setEmail(client.getEmail());
        clientJpa.setType(UserType.CLIENT);

        UserJpa managerJpa = new UserJpa();
        managerJpa.setId(manager.getId());
        managerJpa.setName(manager.getName());
        managerJpa.setEmail(manager.getEmail());
        managerJpa.setType(UserType.MANAGER);

        userRepository.save(clientJpa);
        userRepository.save(managerJpa);

        ExistedCarOrder order =
                new ExistedCarOrder(
                        orderId,
                        client,
                        manager,
                        UUID.randomUUID(),
                        OrderStatus.WAITING_FOR_PAYMENT
                );

        orderRepository.save(order);

        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", client.getId().toString())
                .build();

        var auth =
                new JwtAuthenticationToken(
                        jwt,
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_CLIENT"
                                )
                        )
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        orderService.payOrder(orderId);

        OutboxEventJpa event =
                outboxRepository.findAll().get(0);

        assertNotNull(event.getPayload());

        assertTrue(
                event.getPayload()
                        .contains(orderId.toString())
        );
    }
}