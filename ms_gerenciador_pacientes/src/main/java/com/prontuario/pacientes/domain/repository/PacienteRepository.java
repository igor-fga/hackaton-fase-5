package com.prontuario.pacientes.domain.repository;

import com.prontuario.pacientes.domain.entity.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Optional<Paciente> buscarPorId(UUID id);
    List<Paciente> buscarTodos();
    void excluir(UUID id);

}
