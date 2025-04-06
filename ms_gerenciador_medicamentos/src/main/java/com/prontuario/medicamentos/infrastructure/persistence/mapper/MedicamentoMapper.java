package com.prontuario.medicamentos.infrastructure.persistence.mapper;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;

public class MedicamentoMapper {
    public static MedicamentoEntity toEntity(Medicamento m) {
        MedicamentoEntity e = new MedicamentoEntity();
        e.setNome(m.getNome());
        e.setPrincipioAtivo(m.getPrincipioAtivo());
        e.setFabricante(m.getFabricante());
        e.setDosagem(m.getDosagem());
        return e;
    }

    public static Medicamento toDomain(MedicamentoEntity e) {
        Medicamento m = new Medicamento(
                e.getNome(), e.getPrincipioAtivo(), e.getFabricante(), e.getDosagem());
        m.setId(e.getId());
        return m;
    }
}