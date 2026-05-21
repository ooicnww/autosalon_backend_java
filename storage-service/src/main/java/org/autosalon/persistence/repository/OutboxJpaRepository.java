package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.OutboxEventJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxJpaRepository extends JpaRepository<OutboxEventJpa, UUID> {
    List<OutboxEventJpa> findByProcessedFalse();
}