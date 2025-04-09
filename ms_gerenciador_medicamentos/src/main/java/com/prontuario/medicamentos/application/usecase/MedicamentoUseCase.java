package com.prontuario.medicamentos.application.usecase;

import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.adapters.respository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicamentoUseCase {

    private final MedicamentoRepository repository;

    public MedicamentoUseCase(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public Medicamento cadastrar(Medicamento medicamento) {
        return repository.salvar(medicamento);
    }
    public List<Medicamento> listarTodos() {
        return repository.listarTodos();
    }
    public Medicamento buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado com ID: " + id));
    }

    public Medicamento atualizar(Long id, Medicamento medicamentoAtualizado) {
        Medicamento existente = buscarPorId(id);
        medicamentoAtualizado.setId(existente.getId());
        return repository.salvar(medicamentoAtualizado);
    }

    public void deletar(Long id) {
        repository.deletarPorId(id);
    }
}
