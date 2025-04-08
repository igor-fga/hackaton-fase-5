package com.prontuario.consultas.domain.repository;

import com.prontuario.consultas.domain.entity.Consulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository {
    Consulta save(Consulta consulta);
    Optional<Consulta> findById(Long id);
    List<Consulta> findByPacienteId(Long pacienteId);
    List<Consulta> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
