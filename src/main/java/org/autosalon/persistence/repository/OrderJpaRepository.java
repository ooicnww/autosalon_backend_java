package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.order.OrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpa, UUID> {
}