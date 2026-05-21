package org.autosalon.persistence.repository;

import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpa, UUID> {
}