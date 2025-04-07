package com.prontuario.pacientes.infrastructure.config.database.repository.jpa;

import com.prontuario.pacientes.domain.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteJpaRepository extends JpaRepository<Paciente, UUID> {
}
