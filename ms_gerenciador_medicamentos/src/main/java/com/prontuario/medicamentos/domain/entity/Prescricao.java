package com.prontuario.medicamentos.domain.entity;

import lombok.Data;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDate;
import java.util.List;

@Data
public class Prescricao {
    private Long id;
    private Long consultaId;
    private Long pacienteId;
    private List<Long> medicamentosIds;
    private String posologia;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String observacoes;
    private Boolean ativa;

    // Construtor sem ID (para criação de nova prescrição)
    public Prescricao(Long consultaId, Long pacienteId, List<Long> medicamentosIds,
                      String posologia, LocalDate dataInicio, LocalDate dataTermino,
                      String observacoes, Boolean ativa) {
        this.consultaId = consultaId;
        this.pacienteId = pacienteId;
        this.medicamentosIds = medicamentosIds;
        this.posologia = posologia;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.observacoes = observacoes;
        this.ativa = ativa;
    }

    // Construtor com ID (para reconstrução de dados do banco)
    public Prescricao(Long id, Long consultaId, Long pacienteId, List<Long> medicamentosIds,
                      String posologia, LocalDate dataInicio, LocalDate dataTermino,
                      String observacoes, Boolean ativa) {
        this.id = id;
        this.consultaId = consultaId;
        this.pacienteId = pacienteId;
        this.medicamentosIds = medicamentosIds;
        this.posologia = posologia;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.observacoes = observacoes;
        this.ativa = ativa;
    }
}
