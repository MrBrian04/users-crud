# users-crud

Proyecto Spring Boot con CRUD **solo de usuarios** usando Spring Web, Spring Data JPA y MySQL.

## Arquitectura hexagonal (ports & adapters)

1. Dominio: modelo `User`, validaciones y puertos (in/out).
2. Aplicacion: `UserService` implementa `UserUseCase` y orquesta transacciones.
3. Infraestructura: adaptadores web y persistencia, mappers, configuraciones.
4. Manejo de excepciones: handlers HTTP (nota: falta `@ControllerAdvice` en el handler global).

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

Nota: endpoints de productos/categorias/proveedores no estan implementados en este repo.

## Manejo de errores

- `DomainException`: errores de validacion de datos o reglas de negocio.
- `UserNotFoundException`: recurso no encontrado.
- `Exception`: errores no controlados.

El `GlobalExceptionHandler` traduce estas excepciones a respuestas HTTP con el status adecuado cuando esta registrado como `@ControllerAdvice`.

## Configuracion

Los perfiles activos se definen en `application.yaml`. No existe `application-dev.yaml` en este proyecto; si lo necesitas, puedes crearlo.

## Dependencias principales

Spring Boot Web MVC, Spring Data JPA, MySQL Connector, MapStruct y Lombok.

## Contenido anterior (desactualizado, conservado como referencia)

> Proyecto Spring Boot con CRUD para usuarios, productos, categorias y proveedores usando Spring Web, Spring Data JPA y MySQL.
>
> ## Arquitectura en capas (simple)
>
> 1. Entidad (JPA): modelos persistentes que mapean tablas.
> 2. Repositorio: acceso a datos y consultas personalizadas.
> 3. Servicio: reglas de negocio y orquestacion.
> 4. Controller: endpoints HTTP.
> 5. Manejo de excepciones: respuestas HTTP coherentes.
>
> Validaciones: se realizan de forma nativa en las entidades (constructores y setters) y en los servicios para reglas de negocio. No se usa Bean Validation.
>
> ## Estructura del proyecto
>
> - src/main/java/com/crud/users_crud/UsersCrudApplication.java: punto de entrada.
> - src/main/java/com/crud/users_crud/controller: controllers REST.
> - src/main/java/com/crud/users_crud/service: logica de negocio.
> - src/main/java/com/crud/users_crud/repository: repositorios JPA.
> - src/main/java/com/crud/users_crud/entity: entidades JPA (User, Product, Category, Supplier, Custumer).
> - src/main/java/com/crud/users_crud/exception: excepciones y GlobalExceptionHandler.
> - src/main/java/com/crud/users_crud/config: configuraciones (CORS, etc).
>
> ## Endpoints disponibles
>
> Usuarios
> - POST /api/users
> - GET /api/users
> - GET /api/users/{id}
> - PUT /api/users/{id}
> - DELETE /api/users/{id}
>
> Productos
> - POST /api/products
> - GET /api/products
> - GET /api/products/{id}
> - PUT /api/products/{id}
> - DELETE /api/products/{id}
>
> Categorias
> - POST /api/categories
> - GET /api/categories
> - GET /api/categories/{id}
> - PUT /api/categories/{id}
> - DELETE /api/categories/{id}
>
> Proveedores
> - POST /api/suppliers
> - GET /api/suppliers
> - GET /api/suppliers/{id}
> - PUT /api/suppliers/{id}
> - DELETE /api/suppliers/{id}
>
> ## Manejo de errores
>
> - ValidationException: errores de validacion de datos o reglas de negocio.
> - RuntimeException: casos no encontrados u otros errores no controlados.
>
> El GlobalExceptionHandler traduce estas excepciones a respuestas HTTP con el status adecuado.
>
> ## Configuracion
>
> Los perfiles activos se definen en application.yaml y application-dev.yaml. Ajusta las propiedades de la base de datos segun tu entorno local.
>
> ## Dependencias principales
>
> Spring Boot Web, Spring Data JPA, MySQL Connector y Lombok (para anotaciones de conveniencia en capas no criticas).
