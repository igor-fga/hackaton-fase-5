package com.prontuario.medicamentos.application;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.domain.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;


@Service
public class CadastrarMedicamentoService {

    private final MedicamentoRepository repository;

    public CadastrarMedicamentoService(MedicamentoRepository repository) {
        this.repository = repository;
    }

    public Medicamento cadastrar(Medicamento medicamento) {
        return repository.salvar(medicamento);
    }
}
