package com.prontuario.medicamentos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescricaoDTO {
    private Long id;
    private Long consultaId;
    private Long pacienteId;
    private List<Long> medicamentosIds;
    private String posologia;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String observacoes;
    private Boolean ativa;

}

