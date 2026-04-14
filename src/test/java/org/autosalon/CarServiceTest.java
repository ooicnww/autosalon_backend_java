//package org.autosalon;
//
//import org.autosalon.application.CarService;
//import org.autosalon.domain.model.entities.car.*;
//import org.autosalon.domain.model.enums.*;
//import org.autosalon.infrastructure.repositories.InMemoryCarRepository;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CarServiceTest {
//
//    @Test
//    void shouldFilterCarsByBrand() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel bmw = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        CarModel audi = new CarModel(
//                "Audi",
//                "A4",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.FRONT,
//                900000,
//                new HashMap<>(),
//                190,
//                280
//        );
//
//        repo.save(new Car(bmw, "black", true, 1000000));
//        repo.save(new Car(audi, "white", true, 900000));
//
//        List<Car> result = service.filterCars(
//                null,
//                null,
//                "BMW",
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByColor() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel model = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(model,"black",true,1000000));
//        repo.save(new Car(model,"white",true,1000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                null,null,null,null,"black"
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByFuelType() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel petrol = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        CarModel diesel = new CarModel(
//                "BMW","320d",
//                BodyType.SEDAN,
//                FuelType.DIESEL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(petrol,"black",true,1000000));
//        repo.save(new Car(diesel,"white",true,1000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,
//                FuelType.PETROL,null,null,null,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByPriceMin() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel model = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(model,"black",true,800000));
//        repo.save(new Car(model,"white",true,1500000));
//
//        List<Car> result = service.filterCars(
//                1000000,null,null,null,null,null,
//                null,null,null,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByPriceMax() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel model = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(model,"black",true,800000));
//        repo.save(new Car(model,"white",true,1500000));
//
//        List<Car> result = service.filterCars(
//                null,1000000,null,null,null,null,
//                null,null,null,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByTransmission() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel auto = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        CarModel manual = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.MANUAL,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(auto,"black",true,1000000));
//        repo.save(new Car(manual,"white",true,1000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                null,null,null,TransmissionType.AUTOMATIC,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterCarsByDriveType() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel rear = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        CarModel front = new CarModel(
//                "Audi","A4",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.FRONT,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(rear,"black",true,1000000));
//        repo.save(new Car(front,"white",true,1000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                null,null,DriveType.REAR,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldReturnAllCarsWhenNoFilters() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel model = new CarModel(
//                "BMW","320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                200,
//                300
//        );
//
//        repo.save(new Car(model,"black",true,1000000));
//        repo.save(new Car(model,"white",true,1000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                null,null,null,null,null
//        );
//
//        assertEquals(2,result.size());
//    }
//
//    @Test
//    void shouldFilterByEnginePower() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel weak = new CarModel(
//                "BMW","316i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                120,
//                200
//        );
//
//        CarModel strong = new CarModel(
//                "BMW","M3",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                3000000,
//                new HashMap<>(),
//                450,
//                500
//        );
//
//        repo.save(new Car(weak,"black",true,1000000));
//        repo.save(new Car(strong,"white",true,3000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                300,null,null,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//
//    @Test
//    void shouldFilterByEngineCapacity() {
//
//        var repo = new InMemoryCarRepository();
//        var service = new CarService(repo);
//
//        CarModel small = new CarModel(
//                "BMW","316i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                new HashMap<>(),
//                120,
//                1600
//        );
//
//        CarModel big = new CarModel(
//                "BMW","M3",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                3000000,
//                new HashMap<>(),
//                450,
//                3000
//        );
//
//        repo.save(new Car(small,"black",true,1000000));
//        repo.save(new Car(big,"white",true,3000000));
//
//        List<Car> result = service.filterCars(
//                null,null,null,null,null,null,
//                null,2000,null,null,null
//        );
//
//        assertEquals(1,result.size());
//    }
//}