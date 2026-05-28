package com.crud.users_crud.domain.model;

import com.crud.users_crud.domain.exception.DomainException;

public class User {

    private Long id;
    private String name;
    private String email;
    private Integer age;

    public User(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.age = validateAge(age);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }

    public void setId(Long id) { this.id = id; }

    public void updateWith(User incoming) {
        this.name = validateName(incoming.getName());
        this.email = validateEmail(incoming.getEmail());
        this.age = validateAge(incoming.getAge());
    }

    private String validateName(String name) {
        if (name == null || name.isBlank())
            throw new DomainException("El nombre es obligatorio.");
        if (name.length() < 2 || name.length() > 100)
            throw new DomainException("El nombre debe tener entre 2 y 100 caracteres.");
        return name;
    }

    private String validateEmail(String email) {
        if (email == null || email.isBlank())
            throw new DomainException("Se requiere correo electronico.");
        if (!email.contains("@") || !email.contains("."))
            throw new DomainException("el correo electronico debe ser valido.");
        return email;
    }

    private Integer validateAge(Integer age) {
        if (age != null && (age < 1 || age > 120))
            throw new DomainException("La edad debe estar entre 1 y 120 años.");
        return age;
    }
}