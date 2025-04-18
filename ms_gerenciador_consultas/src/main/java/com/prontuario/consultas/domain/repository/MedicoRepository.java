package com.prontuario.consultas.domain.repository;

import com.prontuario.consultas.domain.entity.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    List<Medico> findByEspecialidade(String especialidade);

    Optional<Medico> findById(Long medicoId);

    List<Medico> findAll();
}
