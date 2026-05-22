package com.crud.users_crud.entity;

import com.crud.users_crud.exception.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

// Entidad JPA para clientes.
@Entity
@Table(name = "customers")
public class Custumer {

    private static final BigDecimal MIN_BALANCE = new BigDecimal("0.00");
    private static final BigDecimal MAX_BALANCE = new BigDecimal("999999.99");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // Constructor vacio requerido por JPA.
    public Custumer() {
    }

    public Custumer(String name, String phone, String email, String address, BigDecimal balance, Boolean isActive) {
        this.name = validateName(name);
        this.phone = validatePhone(phone);
        this.email = validateEmail(email);
        this.address = validateAddress(address);
        this.balance = validateBalance(balance);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = validatePhone(phone);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = validateBalance(balance);
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = validateIsActive(isActive);
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("El nombre del cliente es requerido");
        }
        if (name.length() < 3 || name.length() > 100) {
            throw new ValidationException("El nombre debe tener entre 3 y 100 caracteres");
        }
        return name;
    }

    private String validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new ValidationException("El telefono es requerido");
        }
        if (phone.length() < 4 || phone.length() > 12) {
            throw new ValidationException("El telefono debe tener entre 4 y 12 caracteres");
        }
        return phone;
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

    private String validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new ValidationException("La direccion es requerida");
        }
        if (address.length() < 5 || address.length() > 200) {
            throw new ValidationException("La direccion debe tener entre 5 y 200 caracteres");
        }
        return address;
    }

    private BigDecimal validateBalance(BigDecimal balance) {
        if (balance == null) {
            throw new ValidationException("El saldo es requerido");
        }
        if (balance.compareTo(MIN_BALANCE) < 0) {
            throw new ValidationException("El saldo debe ser mayor o igual a 0");
        }
        if (balance.compareTo(MAX_BALANCE) > 0) {
            throw new ValidationException("El saldo no puede exceder 999999.99");
        }
        return balance;
    }

    private Boolean validateIsActive(Boolean isActive) {
        if (isActive == null) {
            throw new ValidationException("El estado del cliente es requerido");
        }
        return isActive;
    }
}
