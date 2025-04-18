package com.prontuario.consultas.domain.exceptions;


public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }
}

