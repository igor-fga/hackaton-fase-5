package com.prontuario.medicamentos.interfaces.rest.repository;


import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.domain.repository.MedicamentoRepository;
import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;
import com.prontuario.medicamentos.infrastructure.persistence.SpringDataMedicamentoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class MedicamentoRepositoryImpl implements MedicamentoRepository {

    @Autowired
    private SpringDataMedicamentoRepository springDataRepo;

    @Override
    public Medicamento salvar(Medicamento medicamento) {
        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setNome(medicamento.getNome());
        entity.setPrincipioAtivo(medicamento.getPrincipioAtivo());
        entity.setFabricante(medicamento.getFabricante());
        entity.setDosagem(medicamento.getDosagem());

        entity = springDataRepo.save(entity);

        // Retornando objeto de domínio
        medicamento.setId(entity.getId());
        return medicamento;

    }
}
