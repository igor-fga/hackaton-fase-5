package com.prontuario.consultas.domain.exceptions;

// Exceções específicas
public class MedicoNaoEncontradoException extends NegocioException {
    public MedicoNaoEncontradoException() {
        super("Médico não encontrado");
    }
}
