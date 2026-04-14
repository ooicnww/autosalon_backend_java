package org.autosalon.application;

import jakarta.transaction.Transactional;
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

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public OrderService(IOrderRepository orderRepository, IUserRepository userRepository, ICarRepository carRepository,  ICarConfigurationRepository configurationRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.configurationRepository = configurationRepository;
    }

    public CustomOrder createCustomOrder(UUID clientId, UUID configurationId){
        Client client = (Client) userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
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
    public ExistedCarOrder createExistedCarOrder(UUID clientId, UUID carId){
        Client client = (Client) userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Клиент не найден"));
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

    public void approveOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        order.approve();
        orderRepository.save(order);
    }


    public void payOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        order.paid();
        orderRepository.save(order);
    }

    public void completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Заказ не найден"));

        order.complete();
        orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.delete(id);
    }
}