package com.prontuario.medicamentos.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "prescricoes")
public class Prescricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long consultaId;
    private Long pacienteId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> medicamentosIds;

    private String posologia;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String observacoes;
    private Boolean ativa;

    // Construtores mantidos
    public Prescricao() {}

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
}