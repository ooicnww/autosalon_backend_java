package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CarModelJpaRepository extends JpaRepository<CarModelJpa, UUID>, JpaSpecificationExecutor<CarModelJpa> {
}