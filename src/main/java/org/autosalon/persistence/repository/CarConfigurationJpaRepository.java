package org.autosalon.persistence.repository;
import org.autosalon.persistence.entityJpa.car.CarConfigurationJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarConfigurationJpaRepository extends JpaRepository<CarConfigurationJpa, UUID>{
}
