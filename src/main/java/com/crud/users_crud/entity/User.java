package com.crud.users_crud.entity;


// Mapea directamente a una tabla de la base de datos. No tiene lógica de negocio.
// Solo estructura de datos + anotaciones JPA.

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity                          // (1) Marca esta clase como entidad JPA
@Table(name = "users")           // (2) Especifica el nombre de la tabla en BD
@Data                            // (3) Lombok: genera getters, setters, equals, hashCode, toString
@NoArgsConstructor               // (4) Lombok: genera constructor vacío (requerido por JPA)
@AllArgsConstructor              // (5) Lombok: genera constructor con todos los campos
@Builder                         // (6) Lombok: habilita el patrón Builder para construir instancias
public class User {


    @Id                                                    // (7) Marca este campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // (8) Auto-increment delegado a la BD
    private Long id;

    @Column(name = "name", nullable = false, length = 100) // (9) Mapea a columna con restricciones
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "age")
    private Integer age;
}
