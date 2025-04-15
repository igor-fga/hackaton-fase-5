package com.prontuario.consultas.application.dto;

import com.prontuario.consultas.domain.entity.Medico;
import com.prontuario.consultas.domain.entity.TipoConsulta;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaRequest {
    private Long pacienteId;
    private TipoConsulta tipoConsulta;
    private LocalDateTime dataHora;
    private String especialidade;
    private Long medicoId;
    private String motivoConsulta;
    private String anamnese;
    private String diagnostico;
    private String observacoes;
}
