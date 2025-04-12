package com.prontuario.consultas.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {
    private Long pacienteId;
    private LocalDateTime dataHora;
    private Long medicoId;
    private String motivoConsulta;
    private String anamnese;
    private String diagnostico;
    private String observacoes;
}
