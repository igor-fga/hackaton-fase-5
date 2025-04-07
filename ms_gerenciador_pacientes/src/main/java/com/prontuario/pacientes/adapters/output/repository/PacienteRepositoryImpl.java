package com.prontuario.pacientes.adapters.output.repository;

import com.prontuario.pacientes.domain.entity.Paciente;
import com.prontuario.pacientes.domain.repository.PacienteRepository;
import com.prontuario.pacientes.infrastructure.config.database.repository.jpa.PacienteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PacienteRepositoryImpl implements PacienteRepository {

    private final PacienteJpaRepository repository;

    public PacienteRepositoryImpl(PacienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Paciente salvar(Paciente paciente) {
       return repository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
