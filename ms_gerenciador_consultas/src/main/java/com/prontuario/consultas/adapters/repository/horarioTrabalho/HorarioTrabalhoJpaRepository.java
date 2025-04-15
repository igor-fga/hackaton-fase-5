package com.prontuario.consultas.adapters.repository.horarioTrabalho;

import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HorarioTrabalhoJpaRepository extends JpaRepository<HorarioTrabalho, Long> {
    List<HorarioTrabalho> findByMedicoId(Long medicoId);

    Optional<HorarioTrabalho> findByMedicoIdAndDiaSemana(Long id, String diaSemana);
}
