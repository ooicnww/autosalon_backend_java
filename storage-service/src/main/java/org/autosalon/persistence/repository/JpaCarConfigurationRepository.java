package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarConfigurationRepository;
import org.autosalon.domain.services.CarConfigurationDomainService;
import org.autosalon.mapper.mapperJpa.CarConfigurationJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarConfigurationJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JpaCarConfigurationRepository implements ICarConfigurationRepository {

    private final CarConfigurationJpaRepository carConfigurationJpaRepository;
    private final CarConfigurationDomainService carConfigurationDomainService;
    private final CarConfigurationJpaMapper carConfigurationJpaMapper;

    public JpaCarConfigurationRepository(CarConfigurationJpaRepository carConfigurationJpaRepository, CarConfigurationDomainService carConfigurationDomainService, CarConfigurationJpaMapper carConfigurationJpaMapper){
        this.carConfigurationJpaRepository = carConfigurationJpaRepository;
        this.carConfigurationDomainService = carConfigurationDomainService;
        this.carConfigurationJpaMapper = carConfigurationJpaMapper;
    }

    @Override
    public void save(CarConfiguration carConfiguration){
        CarConfigurationJpa jpa = carConfigurationJpaMapper.toJpa(carConfiguration);

        carConfigurationJpaRepository.save(jpa);    }

    @Override
    public Optional<CarConfiguration> findById(UUID id){
        return carConfigurationJpaRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(carConfigurationJpaMapper::toDomain);
    }

    @Override
    public List<CarConfiguration> findAll(){
        return carConfigurationJpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(carConfigurationJpaMapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        CarConfigurationJpa jpa = carConfigurationJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("Модель не найдена"));
        jpa.setRemoved(true);
        carConfigurationJpaRepository.save(jpa);
    }
}
