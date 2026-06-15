package com.crud.users_crud.domain.exception;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(Long id) {
        super("Usuario no encontrado con Identificador: " + id);
    }
}
