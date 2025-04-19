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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "prescricao_medicamento",
            joinColumns = @JoinColumn(name = "prescricao_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id"))
    private List<Medicamento> medicamentos; // Alterado para List<Medicamento>

    private String posologia;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String observacoes;
    private Boolean ativa;

    public Prescricao() {}

    public Prescricao(Long consultaId, Long pacienteId, List<Medicamento> medicamentos,
                      String posologia, LocalDate dataInicio, LocalDate dataTermino,
                      String observacoes, Boolean ativa) {
        this.consultaId = consultaId;
        this.pacienteId = pacienteId;
        this.medicamentos = medicamentos;
        this.posologia = posologia;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.observacoes = observacoes;
        this.ativa = ativa;
    }
}