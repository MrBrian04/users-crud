package com.crud.users_crud.entity;

import com.crud.users_crud.exception.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad JPA para categorias.
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    // Constructor vacio requerido por JPA.
    public Category() {
    }

    public Category(String name, String description) {
        this.name = validateName(name);
        this.description = validateDescription(description);
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

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre de la categoria es requerido");
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
}
