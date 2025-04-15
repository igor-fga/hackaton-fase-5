package com.prontuario.consultas.adapters.repository.horarioTrabalho;

import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import com.prontuario.consultas.domain.repository.HorarioTrabalhoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HorarioTrabalhoRepositoryImpl implements HorarioTrabalhoRepository {

    private final HorarioTrabalhoJpaRepository horarioTrabalhoJpaRepository;

    public HorarioTrabalhoRepositoryImpl(HorarioTrabalhoJpaRepository horarioTrabalhoJpaRepository) {
        this.horarioTrabalhoJpaRepository = horarioTrabalhoJpaRepository;
    }

    @Override
    public List<HorarioTrabalho> findByMedicoId(Long medicoId) {
        return horarioTrabalhoJpaRepository.findByMedicoId(medicoId);
    }

    @Override
    public Optional<HorarioTrabalho> findByMedicoIdAndDiaSemana(Long id, String diaSemana) {
        return horarioTrabalhoJpaRepository.findByMedicoIdAndDiaSemana(id, diaSemana);
    }
}
