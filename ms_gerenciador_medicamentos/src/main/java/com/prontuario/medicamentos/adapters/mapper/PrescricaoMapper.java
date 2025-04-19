package com.prontuario.medicamentos.adapters.mapper;

import com.prontuario.medicamentos.application.dto.PrescricaoDTO;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrescricaoMapper {

    public Prescricao toDomain(PrescricaoDTO dto) {
        Prescricao prescricao = new Prescricao(
                dto.getConsultaId(),
                dto.getPacienteId(),
                dto.getMedicamentosIds(),
                dto.getPosologia(),
                dto.getDataInicio(),
                dto.getDataTermino(),
                dto.getObservacoes(),
                dto.getAtiva()
        );
        prescricao.setId(dto.getId());
        return prescricao;
    }

    public PrescricaoDTO toDTO(Prescricao domain) {
        PrescricaoDTO dto = new PrescricaoDTO();
        dto.setId(domain.getId());
        dto.setConsultaId(domain.getConsultaId());
        dto.setPacienteId(domain.getPacienteId());
        dto.setMedicamentosIds(domain.getMedicamentosIds());
        dto.setPosologia(domain.getPosologia());
        dto.setDataInicio(domain.getDataInicio());
        dto.setDataTermino(domain.getDataTermino());
        dto.setObservacoes(domain.getObservacoes());
        dto.setAtiva(domain.getAtiva());
        return dto;
    }

    public List<PrescricaoDTO> toDTOList(List<Prescricao> prescricoes) {
        return prescricoes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}