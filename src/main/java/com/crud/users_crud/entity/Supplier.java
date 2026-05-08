package com.crud.users_crud.entity;

// Importa anotaciones JPA para mapeo a base de datos.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa anotaciones de validación de Jakarta Bean Validation.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// Importa Lombok para reducir boilerplate.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

// Marca esta clase como entidad JPA (se persiste en BD).
@Entity
// Especifica el nombre de la tabla en la BD.
@Table(name = "suppliers")
// Genera getters para todos los campos (Lombok, seguro con JPA).
@Getter
// Genera setters para todos los campos (Lombok, seguro con JPA).
@Setter
// Genera constructor sin argumentos (JPA lo necesita).
@NoArgsConstructor
// Genera constructor con todos los campos.
@AllArgsConstructor
// Habilita el patrón Builder para crear instancias.
@Builder
public class Supplier {

    // Clave primaria de la tabla.
    @Id
    // ID auto-generado por la BD (estrategia IDENTITY: auto-incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del proveedor, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Size: Valida que el nombre tenga entre 2 y 100 caracteres.
    @NotBlank(message = "El nombre del proveedor es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Email del proveedor, obligatorio y único.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Email: Valida que sea un formato de email válido (ej: proveedor@empresa.com).
    @NotBlank(message = "El email del proveedor es requerido")
    @Email(message = "El email debe ser válido")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Teléfono del proveedor, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Size: Valida que el teléfono tenga entre 5 y 20 caracteres (rango internacional).
    @NotBlank(message = "El teléfono del proveedor es requerido")
    @Size(min = 5, max = 20, message = "El teléfono debe tener entre 5 y 20 caracteres")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    // Estado activo/inactivo del proveedor, obligatorio.
    // @NotNull: No puede ser nulo (un proveedor debe estar activo o inactivo).
    // true = proveedor activo, false = proveedor inactivo/suspendido.
    @NotNull(message = "El estado del proveedor es requerido")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}