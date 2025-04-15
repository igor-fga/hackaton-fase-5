package com.prontuario.consultas.application.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VerificarDisponibilidadeRequest {
    private Long medicoId;
    private LocalDate data;
}
