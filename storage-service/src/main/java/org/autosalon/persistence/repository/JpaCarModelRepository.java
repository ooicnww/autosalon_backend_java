package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarModelRepository;
import org.autosalon.mapper.mapperJpa.CarModelJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarComponentJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.specification.CarModelSpecification;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaCarModelRepository implements ICarModelRepository {
    private final CarModelJpaRepository carModelJpaRepository;
    private final CarComponentJpaRepository carComponentJpaRepository;
    private final CarModelJpaMapper mapper;

    public JpaCarModelRepository(CarModelJpaRepository jpaRepository,CarComponentJpaRepository carComponentJpaRepository, CarModelJpaMapper mapper) {
        this.carModelJpaRepository = jpaRepository;
        this.carComponentJpaRepository = carComponentJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(CarModel model) {

        Map<CarComponentType, CarComponentJpa> defaultComponentsMap = model.getDefaultComponents() == null
                ? Map.of()
                : model.getDefaultComponents().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> carComponentJpaRepository.findById(entry.getValue().getId()).orElseThrow(() -> new RuntimeException("Компонента не найдена"))));

        CarModelJpa jpa = mapper.toJpa(model, defaultComponentsMap);

        carModelJpaRepository.save(jpa);
    }


    @Override
    public Optional<CarModel> findById(UUID id){
        return carModelJpaRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain);
    }

    @Override
    public List<CarModel> findAll(){
        return carModelJpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public List<CarModel> findAllWithFilters(String brand, UUID componentId) {

        Specification<CarModelJpa> spec = Specification.where(CarModelSpecification.withBrand(brand)).and(CarModelSpecification.withComponent(componentId));

        return carModelJpaRepository.findAll(spec).stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        CarModelJpa jpa = carModelJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Модель не найдена"));
        jpa.setRemoved(true);
        carModelJpaRepository.save(jpa);
    }

}