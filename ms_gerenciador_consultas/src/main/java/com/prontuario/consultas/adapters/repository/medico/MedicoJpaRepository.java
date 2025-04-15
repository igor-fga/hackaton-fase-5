package com.prontuario.consultas.adapters.repository.medico;

import com.prontuario.consultas.domain.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoJpaRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidade(String especialidade);
}
