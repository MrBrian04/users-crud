# PLAN DE REFACTORIZACIÓN SIMPLE - Desacoplamiento de Bean Validation

## Objetivo
Eliminar la dependencia de **Jakarta Bean Validation** de las entidades del dominio, manteniendo la arquitectura en capas actual, usando validaciones nativas de Java.

---

## 1. CAMBIOS REQUERIDOS

### Paso 1: Crear excepciones del dominio
**Ubicación:** `domain/exception/` (nueva carpeta)

```java
// domain/exception/DomainException.java
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}

// domain/exception/ValidationException.java
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}
```

### Paso 2: Limpiar cada entidad

**Eliminar:**
```java
❌ @NotBlank, @Email, @Min, @Max, @Size
❌ @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder (Lombok)
❌ import jakarta.validation.*
```

**Agregar:**
```java
✅ Validaciones nativas en constructor
✅ Métodos getter/setter públicos (código nativo)
✅ Constructor sin argumentos
✅ Constructor con argumentos
✅ Las anotaciones JPA (@Entity, @Table, @Column, @Id, @GeneratedValue) SE MANTIENEN
```

**Ejemplo User (ANTES):**
```java
@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 120, message = "La edad debe ser menor a 120")
    @Column(name = "age")
    private Integer age;
}
```

**Ejemplo User (DESPUÉS):**
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "age")
    private Integer age;

    // Constructor vacío (para JPA)
    public User() {}

    // Constructor con validación
    public User(String name, String email, Integer age) throws ValidationException {
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.age = validateAge(age);
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }

    // Setters con validación
    public void setId(Long id) { this.id = id; }
    
    public void setName(String name) throws ValidationException {
        this.name = validateName(name);
    }
    
    public void setEmail(String email) throws ValidationException {
        this.email = validateEmail(email);
    }
    
    public void setAge(Integer age) throws ValidationException {
        this.age = validateAge(age);
    }

    // Métodos de validación privados
    private String validateName(String name) throws ValidationException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre es requerido");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new ValidationException("El nombre debe tener entre 2 y 100 caracteres");
        }
        return name;
    }

    private String validateEmail(String email) throws ValidationException {
        if (email == null || email.isBlank()) {
            throw new ValidationException("El email es requerido");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("El email debe ser válido");
        }
        return email;
    }

    private Integer validateAge(Integer age) throws ValidationException {
        if (age != null && (age < 1 || age > 120)) {
            throw new ValidationException("La edad debe estar entre 1 y 120");
        }
        return age;
    }
}
```

### Paso 3: Actualizar el servicio

**Cambios en UserService:**

```java
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) throws ValidationException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ValidationException("Ya existe un usuario con el email: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    // ... resto de métodos igual
}
```

### Paso 4: Actualizar el controlador

**Cambios en UserController:**

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) throws ValidationException {
        // Ya no hay @Valid aquí, la validación ocurre en el constructor del User
        User created = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ... resto de métodos
}
```

### Paso 5: Actualizar GlobalExceptionHandler

**Nuevo handler para ValidationException:**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
```

---

## 2. RESUMEN DE CAMBIOS

| Aspecto | Antes | Después |
|---------|-------|---------|
| **Validación** | Bean Validation (@NotBlank, @Email) | Métodos nativos en constructor |
| **Excepciones** | @Valid en controlador | ValidationException en dominio |
| **Dependencias externas** | Jakarta Validation | Ninguna en la entidad |
| **Arquitectura** | En capas | En capas (sin cambios) |
| **Anotaciones JPA** | Se mantienen (@Entity, @Column, @Id) | Se mantienen |
| **Getters/Setters** | Lombok (@Getter, @Setter) | Código nativo Java |

---

## 3. ARCHIVOS A MODIFICAR

- [x] Crear `domain/exception/DomainException.java`
- [x] Crear `domain/exception/ValidationException.java`
- [ ] Modificar `entity/User.java`
- [ ] Modificar `entity/Product.java`
- [ ] Modificar `entity/Category.java`
- [ ] Modificar `entity/Supplier.java`
- [ ] Modificar `entity/Customer.java`
- [ ] Modificar `service/UserService.java`
- [ ] Modificar `controller/UserController.java`
- [ ] Actualizar `config/GlobalExceptionHandler.java`
- [ ] Remover dependencia de Jakarta Validation del `build.gradle`

---

## 4. VENTAJAS

✅ **Desacoplamiento**: Las entidades no dependen de librerías externas
✅ **Simplicidad**: Mantiene la arquitectura actual en capas
✅ **Control**: Validaciones explícitas y claras
✅ **Mantenimiento**: Código más portable y testeable
✅ **Flexibilidad**: Fácil cambiar lógica de validación sin tocar dependencias

---

## 5. ORDEN DE IMPLEMENTACIÓN

1. Crear excepciones del dominio (paso 1)
2. Limpiar y refactorizar cada entidad (paso 2)
3. Actualizar servicios (paso 3)
4. Actualizar controladores (paso 4)
5. Actualizar GlobalExceptionHandler (paso 5)
6. Remover Jakarta Validation del build.gradle
7. Remover importes de Lombok (@Getter, @Setter, @No
⁄AllArgsConstructor)


