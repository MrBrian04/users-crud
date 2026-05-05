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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// Importa Lombok para reducir boilerplate.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Importa BigDecimal para manejar dinero con precision.
import java.math.BigDecimal;

// Marca esta clase como entidad JPA.
@Entity
// Especifica el nombre de la tabla en la BD.
@Table(name = "products")
// Genera getters, setters, equals, hashCode y toString.
@Data
// Genera constructor vacio requerido por JPA.
@NoArgsConstructor
// Genera constructor con todos los campos.
@AllArgsConstructor
// Habilita el patron Builder.
@Builder
public class Product {

    // Clave primaria de la tabla.
    @Id
    // ID autogenerado por la BD.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto, obligatorio.
    // @NotNull: Nombre requerido.
    // @NotEmpty: Nombre no puede estar vacío.
    // @Size(min=2, max=100): Valida longitud del nombre.
    @NotNull(message = "El nombre del producto es requerido")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Descripcion opcional del producto.
    // @Size: Si se proporciona descripción, debe cumplir tamaño máximo.
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Column(name = "description", length = 255)
    private String description;

    // Precio obligatorio con precision decimal.
    // @NotNull: Precio requerido.
    // @DecimalMin("0.01"): Precio mínimo debe ser mayor a 0.
    // @DecimalMax("999999.99"): Precio máximo permitido.
    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @DecimalMax(value = "999999.99", message = "El precio no puede exceder 999999.99")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
