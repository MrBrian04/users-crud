# users-crud

Proyecto Spring Boot con CRUD **solo de usuarios** usando Spring Web, Spring Data JPA y MySQL.

## Arquitectura hexagonal (ports & adapters)

1. Dominio: modelo `User`, validaciones y puertos (in/out).
2. Aplicacion: `UserService` implementa `UserUseCase` y orquesta transacciones.
3. Infraestructura: adaptadores web y persistencia, mappers, configuraciones.
4. Manejo de excepciones: handlers HTTP con `@ControllerAdvice`.

Validaciones: se realizan en el **dominio** (constructor y `updateWith`) y en la aplicacion para reglas como unicidad de email. No se usa Bean Validation.

## Estructura del proyecto

- `src/main/java/com/crud/users_crud/UsersCrudApplication.java`: punto de entrada.
- `src/main/java/com/crud/users_crud/application`: casos de uso (service).
- `src/main/java/com/crud/users_crud/domain`: modelo, excepciones y puertos.
- `src/main/java/com/crud/users_crud/infrastructure/adapter/in/web`: controller, DTOs y mapper web.
- `src/main/java/com/crud/users_crud/infrastructure/adapter/out/persistence`: adapter, entity, mapper y repo JPA.
- `src/main/java/com/crud/users_crud/infrastructure/config`: configuraciones (CORS, etc).
- `src/main/java/com/crud/users_crud/infrastructure/exception`: excepciones HTTP.

## Endpoints disponibles

Usuarios
- POST /api/users
- GET /api/users
- GET /api/users/{id}
- PUT /api/users/{id}
- DELETE /api/users/{id}

## Manejo de errores

- `DomainException`: errores de validacion de datos o reglas de negocio.
- `UserNotFoundException`: recurso no encontrado.
- `Exception`: errores no controlados.

El `GlobalExceptionHandler` traduce estas excepciones a respuestas HTTP con el status adecuado cuando esta registrado como `@ControllerAdvice`.

## Configuracion

Los perfiles activos se definen en `application.yaml`.

## Dependencias principales

Spring Boot Web MVC, Spring Data JPA, MySQL Connector, MapStruct y Lombok.
