package com.prontuario.consultas.domain.exceptions;

public class HorarioForaExpedienteException extends NegocioException {
    public HorarioForaExpedienteException() {
        super("Horário fora do expediente do médico");
    }
}
