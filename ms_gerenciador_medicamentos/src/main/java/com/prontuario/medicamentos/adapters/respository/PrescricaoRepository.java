package com.prontuario.medicamentos.adapters.respository;

import com.prontuario.medicamentos.domain.entity.Prescricao;

import java.util.List;

public interface PrescricaoRepository {
    Prescricao salvar(Prescricao prescricao);
    List<Prescricao> listarPorPaciente(Long pacienteId);
    List<Prescricao> listarAtivasPorPaciente(Long pacienteId);
}
