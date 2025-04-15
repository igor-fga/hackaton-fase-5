package com.prontuario.consultas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DisponibilidadeResponse {
    private Long medicoId;
    private String nomeMedico;
    private LocalDate data;
    private List<LocalTime> horariosDisponiveis;
}
