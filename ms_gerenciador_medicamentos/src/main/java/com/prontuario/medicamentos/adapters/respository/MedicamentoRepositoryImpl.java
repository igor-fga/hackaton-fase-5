package com.prontuario.medicamentos.adapters.respository;

import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.repository.SpringDataMedicamentoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MedicamentoRepositoryImpl implements MedicamentoRepository {

    private final SpringDataMedicamentoRepository springDataRepo;

    public MedicamentoRepositoryImpl(SpringDataMedicamentoRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public Medicamento salvar(Medicamento medicamento) {
        Objects.requireNonNull(medicamento, "Medicamento não pode ser nulo");
        return springDataRepo.save(medicamento);  // Retorna a entidade diretamente
    }

    @Override
    public List<Medicamento> listarTodos() {
        return springDataRepo.findAll();  // Retorna a lista de Medicamentos diretamente
    }

    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return springDataRepo.findById(id);  // Retorna a entidade diretamente
    }

    @Override
    public List<Medicamento> buscarPorNome(String nome) {
        return springDataRepo.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public void deletarPorId(Long id) {
        springDataRepo.deleteById(id);
    }
}
