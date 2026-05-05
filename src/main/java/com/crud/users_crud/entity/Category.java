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

// Marca esta clase como entidad JPA.
@Entity
// Genera getters, setters, equals, hashCode y toString.
@Data
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
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Descripcion opcional de la categoria.
    @Column(name = "description", length = 255)
    private String description;

}
