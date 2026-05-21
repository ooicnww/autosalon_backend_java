//package org.autosalon;
//
//import org.autosalon.domain.model.entities.car.*;
//import org.autosalon.domain.model.enums.*;
//import org.autosalon.domain.valueObjects.ModelKey;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CarConfiguratorTest {
//
//    @Test
//    void shouldCalculateFullPrice() {
//
//        Set<ModelKey> models = new HashSet<>();
//        models.add(new ModelKey("BMW", "320i"));
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        components.put(
//                CarComponentType.WHEEL,
//                new CarComponent(
//                        CarComponentType.WHEEL,
//                        "Sport wheel",
//                        5000,
//                        models
//                )
//        );
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        CarConfiguration config = new CarConfiguration(model, components);
//
//        int expectedPrice = 1000000 + 5000;
//
//        assertEquals(expectedPrice, config.getFullPrice());
//    }
//
//    @Test
//    void shouldReturnBasePriceWhenNoComponents() {
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        CarConfiguration config = new CarConfiguration(model, components);
//
//        assertEquals(1000000, config.getFullPrice());
//    }
//
//    @Test
//    void shouldCalculatePriceWithMultipleComponents() {
//
//        Set<ModelKey> models = Set.of(new ModelKey("BMW", "320i"));
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        components.put(
//                CarComponentType.WHEEL,
//                new CarComponent(CarComponentType.WHEEL, "Sport wheel", 5000, models)
//        );
//
//        components.put(
//                CarComponentType.INTERIOR,
//                new CarComponent(CarComponentType.INTERIOR, "Leather", 10000, models)
//        );
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        CarConfiguration config = new CarConfiguration(model, components);
//
//        assertEquals(1015000, config.getFullPrice());
//    }
//
//    @Test
//    void configurationShouldContainComponents() {
//
//        Set<ModelKey> models = Set.of(new ModelKey("BMW", "320i"));
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        CarComponent wheel = new CarComponent(
//                CarComponentType.WHEEL,
//                "Sport wheel",
//                5000,
//                models
//        );
//
//        components.put(CarComponentType.WHEEL, wheel);
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        CarConfiguration config = new CarConfiguration(model, components);
//
//        assertTrue(config.components().containsKey(CarComponentType.WHEEL));
//    }
//
//    @Test
//    void configurationShouldReturnCorrectModel() {
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        CarConfiguration config = new CarConfiguration(model, components);
//
//        assertEquals("BMW", config.model().getBrand());
//    }
//
//    @Test
//    void componentShouldReturnCorrectPrice() {
//
//        Set<ModelKey> models = Set.of(new ModelKey("BMW", "320i"));
//
//        CarComponent component = new CarComponent(
//                CarComponentType.WHEEL,
//                "Sport wheel",
//                5000,
//                models
//        );
//
//        assertEquals(5000, component.price());
//    }
//
//    @Test
//    void componentShouldReturnCorrectType() {
//
//        Set<ModelKey> models = Set.of(new ModelKey("BMW", "320i"));
//
//        CarComponent component = new CarComponent(
//                CarComponentType.WHEEL,
//                "Sport wheel",
//                5000,
//                models
//        );
//
//        assertEquals(CarComponentType.WHEEL, component.type());
//    }
//
//    @Test
//    void componentShouldReturnCorrectName() {
//
//        Set<ModelKey> models = Set.of(new ModelKey("BMW", "320i"));
//
//        CarComponent component = new CarComponent(
//                CarComponentType.WHEEL,
//                "Sport wheel",
//                5000,
//                models
//        );
//
//        assertEquals("Sport wheel", component.name());
//    }
//
//    @Test
//    void componentShouldContainCompatibleModel() {
//
//        ModelKey key = new ModelKey("BMW", "320i");
//
//        Set<ModelKey> models = Set.of(key);
//
//        CarComponent component = new CarComponent(
//                CarComponentType.WHEEL,
//                "Sport wheel",
//                5000,
//                models
//        );
//
//        assertTrue(component.suitableModels().contains(key));
//    }
//
//    @Test
//    void shouldCreateCarWithCorrectPrice() {
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        Car car = new Car(model, "black", true, 1000000);
//
//        assertEquals(1000000, car.getPrice());
//    }
//
//    @Test
//    void carShouldBeAvailable() {
//
//        Map<CarComponentType, CarComponent> components = new HashMap<>();
//
//        CarModel model = new CarModel(
//                "BMW",
//                "320i",
//                BodyType.SEDAN,
//                FuelType.PETROL,
//                TransmissionType.AUTOMATIC,
//                DriveType.REAR,
//                1000000,
//                components,
//                200,
//                300
//        );
//
//        Car car = new Car(model, "black", true, 1000000);
//
//        assertTrue(car.getIsAvailable());
//    }
//}
//
