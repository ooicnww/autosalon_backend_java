package org.autosalon.persistence.repository;

import org.autosalon.domain.build.BuildOrder;
import org.autosalon.domain.repositories.IBuildOrderRepository;
import org.autosalon.mapper.mapperJpa.BuildOrderJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaBuildOrderRepository implements IBuildOrderRepository {

    private final BuildOrderJpaRepository repository;
    private final BuildOrderJpaMapper mapper;

    public JpaBuildOrderRepository(
            BuildOrderJpaRepository repository,
            BuildOrderJpaMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BuildOrder save(BuildOrder buildOrder) {
        return mapper.toDomain(repository.save(mapper.toJpa(buildOrder))
        );
    }

    @Override
    public Optional<BuildOrder> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<BuildOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}