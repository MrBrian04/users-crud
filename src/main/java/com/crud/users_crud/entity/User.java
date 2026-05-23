package com.crud.users_crud.entity;

import com.crud.users_crud.exception.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad JPA para usuarios.
@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "correo_electronico", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "edad")
    private Integer age;

    // Constructor vacio requerido por JPA.
    public User() {
    }

    public User(String name, String email, Integer age) {
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.age = validateAge(age);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = validateAge(age);
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre es requerido");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new ValidationException("El nombre debe tener entre 2 y 100 caracteres");
        }
        return name;
    }

    private String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("El email es requerido");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("El email debe ser valido");
        }
        return email;
    }

    private Integer validateAge(Integer age) {
        if (age != null && (age < 1 || age > 120)) {
            throw new ValidationException("La edad debe estar entre 1 y 120");
        }
        return age;
    }
}
