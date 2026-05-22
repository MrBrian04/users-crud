package com.crud.users_crud.entity;

import com.crud.users_crud.exception.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

// Entidad JPA para productos.
@Entity
@Table(name = "products")
public class Product {

    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Constructor vacio requerido por JPA.
    public Product() {
    }

    public Product(String name, String description, BigDecimal price) {
        this.name = validateName(name);
        this.description = validateDescription(description);
        this.price = validatePrice(price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = validateDescription(description);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = validatePrice(price);
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre del producto es requerido");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new ValidationException("El nombre debe tener entre 2 y 100 caracteres");
        }
        return name;
    }

    private String validateDescription(String description) {
        if (description != null && description.length() > 255) {
            throw new ValidationException("La descripcion no puede exceder 255 caracteres");
        }
        return description;
    }

    private BigDecimal validatePrice(BigDecimal price) {
        if (price == null) {
            throw new ValidationException("El precio es requerido");
        }
        if (price.compareTo(MIN_PRICE) < 0) {
            throw new ValidationException("El precio debe ser mayor a 0");
        }
        if (price.compareTo(MAX_PRICE) > 0) {
            throw new ValidationException("El precio no puede exceder 999999.99");
        }
        return price;
    }
}
