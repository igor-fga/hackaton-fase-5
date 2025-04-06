package com.prontuario.medicamentos.infrastructure.persistence.mapper;

import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;

public class MedicamentoMapper {
    public static MedicamentoEntity toEntity(Medicamento domain) {
        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setNome(domain.getNome());
        entity.setPrincipioAtivo(domain.getPrincipioAtivo());
        entity.setFabricante(domain.getFabricante());
        entity.setDosagem(domain.getDosagem());
        return entity;
    }

    public static Medicamento toDomain(MedicamentoEntity entity) {
        Medicamento domain = new Medicamento(
                entity.getNome(),
                entity.getPrincipioAtivo(),
                entity.getFabricante(),
                entity.getDosagem()
        );
        domain.setId(entity.getId());
        return domain;
    }
}