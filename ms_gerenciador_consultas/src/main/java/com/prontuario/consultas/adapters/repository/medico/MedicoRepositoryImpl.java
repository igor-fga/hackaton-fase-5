package com.prontuario.consultas.adapters.repository.medico;

import com.prontuario.consultas.domain.entity.Medico;
import com.prontuario.consultas.domain.repository.MedicoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MedicoRepositoryImpl implements MedicoRepository {
    private final MedicoJpaRepository medicoJpaRepository;

    public MedicoRepositoryImpl(MedicoJpaRepository medicoJpaRepository) {
        this.medicoJpaRepository = medicoJpaRepository;
    }
    @Override
    public List<Medico> findByEspecialidade(String especialidade) {
        return medicoJpaRepository.findByEspecialidade(especialidade);
    }

    @Override
    public Optional<Medico> findById(Long medicoId) {
        return medicoJpaRepository.findById(medicoId);
    }

    @Override
    public List<Medico> findAll() {
        return medicoJpaRepository.findAll();
    }
}
