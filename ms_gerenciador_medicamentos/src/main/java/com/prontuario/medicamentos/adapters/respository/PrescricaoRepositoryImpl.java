package com.prontuario.medicamentos.adapters.respository;

import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.domain.repository.SpringDataPrescricaoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PrescricaoRepositoryImpl implements PrescricaoRepository {

    private final SpringDataPrescricaoRepository springData;

    public PrescricaoRepositoryImpl(SpringDataPrescricaoRepository springData) {
        this.springData = springData;
    }

    @Override
    public Prescricao salvar(Prescricao prescricao) {
        return springData.save(prescricao);
    }

    @Override
    public List<Prescricao> listarPorPaciente(Long pacienteId) {
        return springData.findByPacienteId(pacienteId);
    }

    @Override
    public List<Prescricao> listarAtivasPorPaciente(Long pacienteId) {
        LocalDate hoje = LocalDate.now();
        return springData.findByPacienteIdAndDataInicioLessThanEqualAndDataTerminoGreaterThanEqual(
                pacienteId, hoje, hoje);
    }
}