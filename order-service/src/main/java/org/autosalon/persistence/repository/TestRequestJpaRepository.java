package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.testRequest.TestRequestJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRequestJpaRepository extends JpaRepository<TestRequestJpa, UUID> {
}