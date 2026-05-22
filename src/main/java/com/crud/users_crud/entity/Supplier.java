package com.crud.users_crud.entity;

import com.crud.users_crud.exception.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad JPA para proveedores.
@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // Constructor vacio requerido por JPA.
    public Supplier() {
    }

    public Supplier(String name, String email, String phone, Boolean isActive) {
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.phone = validatePhone(phone);
        this.isActive = validateIsActive(isActive);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = validatePhone(phone);
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = validateIsActive(isActive);
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre del proveedor es requerido");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new ValidationException("El nombre debe tener entre 2 y 100 caracteres");
        }
        return name;
    }

    private String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("El email del proveedor es requerido");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("El email debe ser valido");
        }
        return email;
    }

    private String validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new ValidationException("El telefono del proveedor es requerido");
        }
        if (phone.length() < 5 || phone.length() > 20) {
            throw new ValidationException("El telefono debe tener entre 5 y 20 caracteres");
        }
        return phone;
    }

    private Boolean validateIsActive(Boolean isActive) {
        if (isActive == null) {
            throw new ValidationException("El estado del proveedor es requerido");
        }
        return isActive;
    }
}