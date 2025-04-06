package com.prontuario.medicamentos.application;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.domain.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastrarMedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public CadastrarMedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Medicamento cadastrar(Medicamento medicamento) {
        return medicamentoRepository.salvar(medicamento);
    }
}
