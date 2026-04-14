package org.autosalon.persistence.repository;

import org.autosalon.domain.model.users.*;
import org.autosalon.domain.repositories.IUserRepository;
import org.autosalon.mapper.mapperJpa.UserJpaMapper;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JpaUserRepository implements IUserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserJpaMapper mapper;

    public JpaUserRepository(UserJpaRepository jpaRepository, UserJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(User user) {
        UserJpa jpa = mapper.toJpa(user);
        jpaRepository.save(jpa);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).filter(user -> !user.isRemoved()).map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        UserJpa user = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setRemoved(true);

        jpaRepository.save(user);
    }
}