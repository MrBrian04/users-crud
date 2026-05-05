// Paquete donde viven las entidades JPA.
package com.crud.users_crud.entity;

// Mapea directamente a una tabla de la base de datos. No tiene logica de negocio.
// Solo estructura de datos + anotaciones JPA.

// Importa anotaciones JPA para mapear la entidad.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa anotaciones de validación de Jakarta Bean Validation.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// Importa Lombok para reducir boilerplate.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Marca esta clase como entidad JPA (se persiste en BD).
@Entity
// Especifica el nombre de la tabla real en la BD.
@Table(name = "users")
// Genera getters, setters, equals, hashCode y toString.
@Data
// Genera constructor vacio requerido por JPA.
@NoArgsConstructor
// Genera constructor con todos los campos.
@AllArgsConstructor
// Habilita el patron Builder para crear instancias.
@Builder
public class User {

    // Marca el campo como clave primaria.
    @Id
    // Genera el ID con estrategia auto-increment en la BD.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapea el nombre a una columna no nula con longitud maxima.
    // @NotNull: El nombre no puede ser null (validación de BD).
    // @NotEmpty: El nombre no puede estar vacío (validación de BD + negocio).
    // @Size(min=2, max=100): Valida que el nombre tenga entre 2 y 100 caracteres.
    @NotNull(message = "El nombre es requerido")
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Mapea el email; debe ser unico y no nulo.
    // @NotNull: Email requerido.
    // @NotEmpty: Email no puede estar vacío.
    // @Email: Valida que sea un formato de email válido (ej: usuario@dominio.com).
    @NotNull(message = "El email es requerido")
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    // Mapea la edad; puede ser nula si no se provee.
    // @Min(1): La edad mínima es 1 año.
    // @Max(120): La edad máxima es 120 años (rango realista).
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    @Max(value = 120, message = "La edad debe ser menor a 120")
    @Column(name = "age")
    private Integer age;
}
