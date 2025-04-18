package com.prontuario.consultas.domain.exceptions;

public class MedicoNaoAtendeNesteDiaException extends NegocioException {
    public MedicoNaoAtendeNesteDiaException() {
        super("Médico não atende neste dia da semana");
    }
}
