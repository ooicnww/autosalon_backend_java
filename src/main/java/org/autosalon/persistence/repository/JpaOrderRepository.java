package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.enums.OrderType;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.mapper.mapperJpa.CarConfigurationJpaMapper;
import org.autosalon.mapper.mapperJpa.CarJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarConfigurationJpa;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.order.OrderJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JpaOrderRepository implements IOrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final CarConfigurationJpaMapper carConfigurationMapper;
    private final CarJpaMapper carJpaMapper;

    public JpaOrderRepository(OrderJpaRepository jpaRepository, CarConfigurationJpaMapper carConfigurationMapper, CarJpaMapper carJpaMapper) {
        this.jpaRepository = jpaRepository;
        this.carConfigurationMapper = carConfigurationMapper;
        this.carJpaMapper = carJpaMapper;
    }

    @Override
    public void save(Order order) {
        OrderJpa jpa = new OrderJpa();

        jpa.setId(order.getId());
        jpa.setStatus(order.getStatus());

        UserJpa client = new UserJpa();
        client.setId(order.getClient().getId());
        jpa.setClient(client);

        UserJpa manager = new UserJpa();
        manager.setId(order.getManager().getId());
        jpa.setManager(manager);

        if (order instanceof CustomOrder custom) {
            jpa.setType(OrderType.CUSTOM);

            CarConfigurationJpa config = new CarConfigurationJpa();
            config.setId(custom.getConfiguration().id());
            jpa.setConfiguration(config);
        }

        if (order instanceof ExistedCarOrder existed) {
            jpa.setType(OrderType.EXISTED);

            CarJpa car = new CarJpa();
            car.setId(existed.getCar().getId());
            jpa.setCar(car);
        }

        jpaRepository.save(jpa);
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(this::toDomain).toList();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(this::toDomain);
    }

    @Override
    public void delete(UUID id) {
        OrderJpa jpa = jpaRepository.findById(id).orElseThrow();
        jpa.setRemoved(true);
        jpaRepository.save(jpa);
    }

    private Order toDomain(OrderJpa jpa) {

        Client client = new Client(
                jpa.getClient().getId(),
                jpa.getClient().getName(),
                jpa.getClient().getEmail()
        );

        Manager manager = new Manager(
                jpa.getManager().getId(),
                jpa.getManager().getName(),
                jpa.getManager().getEmail()
        );

        if (jpa.getType() == OrderType.CUSTOM) {
            return new CustomOrder(
                    jpa.getId(),
                    client,
                    manager,
                    carConfigurationMapper.toDomain(jpa.getConfiguration()),
                    jpa.getStatus()
            );
        }

        if (jpa.getType() == OrderType.EXISTED) {
            return new ExistedCarOrder(
                    jpa.getId(),
                    client,
                    manager,
                    carJpaMapper.toDomain(jpa.getCar()),
                    jpa.getStatus()
            );
        }

        throw new RuntimeException("Неизвестный тип заказа");
    }
}