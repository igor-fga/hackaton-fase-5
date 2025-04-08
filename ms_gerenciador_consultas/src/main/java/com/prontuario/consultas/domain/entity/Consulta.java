package com.prontuario.consultas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pacienteId;
    private LocalDateTime dataHora;
    private Long medicoId;
    private String motivoConsulta;
    private String anamnese;
    private String diagnostico;
    private String observacoes;
}