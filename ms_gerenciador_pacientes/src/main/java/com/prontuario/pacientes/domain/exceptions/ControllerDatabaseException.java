package com.prontuario.pacientes.domain.exceptions;

public class ControllerDatabaseException extends RuntimeException {

    public ControllerDatabaseException(String message) {
        super(message);
    }

    public ControllerDatabaseException(String message, Throwable cause){
        super(message, cause);
    }
}
