

# Car Dealership Backend System

Backend-приложение для мультибрендового автосалона с поддержкой:

* конфигуратора автомобилей
* оформления заказов
* управления складом и запчастями
* записи на тест-драйв
* ролевой модели доступа

Проект демонстрирует применение **DDD, многослойной архитектуры и современных backend-технологий**.

---

## Технологии

* **Java 21**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Spring Security**
* **PostgreSQL**
* **Liquibase**
* **Keycloak**
* **MapStruct**
* **Gradle**
* **Docker**
* **Testcontainers**
* **JUnit 5**

---

## Архитектура

Проект реализован с разделением на слои:

### 🔹 Domain Layer (`domain`)

* бизнес-сущности (`Car`, `Order`, `CarModel`, `User`)
* value objects (`ModelKey`)
* enum'ы (FuelType, TransmissionType и др.)
* доменные исключения:

    * `DomainValidationException`
    * `NotSuitableComponentException`
    * `EntityNotFoundException`

---

### 🔹 Application Layer (`application`)

* бизнес-логика:

    * `CarService`
    * `OrderService`
    * `UserService`
    * `CarConfigurationApplicationService`
* orchestration доменных операций

---

### 🔹 Persistence Layer (`persistence`)

* JPA-сущности (`entityJpa`)
* Spring Data репозитории
* кастомные репозитории (`Jpa*Repository`)
* спецификации (`CarModelSpecification`)

---

### 🔹 Infrastructure Layer (`infrastructure`)

* реализации репозиториев (in-memory для проверок)
* вспомогательная инфраструктура

---

### 🔹 Presentation Layer (`presentation`)

* REST-контроллеры:

    * `CarController`
    * `OrderController`
    * `TestRequestController`
    * `UserController`
* DTO:

    * Request / Response модели
* валидация входных данных

---

### 🔹 Mapper Layer (`mapper`)

* MapStruct:

    * DTO ↔ Domain
    * Domain ↔ JPA

---

## Роли пользователей

| Роль            | Описание                                   |
| --------------- | ------------------------------------------ |
| USER            | клиент, работает только со своими заказами |
| MANAGER         | управляет заказами                         |
| WAREHOUSE_ADMIN | управляет складом                          |
| ADMIN           | полный доступ                              |

---

## Основной функционал

### Конфигуратор автомобилей

* выбор компонентов (колёса, интерьер и т.д.)
* проверка совместимости компонентов
* расчет стоимости конфигурации
* доменная валидация

---

### Заказы

* заказ автомобиля в наличии (`ExistedCarOrder`)
* заказ с конфигурацией (`CustomOrder`)
* жизненный цикл заказа (`OrderStatus`)
* автоматическое назначение менеджера

---

### Тест-драйв

* оформление заявки (`TestRequest`)
* управление доступными авто

---

### Склад и запчасти

* учет автомобилей
* учет запчастей (`Part`)
* редактирование данных

---

## Фильтрация

Реализована через:

* **Spring Data Specifications**


Фильтры:

* бренд
* модель
* характеристики
* компоненты

---

## Тестирование

### Unit-тесты

* `CarServiceTest`
* `OrderServiceTest`
* `CarConfiguratorTest`

---

### Интеграционные тесты

* `CarControllerIT`
* `OrderControllerIT`
* `TestDriveControllerIT`
* `UserControllerIT`

Используется:

* **Testcontainers (PostgreSQL)**

Проверяется:

* REST API
* репозитории
* миграции
* Spring Context

---

## Безопасность

* Spring Security + Keycloak
* OAuth2
* role-based access control (RBAC)

### Проверка владельца заказа

```java
@PreAuthorize("#order.userId == authentication.principal.id")
```

---

## База данных

* PostgreSQL (Docker)
* Liquibase

### Базовая сущность:

```java
UUID id;
Instant createdAt;
Instant updatedAt;
boolean removed;
```

---

## REST API

Основные контроллеры:

* `/cars`
* `/orders`
* `/car-models`
* `/components`
* `/test-drives`
* `/users`

---

###  Swagger

```bash
http://localhost:8080/swagger-ui.html
```

---

## Запуск

```bash
docker-compose up --build
```

---

## Структура проекта

```bash
src/main/java/org/autosalon
├── application
├── domain
├── infrastructure
├── mapper
├── persistence
└── presentation
```

---

## Эволюция проекта

| Этап | Описание                   |
| - | -------------------------- |
| 1 | DDD, in-memory репозитории |
| 2 | Spring Boot, JPA, PostgreSQL |
| 3 | Security + Keycloak        |
| 4 | В процессе — микросервисы  |

---

## Планы (этап 4)

* разделение на:

    * OrderService
    * StorageService
* брокер сообщений (Kafka / RabbitMQ)
* Outbox Pattern
* асинхронная обработка заказов

---

## Что демонстрирует проект

* DDD-подход
* многослойную архитектуру
* работу с JPA и спецификациями
* безопасный REST API
* интеграцию с Keycloak
* тестирование (unit + integration)



