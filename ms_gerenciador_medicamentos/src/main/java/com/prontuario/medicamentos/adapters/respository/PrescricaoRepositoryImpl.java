package com.prontuario.medicamentos.adapters.respository;

import com.prontuario.medicamentos.domain.entity.PrescricaoEntity;
import com.prontuario.medicamentos.adapters.mapper.PrescricaoMapper;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.domain.repository.SpringDataPrescricaoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PrescricaoRepositoryImpl implements PrescricaoRepository {

    private final SpringDataPrescricaoRepository springData;
    private final PrescricaoMapper prescricaoMapper;

    public PrescricaoRepositoryImpl(SpringDataPrescricaoRepository springData, PrescricaoMapper prescricaoMapper) {
        this.springData = springData;
        this.prescricaoMapper = prescricaoMapper;
    }

    @Override
    public Prescricao salvar(Prescricao prescricao) {
        var entity = prescricaoMapper.toEntity(prescricao);  // Instância do mapper usada aqui
        return prescricaoMapper.toDomain(springData.save(entity));
    }

    @Override
    public List<Prescricao> listarPorPaciente(Long pacienteId) {
        return springData.findByPacienteId(pacienteId)
                .stream().map(prescricaoMapper::toDomain).toList();
    }

    @Override
    public List<Prescricao> listarAtivasPorPaciente(Long pacienteId) {
        LocalDate hoje = LocalDate.now();
        return springData
                .findByPacienteIdAndDataInicioLessThanEqualAndDataTerminoGreaterThanEqual(pacienteId, hoje, hoje)
                .stream()
                .map(prescricaoMapper::toDomain)
                .toList();
    }

}
