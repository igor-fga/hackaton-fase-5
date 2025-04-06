package com.prontuario.medicamentos.domain.repository;

import com.prontuario.medicamentos.domain.model.Medicamento;

public interface MedicamentoRepository {
    Medicamento salvar(Medicamento medicamento);
}