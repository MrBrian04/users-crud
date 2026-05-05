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
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Mapea el email; debe ser unico y no nulo.
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    // Mapea la edad; puede ser nula si no se provee.
    @Column(name = "age")
    private Integer age;
}
