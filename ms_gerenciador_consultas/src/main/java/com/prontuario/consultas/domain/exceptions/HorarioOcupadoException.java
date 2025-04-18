package com.prontuario.consultas.domain.exceptions;

public class HorarioOcupadoException extends NegocioException {
    public HorarioOcupadoException() {
        super("Horário já ocupado");
    }
}
