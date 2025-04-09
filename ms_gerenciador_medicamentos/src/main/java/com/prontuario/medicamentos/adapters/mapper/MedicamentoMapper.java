package com.prontuario.medicamentos.adapters.mapper;

import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.entity.MedicamentoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicamentoMapper {

    public Medicamento toEntity(MedicamentoDTO dto) {
        Medicamento medicamento = new Medicamento(
                dto.getNome(),
                dto.getPrincipioAtivo(),
                dto.getFabricante(),
                dto.getDosagem()
        );
        medicamento.setId(dto.getId());
        return medicamento;
    }


    public MedicamentoDTO toDTO(Medicamento entity) {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setPrincipioAtivo(entity.getPrincipioAtivo());
        dto.setFabricante(entity.getFabricante());
        dto.setDosagem(entity.getDosagem());
        return dto;
    }

    public MedicamentoEntity toEntity(Medicamento domain) {
        MedicamentoEntity entity = new MedicamentoEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setPrincipioAtivo(domain.getPrincipioAtivo());
        entity.setFabricante(domain.getFabricante());
        entity.setDosagem(domain.getDosagem());
        return entity;
    }

    public Medicamento toDTO(MedicamentoEntity entity) {
        Medicamento dto = new Medicamento(
                entity.getNome(),
                entity.getPrincipioAtivo(),
                entity.getFabricante(),
                entity.getDosagem()
        );
        dto.setId(entity.getId());
        return dto;
    }
    public List<MedicamentoDTO> toDTOList(List<Medicamento> medicamentos) {
        return medicamentos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
