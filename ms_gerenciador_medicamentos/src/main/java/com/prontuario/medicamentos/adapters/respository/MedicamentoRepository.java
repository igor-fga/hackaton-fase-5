package com.prontuario.medicamentos.adapters.respository;
import java.util.List;
import java.util.Optional;

import com.prontuario.medicamentos.domain.entity.Medicamento;

public interface MedicamentoRepository {
    Medicamento salvar(Medicamento medicamento);
    List<Medicamento> listarTodos();
    Optional<Medicamento> buscarPorId(Long id);
    List<Medicamento> buscarPorNome(String nome);
    void deletarPorId(Long id);
}