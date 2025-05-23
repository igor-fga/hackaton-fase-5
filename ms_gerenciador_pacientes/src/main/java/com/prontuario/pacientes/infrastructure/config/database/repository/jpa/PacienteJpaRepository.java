package com.prontuario.pacientes.infrastructure.config.database.repository.jpa;

import com.prontuario.pacientes.domain.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteJpaRepository extends JpaRepository<Paciente,Long> {

    boolean existsByCpf(String cpf);
    Optional<Paciente> findByCpf(String cpf);
    Page<Paciente> findByNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);

}
