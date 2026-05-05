# users-crud

Proyecto Spring Boot con tres CRUD (users, products, categories) usando Spring Web, Spring Data JPA, MySQL y validaciones.

## Arquitectura: Las 5 Capas

```
┌─────────────────────────────────────────────────────────────┐
│ 1. ENTIDAD (JPA + Validaciones)                             │
│    Entity → mapea a tabla BD + anotaciones de validación    │
├─────────────────────────────────────────────────────────────┤
│ 2. REPOSITORIO (Spring Data JPA)                           │
│    Repository → operaciones CRUD automáticas               │
├─────────────────────────────────────────────────────────────┤
│ 3. SERVICIO (Lógica de Negocio)                           │
│    Service → validaciones, reglas, orquestación            │
├─────────────────────────────────────────────────────────────┤
│ 4. CONTROLLER (REST HTTP)                                  │
│    Controller → HTTP endpoints, @Valid en POST/PUT         │
├─────────────────────────────────────────────────────────────┤
│ 5. MANEJADOR DE EXCEPCIONES (GlobalExceptionHandler)       │
│    Config → intercepta errores → 404/400/500 + mensaje     │
└─────────────────────────────────────────────────────────────┘
```

## Flujo de una Entidad hasta el CRUD

1. **Entidad (JPA)**: estructura de datos + validaciones (`@NotNull`, `@Email`, `@Size`, etc).
2. **Repositorio**: operaciones CRUD automáticas + queries custom (ex: `findByEmail`).
3. **Servicio**: aplica reglas de negocio, valida, orquesta, usa repositorio.
4. **Controller**: expone endpoints HTTP, valida entrada con `@Valid`, convierte JSON <-> objeto.
5. **GlobalExceptionHandler**: intercepta excepciones y las convierte en respuestas HTTP limpias (404, 400, 500).

## Estructura del Proyecto

```
src/main/java/com/crud/users_crud/
├── UsersCrudApplication.java          ← Punto de entrada Spring Boot
├── config/
│   └── GlobalExceptionHandler.java    ← Interceptor global de errores
├── entity/
│   ├── User.java                      ← Validaciones: @NotNull, @Email, @Min/@Max
│   ├── Product.java                   ← Validaciones: @DecimalMin/@DecimalMax
│   └── Category.java                  ← Validaciones: @Size
├── repository/
│   ├── UserRepository.java            ← findByEmail() custom
│   ├── ProductRepository.java         ← findByName() custom
│   └── CategoryRepository.java        ← findByName() custom
├── service/
│   ├── UserService.java               ← CRUD + validación de email único
│   ├── ProductService.java            ← CRUD + validación de nombre único
│   └── CategoryService.java           ← CRUD + validación de nombre único
└── controller/
    ├── UserController.java            ← @Valid en @PostMapping/@PutMapping
    ├── ProductController.java         ← @Valid en @PostMapping/@PutMapping
    └── CategoryController.java        ← @Valid en @PostMapping/@PutMapping
```

## Validaciones en 3 Niveles

### 1. Anotaciones en Entidades (Validación de Tipos)

```java
// User.java
@NotNull @NotEmpty @Size(min=2, max=100)
private String name;

@NotNull @NotEmpty @Email
private String email;

@Min(1) @Max(120)
private Integer age;
```

**User, Product y Category** tienen validaciones completas.

### 2. Validación Manual en Servicios (Reglas de Negocio)

```java
// UserService.create()
if (userRepository.findByEmail(user.getEmail()).isPresent()) {
    throw new IllegalArgumentException("Ya existe un usuario con el email: " + email);
}
```

### 3. Manejo Global de Errores (GlobalExceptionHandler)

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Captura RuntimeException (no encontrado)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
    
    // Captura IllegalArgumentException (validaciones fallidas)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    
    // Captura MethodArgumentNotValidException (anotaciones @NotNull, @Email, etc)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
