package com.prontuario.medicamentos.infrastructure.persistence.repository;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.domain.repository.MedicamentoRepository;
import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;
import com.prontuario.medicamentos.infrastructure.persistence.mapper.MedicamentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MedicamentoRepositoryImpl implements MedicamentoRepository {

    private final SpringDataMedicamentoRepository springDataRepo;

    public MedicamentoRepositoryImpl(SpringDataMedicamentoRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Medicamento salvar(Medicamento medicamento) {
        MedicamentoEntity entity = MedicamentoMapper.toEntity(medicamento);
        MedicamentoEntity savedEntity = springDataRepo.save(entity);
        return MedicamentoMapper.toDomain(savedEntity);
    }
}