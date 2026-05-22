package com.crud.users_crud.exception;

// Excepcion de validacion del dominio, sin dependencias externas.
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}


