package org.autosalon.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.autosalon.config.SecurityUtils;
import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.exceptions.LockedActionException;
import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.domain.repositories.IUserRepository;
import org.autosalon.event.OrderSentForApprovalEvent;
import org.autosalon.infrastructure.client.StorageClient;
import org.autosalon.infrastructure.client.dto.CarResponse;
import org.autosalon.infrastructure.client.dto.ConfigurationResponse;
import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.autosalon.persistence.repository.OutboxJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final StorageClient storageClient;
    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public OrderService(
            IOrderRepository orderRepository,
            IUserRepository userRepository,
            StorageClient storageClient,
            OutboxJpaRepository outboxRepository,
            ObjectMapper objectMapper
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.storageClient = storageClient;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    public CustomOrder createCustomOrder(UUID configurationId) {

        UUID userId = SecurityUtils.getCurrentUserId();

        Client client = (Client) userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Клиент не найден"));

        ConfigurationResponse configuration = storageClient.getConfiguration(configurationId);

        Manager manager = userRepository
                .findAll()
                .stream()
                .filter(user -> user instanceof Manager)
                .map(user -> (Manager) user)
                .findAny()
                .orElseThrow(() ->
                        new DomainValidationException("Нет доступных менеджеров"));

        CustomOrder order = new CustomOrder(
                client,
                manager,
                configuration.id()
        );

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public ExistedCarOrder createExistedCarOrder(UUID carId) {

        UUID userId = SecurityUtils.getCurrentUserId();

        Client client = (Client) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Клиент не найден"));

        CarResponse car = storageClient.getCar(carId);

        if (!car.available()) {
            throw new LockedActionException("Нельзя купить машину не в наличии");
        }

        Manager manager = userRepository
                .findAll()
                .stream()
                .filter(user -> user instanceof Manager)
                .map(user -> (Manager) user)
                .findAny()
                .orElseThrow(() -> new DomainValidationException("Нет доступных менеджеров"));

        ExistedCarOrder order = new ExistedCarOrder(
                client,
                manager,
                car.id()
        );

        orderRepository.save(order);

        return order;
    }

    public List<Order> getAllOrders() {

        UUID userId = SecurityUtils.getCurrentUserId();

        if (SecurityUtils.hasRole("ADMIN") || SecurityUtils.hasRole("MANAGER")) {
            return orderRepository.findAll();
        }

        return orderRepository.findByUserId(userId);
    }

    private void checkOwner(Order order) {

        if (SecurityUtils.hasRole("ADMIN") || SecurityUtils.hasRole("MANAGER")) {
            return;
        }

        UUID currentUserId = SecurityUtils.getCurrentUserId();

        if (!order.getClient().getId().equals(currentUserId)) {
            throw new RuntimeException("Доступ запрещен");
        }
    }

    public void approveOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.approve();
        order.waitForPayment();

        orderRepository.save(order);
    }

    @Transactional
    public void payOrder(UUID id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        checkOwner(order);

        order.paid();

        orderRepository.save(order);

        UUID carId = null;
        UUID configurationId = null;

        if (order instanceof ExistedCarOrder existed) {
            carId = existed.getCarId();
        }

        if (order instanceof CustomOrder custom) {
            configurationId = custom.getConfigurationId();
        }

        OrderSentForApprovalEvent event = new OrderSentForApprovalEvent(order.getId(), carId, configurationId, UUID.randomUUID().toString());

        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        OutboxEventJpa outbox = new OutboxEventJpa(UUID.randomUUID(), "order-sent-for-approval", payload);

        outboxRepository.save(outbox);

    }

    public void completeOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        checkOwner(order);

        order.complete();

        orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        checkOwner(order);

        orderRepository.delete(id);
    }
}