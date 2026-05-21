package org.autosalon.domain.repositories;

import org.autosalon.domain.build.BuildOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBuildOrderRepository {

    BuildOrder save(BuildOrder buildOrder);

    Optional<BuildOrder> findById(UUID id);

    List<BuildOrder> findAll();

    void delete(UUID id);
}