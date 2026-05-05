# users-crud

Proyecto Spring Boot con tres CRUD (users, products, categories) usando Spring Web, Spring Data JPA y MySQL.

## Flujo general (de una entidad hasta el CRUD)

1. **Entidad (JPA)**: define la estructura de datos y el mapeo a tabla. Ej: `User` -> tabla `users`.
2. **Repositorio (Spring Data JPA)**: provee operaciones CRUD y consultas derivadas por nombre. Ej: `UserRepository.findByEmail`.
3. **Servicio**: aplica reglas de negocio, valida, orquesta y usa repositorio. Ej: `UserService.create`.
4. **Controller (REST)**: expone endpoints HTTP, convierte JSON <-> objeto, devuelve status HTTP.
5. **Configuracion**: `application.yaml` define la BD, JPA y el puerto.

## Estructura principal

- `src/main/java/com/crud/users_crud/UsersCrudApplication.java`: punto de arranque.
- `src/main/java/com/crud/users_crud/entity/*`: entidades JPA (User, Product, Category).
- `src/main/java/com/crud/users_crud/repository/*`: repositorios JPA.
- `src/main/java/com/crud/users_crud/service/*`: logica de negocio.
- `src/main/java/com/crud/users_crud/controller/*`: endpoints REST.
- `src/main/resources/application.yaml`: configuracion de la app.

## Endpoints (resumen)

Base paths:
- `/api/users`
- `/api/products`
- `/api/categories`

Operaciones:
- `POST /` crear
- `GET /` listar
- `GET /{id}` obtener por id
- `PUT /{id}` actualizar
- `DELETE /{id}` eliminar

## Conceptos y palabras clave

- **@Entity**: marca una clase como entidad persistente en JPA.
- **@Table**: define el nombre de la tabla en la BD.
- **@Id / @GeneratedValue**: define la clave primaria y su estrategia.
- **@Column**: configura detalles de columna (nullable, length, unique, etc.).
- **JpaRepository**: interfaz base con CRUD listo para usar.
- **Query methods**: metodos como `findByEmail` o `findByName` generan SQL automaticamente.
- **@Service**: capa de negocio; centraliza validaciones y reglas.
- **@Transactional**: agrupa operaciones en una transaccion (atomicidad).
- **@RestController**: controller REST que devuelve JSON.
- **@RequestMapping / @GetMapping / @PostMapping**: mapeo de rutas HTTP.
- **ResponseEntity**: permite controlar status y body HTTP.

## Configuracion local (BD)

`application.yaml` usa MySQL local y una variable `${password}`. Define la variable de entorno `password` antes de levantar la app o reemplazala en el YAML.

## Probar rapidamente

1) Ejecutar tests:

```powershell
./gradlew test
```

2) Levantar la app:

```powershell
./gradlew bootRun
```

## Ejemplos de payloads

User:

```json
{
  "name": "Ana",
  "email": "ana@example.com",
  "age": 28
}
```

Product:

```json
{
  "name": "Laptop",
  "description": "14 pulgadas",
  "price": 999.99
}
```

Category:

```json
{
  "name": "Electronics",
  "description": "Dispositivos electronicos"
}
```

