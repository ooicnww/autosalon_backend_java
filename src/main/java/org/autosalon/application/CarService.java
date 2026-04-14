    package org.autosalon.application;

    import org.autosalon.domain.exceptions.DomainValidationException;
    import org.autosalon.domain.model.entities.car.Car;
    import org.autosalon.domain.model.entities.car.CarModel;
    import org.autosalon.domain.repositories.ICarModelRepository;
    import org.autosalon.domain.repositories.ICarRepository;
    import org.autosalon.presentation.dto.CarDto;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    @Service
    public class CarService {

        private final ICarRepository carRepository;
        private final CarModelService modelService;

        public CarService(ICarRepository carRepository, CarModelService modelService) {
            this.carRepository = carRepository;
            this.modelService = modelService;
        }

        public Car addCar(CarDto dto) {

            CarModel model = modelService.getModelById(dto.modelId());

            Car car = new Car(
                    model,
                    dto.color(),
                    dto.available(),
                    dto.price()
            );

            carRepository.save(car);
            return car;
        }

        public void updateCar(CarDto dto) {

            CarModel model = modelService.getModelById(dto.modelId());

            Car car = new Car(
                    dto.id(),
                    model,
                    dto.color(),
                    dto.available(),
                    dto.price()
            );

            carRepository.save(car);
        }

        public List<Car> getAllCars() {
            return carRepository.findAll();
        }

        public Optional<Car> getCarById(UUID id) {
            return carRepository.findById(id);
        }

        public void deleteCar(UUID id) {
            carRepository.delete(id);
        }
    }