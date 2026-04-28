package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.order.OrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpa, UUID> {
    List<OrderJpa> findByClient_IdAndRemovedFalse(UUID clientId);
}