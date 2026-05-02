package com.crud.users_crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity                          // (1) Marca esta clase como entidad JPA
@Table(name = "products")           // (2) Especifica el nombre de la tabla en BD
@Data                            // (3) Lombok: genera getters, setters, equals, hashCode, toString
@NoArgsConstructor               // (4) Lombok: genera constructor vacío (requerido por JPA)
@AllArgsConstructor              // (5) Lombok: genera constructor con todos los campos
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
