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
    @Column(name = "tipo_consulta")
    @Enumerated(EnumType.STRING)
    private TipoConsulta tipoConsulta;
    @Column(name= "data_hora")
    private LocalDateTime dataHora;
    private String especialidade;
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
    @Column(name="motivo_consulta")
    private String motivoConsulta;
    @Column(nullable = true)
    private String anamnese;
    @Column(nullable = true)
    private String diagnostico;
    @Column(nullable = true)
    private String observacoes;
}