package com.prontuario.medicamentos.application.usecase;


import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.adapters.respository.PrescricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PrescricaoUseCase {

    private final PrescricaoRepository repository;

    public PrescricaoUseCase(PrescricaoRepository repository) {
        this.repository = repository;
    }

    public Prescricao salvar(Prescricao prescricao) {
        return repository.salvar(prescricao);
    }

    public List<Prescricao> listarPorPaciente(Long pacienteId) {
        return repository.listarPorPaciente(pacienteId);
    }

    public List<Prescricao> listarAtivasPorPaciente(Long pacienteId) {
        return repository.listarAtivasPorPaciente(pacienteId);
    }
}