package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarComponentRepository;
import org.autosalon.mapper.mapperJpa.CarComponentJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarComponentJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
@Primary
public class JpaCarComponentRepository implements ICarComponentRepository {
    private final CarComponentJpaRepository carComponentRepository;
    private final CarModelJpaRepository carModelRepository;
    private final CarComponentJpaMapper mapper;

    public JpaCarComponentRepository(CarComponentJpaRepository carComponentRepository,CarModelJpaRepository carModelRepository, CarComponentJpaMapper mapper){
        this.carComponentRepository = carComponentRepository;
        this.carModelRepository = carModelRepository;
        this.mapper = mapper;
    }


    @Override
    public void save(CarComponent carComponent) {
        Set<CarModelJpa> suitableModels = carComponent.getSuitableModels() == null
                ? Set.of()
                : carComponent.getSuitableModels().stream().map(id -> carModelRepository.findById(id).orElseThrow(() -> new RuntimeException("Модель " + id + " не найдена"))).collect(Collectors.toSet());
        CarComponentJpa jpa = mapper.toJpa(carComponent, suitableModels);
        carComponentRepository.save(jpa);
    }

    @Override
    public Optional<CarComponent> findById(UUID id){
        return carComponentRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain);
    }

    @Override
    public List<CarComponent> findAll(){
        return carComponentRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        CarComponentJpa jpa = carComponentRepository.findById(id).orElseThrow(() -> new RuntimeException("Компонента не найдена"));
        jpa.setRemoved(true);
        carComponentRepository.save(jpa);
    }
}


