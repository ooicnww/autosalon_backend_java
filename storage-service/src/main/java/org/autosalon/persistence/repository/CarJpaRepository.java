package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarJpaRepository extends JpaRepository<CarJpa, UUID> {
}