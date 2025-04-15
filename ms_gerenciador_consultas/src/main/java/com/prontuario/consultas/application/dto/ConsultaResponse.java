package com.prontuario.consultas.application.dto;

import com.prontuario.consultas.domain.entity.TipoConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaResponse {
    private Long id;
    private Long pacienteId;
    private TipoConsulta tipoConsulta;
    private LocalDateTime dataHora;
    private String especialidade;
    private Long medicoId;
    private String nomeMedico;
    private String motivoConsulta;
    private String anamnese;
    private String diagnostico;
    private String observacoes;
}
