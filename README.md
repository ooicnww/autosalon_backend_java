# Car Dealership Backend System

Backend-приложение для мультибрендового автосалона с поддержкой:

* конфигуратора автомобилей;
* оформления заказов;
* управления складом и автомобилями;
* ролевой модели доступа;
* микросервисного взаимодействия;
* асинхронной обработки бизнес-событий.

Проект демонстрирует применение:

* DDD (Domain-Driven Design);
* луковой архитектуры;
* микросервисной архитектуры;
* event-driven подхода;
* современных backend-технологий Java/Spring.

---

# Технологии

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* PostgreSQL
* Liquibase
* Keycloak
* Apache Kafka
* Testcontainers
* Docker
* MapStruct
* Gradle
* JUnit 5

---

# Архитектура проекта

Проект построен на основе многослойной архитектуры и DDD.

## Domain Layer (`domain`)

Содержит:

* бизнес-сущности:

  * `Car`
  * `Order`
  * `CarModel`
  * `User`
  * `Part`
* value objects:

  * `ModelKey`
* enum'ы:

  * `FuelType`
  * `TransmissionType`
  * `DriveType`
  * `OrderStatus`
* доменные исключения:

  * `DomainValidationException`
  * `EntityNotFoundException`
  * `NotSuitableComponentException`

Domain Layer инкапсулирует бизнес-правила системы.

---

## Application Layer (`application`)

Содержит application services:

* `CarService`
* `OrderService`
* `UserService`
* `CarConfigurationApplicationService`

Responsibilities:

* orchestration бизнес-операций;
* coordination domain logic;
* работа со сценариями приложения.

---

## Persistence Layer (`persistence`)

Содержит:

* JPA entities (`entityJpa`);
* Spring Data repositories;
* кастомные JPA-репозитории;
* Specifications для фильтрации.

Примеры:

* `JpaOrderRepository`
* `JpaCarRepository`
* `CarModelSpecification`

---

## Infrastructure Layer (`infrastructure`)

Содержит:

* Kafka listeners/publishers;
* Outbox Pattern;
* инфраструктурные сервисы;
* интеграцию между микросервисами.

---

## Presentation Layer (`presentation`)

REST API приложения.

Контроллеры:

* `CarController`
* `OrderController`
* `UserController`
* `TestRequestController`

Используются:

* DTO;
* request/response модели;
* validation annotations.

---

## Mapper Layer (`mapper`)

Используется MapStruct для:

* DTO ↔ Domain;
* Domain ↔ JPA.

---

# Микросервисная архитектура 

Система разделена на два микросервиса.

| Сервис           | Ответственность                                  |
| ---------------- | ------------------------------------------------ |
| `OrderService`   | заказы, пользователи, бизнес-процессы оформления |
| `StorageService` | склад, автомобили, конфигурации, сборка          |

---

# Асинхронное взаимодействие

Для межсервисного взаимодействия используется:

* Apache Kafka;
* event-driven architecture;
* Outbox Pattern.

---

## Реализованные события

### OrderService → StorageService

```text
OrderSentForApprovalEvent
```

Отправляется после оформления заказа.

---

### StorageService → OrderService

```text
OrderApprovedEvent
OrderRejectedEvent
```

Используются для подтверждения или отклонения заказа после проверки склада.

---

# Outbox Pattern

Реализован паттерн гарантированной доставки сообщений.

Особенности:

* события сохраняются в таблицу `outbox_events`;
* publisher асинхронно публикует события в Kafka;
* реализована идемпотентность обработки;
* предотвращается потеря сообщений при сбоях.

---

# Роли пользователей

| Роль            | Описание            |
| --------------- | ------------------- |
| USER            | клиент              |
| MANAGER         | управление заказами |
| WAREHOUSE_ADMIN | управление складом  |
| ADMIN           | полный доступ       |

---

# Основной функционал

## Конфигуратор автомобилей

Поддерживает:

* выбор компонентов;
* проверку совместимости;
* расчет стоимости;
* доменную валидацию.

---

## Заказы

Поддерживаются:

* заказ автомобиля в наличии (`ExistedCarOrder`);
* заказ кастомной конфигурации (`CustomOrder`);
* жизненный цикл заказа;
* автоматическое назначение менеджера.

---

## Склад

Реализовано:

* управление автомобилями;
* учет доступности автомобилей;
* управление компонентами;
* управление конфигурациями.

---

# Фильтрация

Используются:

* Spring Data Specifications.

Поддерживаются фильтры:

* бренд;
* модель;
* характеристики;
* компоненты;
* тип топлива;
* трансмиссия.

---

# Безопасность

Используются:

* Spring Security;
* Keycloak;
* OAuth2;
* RBAC.

---

## Пример проверки доступа

```java
@PreAuthorize("#order.userId == authentication.principal.id")
```

---

# База данных

Используется:

* PostgreSQL;
* Liquibase migrations.

---

## Базовая сущность

```java
UUID id;
Instant createdAt;
Instant updatedAt;
boolean removed;
```

---

# REST API

Основные endpoints:

```http
/cars
/orders
/car-models
/components
/test-drives
/users
```

---

# Swagger

```http
http://localhost:8080/swagger-ui.html
```

---

# Тестирование

## Unit-тесты

Реализованы:

* `CarServiceTest`
* `OrderServiceTest`
* `CarConfiguratorTest`

---

## Интеграционные тесты

Реализованы:

* `CarControllerIT`
* `OrderControllerIT`
* `UserControllerIT`
* Kafka integration tests
* Outbox integration tests

---

## Используется

* Testcontainers;
* PostgreSQL Container;
* Kafka Container.

---

## Проверяется

* REST API;
* Kafka messaging;
* listeners;
* Outbox processing;
* миграции;
* Spring Context;
* взаимодействие микросервисов.

---

# Запуск проекта

## Docker Compose

```bash
docker-compose up --build
```

---

# Структура проекта

```bash
order-service/
storage-service/
```

---

# Эволюция проекта

| Этап | Описание                                  |
| ---- | ----------------------------------------- |
| 1  | DDD, domain model, in-memory repositories |
| 2  | Spring Boot, REST API, PostgreSQL, JPA    |
| 3  | Spring Security, Keycloak, RBAC           |
| 4  | Микросервисы, Kafka, Outbox Pattern       |

---

# Планы развития

В следующем этапе планируется добавить синхронное межсервисное взаимодействие через gRPC.

---

# Планируемая архитектура 

## StorageService

Планируется:

* реализовать gRPC server;
* предоставлять список автомобилей в наличии;
* возвращать только доступные автомобили.

---

## OrderService

Планируется:

* реализовать gRPC client;
* получать список автомобилей через gRPC;
* предоставлять REST API поверх gRPC.

---

# Планируемые REST endpoints

```http
GET /api/v1/cars
GET /api/v1/cars/{id}
```

---

# Надежность

Планируется:

* timeout для gRPC client;
* обработка `503 Service Unavailable`;
* базовая отказоустойчивость межсервисных вызовов.

---

# Планируемые тесты

* integration tests для gRPC server;
* integration tests для gRPC client;
* timeout tests;
* service unavailable tests.

---

# Что демонстрирует проект

Проект демонстрирует:

* DDD-подход;
* луковую архитектуру;
* микросервисную архитектуру;
* event-driven communication;
* Kafka integration;
* Outbox Pattern;
* Spring Security + Keycloak;
* безопасный REST API;
* интеграционное тестирование через Testcontainers.

