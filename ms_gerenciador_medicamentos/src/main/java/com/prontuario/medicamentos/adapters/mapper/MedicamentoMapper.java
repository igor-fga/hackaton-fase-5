package com.prontuario.medicamentos.adapters.mapper;

import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicamentoMapper {

    // Converte o MedicamentoDTO para a entidade de domínio Medicamento
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

    // Converte a entidade de domínio Medicamento para MedicamentoDTO
    public MedicamentoDTO toDTO(Medicamento entity) {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setPrincipioAtivo(entity.getPrincipioAtivo());
        dto.setFabricante(entity.getFabricante());
        dto.setDosagem(entity.getDosagem());
        return dto;
    }

    // Converte uma lista de Medicamentos para uma lista de MedicamentoDTOs
    public List<MedicamentoDTO> toDTOList(List<Medicamento> medicamentos) {
        return medicamentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
