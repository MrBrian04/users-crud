package com.crud.users_crud.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
// Genera getters para todos los campos (de Lombok). Seguro con JPA y relaciones futuras.
@Getter
// Genera setters para todos los campos (de Lombok). Seguro con JPA y relaciones futuras.
@Setter
// Genera constructor vacio requerido por JPA.
@NoArgsConstructor
// Genera constructor con todos los campos.
@AllArgsConstructor
// Habilita el patron Builder para crear instancias.
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido") // NotBlank es mejor para Strings
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 5, max = 20, message = "El teléfono debe tener entre 5 y 20 caracteres") // CORREGIDO
    @Column(nullable = false, length = 20)
    private String phone;

    @NotNull(message = "El estado es requerido")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}