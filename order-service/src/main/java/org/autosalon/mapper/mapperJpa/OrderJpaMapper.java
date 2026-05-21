package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.enums.OrderType;
import org.autosalon.persistence.entityJpa.order.OrderJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.stereotype.Component;

@Component
public class OrderJpaMapper {

    public OrderJpa toJpa(Order order) {

        OrderJpa jpa = new OrderJpa();

        jpa.setId(order.getId());

        UserJpa manager = new UserJpa();
        manager.setId(order.getManager().getId());
        jpa.setManager(manager);

        UserJpa client = new UserJpa();
        client.setId(order.getClient().getId());
        jpa.setClient(client);

        jpa.setStatus(order.getStatus());

        if (order instanceof CustomOrder custom) {

            jpa.setType(OrderType.CUSTOM);
            jpa.setConfigurationId(custom.getConfigurationId());
        }

        if (order instanceof ExistedCarOrder existed) {

            jpa.setType(OrderType.EXISTED);
            jpa.setCarId(existed.getCarId());
        }

        return jpa;
    }
}