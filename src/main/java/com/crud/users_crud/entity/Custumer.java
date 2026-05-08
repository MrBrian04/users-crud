package com.crud.users_crud.entity;

// Importa anotaciones JPA para mapeo a base de datos.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa anotaciones de validación de Jakarta Bean Validation.
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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

// Importa BigDecimal para precisión en valores monetarios.
import java.math.BigDecimal;

// Marca esta clase como entidad JPA (se persiste en BD).
@Entity
// Especifica el nombre de la tabla en la BD.
@Table(name = "customers")
// Genera getters para todos los campos (Lombok, seguro con JPA).
@Getter
// Genera setters para todos los campos (Lombok, seguro con JPA).
@Setter
// Genera constructor con todos los campos.
@AllArgsConstructor
// Genera constructor sin argumentos (JPA lo necesita).
@NoArgsConstructor
// Habilita el patrón Builder para crear instancias.
@Builder
public class Custumer {

    // Clave primaria de la tabla.
    @Id
    // ID auto-generado por la BD (estrategia IDENTITY: auto-incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre completo del cliente, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios en blanco.
    // @Size: Valida que el nombre tenga entre 3 y 100 caracteres.
    @NotBlank(message = "El nombre del cliente es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Teléfono del cliente, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Size: Valida que el teléfono tenga entre 4 y 12 caracteres (rango típico).
    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 4, max = 12, message = "El teléfono debe tener entre 4 y 12 caracteres")
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    // Email del cliente, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Email: Valida que sea un formato de email válido (ej: usuario@dominio.com).
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    // Dirección del cliente, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Size: Valida que la dirección tenga entre 5 y 200 caracteres.
    @NotBlank(message = "La dirección es requerida")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    @Column(name = "address", nullable = false, length = 200)
    private String address;

    // Saldo (balance) del cliente, obligatorio.
    // @NotNull: No puede ser nulo (único validator válido para números).
    // @DecimalMin: Saldo mínimo debe ser 0.00 (no permite negativos).
    // @DecimalMax: Saldo máximo permitido es 999999.99.
    // precision=10, scale=2: En BD almacena con 10 dígitos totales y 2 decimales (ej: 99999999.99).
    @NotNull(message = "El saldo es requerido")
    @DecimalMin(value = "0.00", message = "El saldo debe ser mayor o igual a 0")
    @DecimalMax(value = "999999.99", message = "El saldo no puede exceder 999999.99")
    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    // Estado activo/inactivo del cliente, obligatorio.
    // @NotNull: No puede ser nulo (un cliente debe estar activo o inactivo).
    // true = cliente activo, false = cliente inactivo/suspendido.
    @NotNull(message = "El estado del cliente es requerido")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;






}
