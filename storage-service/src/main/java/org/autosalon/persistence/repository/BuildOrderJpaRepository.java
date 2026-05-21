package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.build.BuildOrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuildOrderJpaRepository extends JpaRepository<BuildOrderJpa, UUID> {
    boolean existsBySourceOrderId(UUID sourceOrderId);
}