package org.autosalon.application;

import jakarta.transaction.Transactional;
import org.autosalon.config.SecurityUtils;
import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.exceptions.LockedActionException;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;
import org.autosalon.domain.model.users.User;
import org.autosalon.domain.repositories.ICarConfigurationRepository;
import org.autosalon.domain.repositories.ICarRepository;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService{
    private IOrderRepository orderRepository;
    private IUserRepository userRepository;
    private ICarRepository carRepository;
    private ICarConfigurationRepository configurationRepository;

    public OrderService(IOrderRepository orderRepository, IUserRepository userRepository, ICarRepository carRepository,  ICarConfigurationRepository configurationRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.configurationRepository = configurationRepository;
    }

    public CustomOrder createCustomOrder(UUID configurationId){
        UUID userId = SecurityUtils.getCurrentUserId();
        Client client = (Client) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
        CarConfiguration configuration = configurationRepository.findById(configurationId).orElseThrow(() -> new RuntimeException("Конфигурация не найдена"));

        Manager manager = userRepository
                .findAll()
                .stream()
                .filter(user -> user instanceof Manager)
                .map(user -> (Manager) user)
                .findAny()
                .orElseThrow(() -> new DomainValidationException("Нет доступных менеджеров"));
        CustomOrder order = new CustomOrder(client, manager, configuration);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public ExistedCarOrder createExistedCarOrder(UUID carId){
        UUID userId = SecurityUtils.getCurrentUserId();
        Client client = (Client) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Машина не найдена"));

        if (!car.isAvailable()) {
            throw new LockedActionException("Нельзя купить машину не в наличии");
        }
        Manager manager = userRepository
                .findAll()
                .stream()
                .filter(user -> user instanceof Manager)
                .map(user -> (Manager) user)
                .findAny()
                .orElseThrow(() -> new DomainValidationException("Нет доступных менеджеров"));
        ExistedCarOrder order = new ExistedCarOrder(client, manager, car);
        orderRepository.save(order);
        car.resetAvailable();
        carRepository.save(car);
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
        orderRepository.save(order);
    }


    public void payOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        checkOwner(order);

        order.paid();
        orderRepository.save(order);
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