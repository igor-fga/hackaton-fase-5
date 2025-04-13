package com.prontuario.pacientes.domain.exceptions;

public class CpfJaExisteException extends RuntimeException {
    public CpfJaExisteException(String mensagem) {
        super(mensagem);
    }
}
