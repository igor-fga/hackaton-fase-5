package com.prontuario.medicamentos.adapters.respository;

import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.entity.MedicamentoEntity;
import com.prontuario.medicamentos.adapters.mapper.MedicamentoMapper;
import com.prontuario.medicamentos.domain.repository.SpringDataMedicamentoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


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
        return mapper.toDTO(savedEntity);
    }
    @Override
    public List<Medicamento> listarTodos() {
        return springDataRepo.findAll()
                .stream()
                .map(mapper::toDTO) // <- conversão correta aqui
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return springDataRepo.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public void deletarPorId(Long id) {
        springDataRepo.deleteById(id);
    }
}