```

## Códigos HTTP Devueltos

| Operación | Status | Significado |
|-----------|--------|-------------|
| Crear usuario válido | **201** | Created (éxito) |
| Obtener usuario existente | **200** | OK |
| Actualizar usuario existente | **200** | OK |
| Eliminar usuario existente | **204** | No Content (éxito sin body) |
| Obtener/Actualizar/Eliminar usuario NO EXISTE | **404** | Not Found + mensaje |
| Crear con email duplicado | **400** | Bad Request + mensaje |
| Crear con nombre vacío | **400** | Bad Request + mensaje |
| Crear con edad > 120 | **400** | Bad Request + mensaje |
| Error de conexión BD | **500** | Internal Server Error |

## Conceptos y Palabras Clave

### Validación
- **`@Valid`**: en controller, valida entidad antes de procesarla
- **`@NotNull`**: campo no puede ser null
- **`@NotEmpty`**: string no puede estar vacío
- **`@Email`**: valida formato email
- **`@Size(min, max)`**: rango de longitud
- **`@Min / @Max`**: rango de números
- **`@DecimalMin / @DecimalMax`**: rango de decimales (precios)

### JPA & Persistencia
- **`@Entity`**: marca clase como persistente
- **`@Table`**: nombre de tabla en BD
- **`@Id / @GeneratedValue`**: clave primaria auto-increment
- **`@Column`**: detalles de columna (nullable, unique, length)
- **`JpaRepository`**: interfaz CRUD automática

### Spring Framework
- **`@Service`**: capa de lógica de negocio
- **`@Transactional`**: agrupa operaciones en transacción (atomicidad)
- **`@RestController`**: controller REST (devuelve JSON)
- **`@RequestMapping / @GetMapping / @PostMapping`**: mapeo HTTP
- **`ResponseEntity`**: controla status + body HTTP
- **`@RestControllerAdvice`**: interceptor global de excepciones
- **`@ExceptionHandler`**: define qué excepción maneja cada método

## Endpoints Disponibles

### Users
- `POST /api/users` → crear (requiere: name, email, age válidos)
- `GET /api/users` → listar todos
- `GET /api/users/{id}` → obtener por ID
- `PUT /api/users/{id}` → actualizar
- `DELETE /api/users/{id}` → eliminar

### Products
- `POST /api/products` → crear (requiere: name, price > 0)
- `GET /api/products` → listar todos
- `GET /api/products/{id}` → obtener por ID
- `PUT /api/products/{id}` → actualizar
- `DELETE /api/products/{id}` → eliminar

### Categories
- `POST /api/categories` → crear (requiere: name)
- `GET /api/categories` → listar todos
- `GET /api/categories/{id}` → obtener por ID
- `PUT /api/categories/{id}` → actualizar
- `DELETE /api/categories/{id}` → eliminar

## Ejemplos de Prueba

### ✅ Crear User Válido
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan",
    "email": "juan@example.com",
    "age": 30
  }'
# Respuesta: 201 Created
```

### ❌ Crear User con Email Inválido
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan", "email":"invalido", "age":30}'
# Respuesta: 400 Bad Request
# email: El email debe ser válido
```

### ❌ Crear User con Edad Fuera de Rango
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan", "email":"juan@ex.com", "age":150}'
# Respuesta: 400 Bad Request
# age: La edad debe ser menor a 120
```

### ❌ Crear User con Email Duplicado
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan2", "email":"juan@example.com", "age":25}'
# Respuesta: 400 Bad Request (existe otro user con ese email)
# Ya existe un usuario con el email: juan@example.com
```

### ❌ Eliminar User que NO Existe
```bash
curl -X DELETE http://localhost:8080/api/users/999
# Respuesta: 404 Not Found
# Usuario no encontrado con ID: 999
```

### ✅ Obtener User por ID
```bash
curl http://localhost:8080/api/users/1
# Respuesta: 200 OK + JSON con datos del user
```

## Configuración Local

`src/main/resources/application.yaml` usa MySQL local:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tu_base_datos
    username: root
    password: ${password}  # Define como variable de entorno o reemplaza manualmente
  jpa:
    hibernate:
      ddl-auto: update  # Crea/actualiza tablas automáticamente
```

### Opción 1: Variable de Entorno (PowerShell)
```powershell
$env:password="TU_PASSWORD"
./gradlew bootRun
```

### Opción 2: Reemplazar en YAML
```yaml
password: tu_password_aqui
```

## Levantar la Aplicación

```powershell
# Compilar
./gradlew clean build -x test

# Ejecutar
./gradlew bootRun
```

La app estará en `http://localhost:8080`.

## Payloads de Ejemplo

### User (validaciones)
```json
{
  "name": "Ana",
  "email": "ana@example.com",
  "age": 28
}
```
- `name`: 2-100 caracteres, requerido
- `email`: formato válido, requerido
- `age`: entre 1 y 120, opcional

### Product (validaciones)
```json
{
  "name": "Laptop Dell",
  "description": "14 pulgadas Full HD",
  "price": 899.99
}
```
- `name`: 2-100 caracteres, requerido, único
- `description`: máx 255 caracteres, opcional
- `price`: entre 0.01 y 999999.99, requerido

### Category (validaciones)
```json
{
  "name": "Electronics",
  "description": "Dispositivos electrónicos"
}
```
- `name`: 2-100 caracteres, requerido, único
- `description`: máx 255 caracteres, opcional

## Flujo Completo de una Petición

```
1. Cliente envía JSON POST /api/users
                ↓
2. @Valid valida anotaciones (@NotNull, @Email, etc)
                ↓
3. Si falla: GlobalExceptionHandler → 400 + mensaje
                ↓
4. Si OK: Controller → Service
                ↓
5. Service valida negocio (email duplicado)
                ↓
6. Si falla: GlobalExceptionHandler → 400 + mensaje
                ↓
7. Si OK: Repository → INSERT en BD
                ↓
8. Devuelve 201 Created + JSON del User creado
```

## Dependencias Principales

```gradle
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-webmvc'
implementation 'org.springframework.boot:spring-boot-starter-validation'  // ← Validaciones
runtimeOnly 'com.mysql:mysql-connector-j'
compileOnly 'org.projectlombok:lombok'
```

