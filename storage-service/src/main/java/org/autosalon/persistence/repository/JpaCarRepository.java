package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarRepository;
import org.autosalon.mapper.mapperJpa.CarJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JpaCarRepository implements ICarRepository {
    private final CarJpaRepository jpaRepository;
    private final CarJpaMapper mapper;

    public JpaCarRepository(CarJpaRepository jpaRepository, CarJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Car car) {
        jpaRepository.save(mapper.toJpa(car));
    }


    @Override
    public Optional<Car> findById(UUID id){
        return jpaRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain);
    }

    @Override
    public List<Car> findAll(){
        return jpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        CarJpa jpa = jpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Машина не найдена"));
        jpa.setRemoved(true);
        jpaRepository.save(jpa);
    }
}