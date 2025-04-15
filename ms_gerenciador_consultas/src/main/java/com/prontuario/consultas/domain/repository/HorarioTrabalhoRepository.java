package com.prontuario.consultas.domain.repository;

import com.prontuario.consultas.domain.entity.HorarioTrabalho;

import java.util.List;
import java.util.Optional;

public interface HorarioTrabalhoRepository {
    List<HorarioTrabalho> findByMedicoId(Long medicoId);

    Optional<HorarioTrabalho> findByMedicoIdAndDiaSemana(Long id, String diaSemana);
}
