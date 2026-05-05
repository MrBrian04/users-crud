// Paquete donde viven las entidades JPA.
package com.crud.users_crud.entity;

// Importa anotaciones JPA para mapear la entidad.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Descripcion opcional del producto.
    @Column(name = "description", length = 255)
    private String description;

    // Precio obligatorio con precision decimal.
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
