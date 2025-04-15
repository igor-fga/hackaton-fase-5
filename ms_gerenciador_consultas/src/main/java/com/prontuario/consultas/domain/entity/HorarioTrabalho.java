package com.prontuario.consultas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "horario_trabalho")
public class HorarioTrabalho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
}
