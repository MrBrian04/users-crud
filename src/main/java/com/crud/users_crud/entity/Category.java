// Paquete donde viven las entidades JPA.
package com.crud.users_crud.entity;

// Importa anotaciones JPA para mapear la entidad.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importa anotaciones de validación de Jakarta Bean Validation.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// Importa Lombok para reducir boilerplate.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

// Marca esta clase como entidad JPA.
@Entity
// Genera getters para todos los campos (de Lombok). Seguro con JPA.
@Getter
// Genera setters para todos los campos (de Lombok). Seguro con JPA.
@Setter
// Especifica el nombre de la tabla en la BD.
@Table(name = "categories")
// Genera constructor vacio requerido por JPA.
@NoArgsConstructor
// Genera constructor con todos los campos.
@AllArgsConstructor
// Habilita el patron Builder.
@Builder
public class Category {

    // Clave primaria de la tabla.
    @Id
    // ID autogenerado por la BD.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la categoria, obligatorio.
    // @NotBlank: No nulo, no vacío y no solo espacios.
    // @Size(min=2, max=100): Valida longitud del nombre.
    @NotBlank(message = "El nombre de la categoría es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Descripcion opcional de la categoria.
    // @Size: Si se proporciona descripción, debe cumplir tamaño máximo.
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Column(name = "description", length = 255)
    private String description;

}
