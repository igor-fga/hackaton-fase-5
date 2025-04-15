package com.prontuario.consultas.adapters.repository.consulta;

import com.prontuario.consultas.domain.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaJpaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicioDia, LocalDateTime fimDia);
}
