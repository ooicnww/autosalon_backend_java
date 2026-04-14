package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.Part;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPartRepository{
    void save(Part part);

    Optional<Part> findById(UUID id);

    List<Part> findAll();

    void delete(UUID id);
}