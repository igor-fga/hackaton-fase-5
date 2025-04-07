package com.prontuario.medicamentos.infrastructure.persistence.repository;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.domain.repository.MedicamentoRepository;
import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;
import com.prontuario.medicamentos.infrastructure.persistence.mapper.MedicamentoMapper;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class MedicamentoRepositoryImpl implements MedicamentoRepository {

    private final SpringDataMedicamentoRepository springDataRepo;
    private final MedicamentoMapper mapper;

    public MedicamentoRepositoryImpl(SpringDataMedicamentoRepository springDataRepo,
                                     MedicamentoMapper mapper) {
        this.springDataRepo = springDataRepo;
        this.mapper = mapper;
    }

    @Override
    public Medicamento salvar(Medicamento medicamento) {
        Objects.requireNonNull(medicamento, "Medicamento não pode ser nulo");

        MedicamentoEntity entity = mapper.toEntity(medicamento);
        MedicamentoEntity savedEntity = springDataRepo.save(entity);
        return mapper.toDomain(savedEntity);
    }
}