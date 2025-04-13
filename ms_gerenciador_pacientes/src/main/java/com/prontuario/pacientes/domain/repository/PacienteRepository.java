package com.prontuario.pacientes.domain.repository;

import com.prontuario.pacientes.domain.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> buscarTodos();
    void excluir(Long id);
    boolean existsByCpf(String cpf);
    Optional<Paciente> findByCpf(String cpf);
}
