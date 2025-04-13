package com.prontuario.pacientes.domain.repository;

import com.prontuario.pacientes.domain.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> buscarTodos();
    void excluir(Long id);
    boolean existsByCpf(String cpf);
    Optional<Paciente> findByCpf(String cpf);
    Page<Paciente> findByNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);
}
