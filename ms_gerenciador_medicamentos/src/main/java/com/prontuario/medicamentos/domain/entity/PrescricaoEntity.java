package com.prontuario.medicamentos.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class PrescricaoEntity {
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
}
