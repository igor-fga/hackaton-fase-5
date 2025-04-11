package com.prontuario.medicamentos.application.usecase;

import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.adapters.respository.MedicamentoRepository;
import com.prontuario.medicamentos.domain.exceptions.ControllerDatabaseException;
import com.prontuario.medicamentos.domain.exceptions.ControllerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicamentoUseCase {

    private final MedicamentoRepository repository;

    public MedicamentoUseCase(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public Medicamento cadastrar(Medicamento medicamento) {
        if (medicamento == null) {
            throw new IllegalArgumentException("Medicamento não pode ser nulo");
        }
        try {
            return repository.salvar(medicamento);
        } catch (Exception ex) {
            throw new ControllerDatabaseException("Erro ao salvar no banco de dados", ex);
        }
    }
    public List<Medicamento> listarTodos() {
        return repository.listarTodos();
    }
    public Medicamento buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new ControllerNotFoundException("Medicamento com ID " + id + " não encontrado"));
    }

    public Medicamento atualizar(Long id, Medicamento medicamentoAtualizado) {
        Medicamento existente = buscarPorId(id);
        medicamentoAtualizado.setId(existente.getId());
        return repository.salvar(medicamentoAtualizado);
    }
    public List<Medicamento> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome);
    }

    public void deletar(Long id) {
        repository.deletarPorId(id);
    }
}
