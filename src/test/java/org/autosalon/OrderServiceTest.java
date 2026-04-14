//package org.autosalon;
//
//import org.autosalon.application.OrderService;
//import org.autosalon.domain.model.entities.car.*;
//import org.autosalon.domain.model.entities.order.OrderStatus;
//import org.autosalon.domain.model.enums.*;
//import org.autosalon.domain.model.users.Client;
//import org.autosalon.domain.model.users.Manager;
//import org.autosalon.infrastructure.repositories.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class OrderServiceTest {
//
//    @Test
//    void shouldCreateOrderForExistingCar() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo, userRepo, carRepo);
//
//        Client client = new Client("Ivan", "mail");
//        Manager manager = new Manager("Anna", "mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
//
//        CarModel model = new CarModel(
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
//        Car car = new Car(model, "black", true, 1000000);
//
//        var order = service.createExistedCarOrder(client, car);
//
//        assertNotNull(order);
//        assertEquals(1, orderRepo.findAll().size());
//    }
//
//
//    @Test
//    void shouldSaveOrderInRepository() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo, userRepo, carRepo);
//
//        Client client = new Client("Ivan", "mail");
//        Manager manager = new Manager("Anna", "mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        service.createExistedCarOrder(client,car);
//
//        assertEquals(1, orderRepo.findAll().size());
//    }
//
//    @Test
//    void shouldAssignManagerToOrder() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo, userRepo, carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertNotNull(order.getManager());
//    }
//
//    @Test
//    void orderShouldHaveClient() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertEquals(client, order.getClient());
//    }
//
//    @Test
//    void orderShouldContainCar() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertEquals(car, order.getCar());
//    }
//
//    @Test
//    void orderRepositoryShouldReturnOrders() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        service.createExistedCarOrder(client,car);
//
//        assertFalse(orderRepo.findAll().isEmpty());
//    }
//
//    @Test
//    void shouldGenerateOrderId() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertNotNull(order.getId());
//    }
//
//    @Test
//    void orderShouldHaveCreatedStatus() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertEquals(OrderStatus.CREATED, order.getStatus());
//    }
//
//    @Test
//    void shouldCreateMultipleOrders() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car1 = new Car(model,"black",true,1000000);
//        Car car2 = new Car(model,"white",true,1000000);
//
//        service.createExistedCarOrder(client,car1);
//        service.createExistedCarOrder(client,car2);
//
//        assertEquals(2, orderRepo.findAll().size());
//    }
//
//    @Test
//    void orderShouldHaveCreationDate() {
//
//        var carRepo = new InMemoryCarRepository();
//        var orderRepo = new InMemoryOrderRepository();
//        var userRepo = new InMemoryUserRepository();
//
//        OrderService service = new OrderService(orderRepo,userRepo,carRepo);
//
//        Client client = new Client("Ivan","mail");
//        Manager manager = new Manager("Anna","mail");
//
//        userRepo.save(client);
//        userRepo.save(manager);
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
//        Car car = new Car(model,"black",true,1000000);
//
//        var order = service.createExistedCarOrder(client,car);
//
//        assertNotNull(order.getCreatedAt());
//    }
//}