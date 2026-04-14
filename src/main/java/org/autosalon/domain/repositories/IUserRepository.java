package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.model.users.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    void save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    void delete(UUID id);
}