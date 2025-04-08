package com.prontuario.consultas.adapters.repository;

import com.prontuario.consultas.domain.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaJpaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

}
