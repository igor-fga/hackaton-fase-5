package com.prontuario.medicamentos.adapters.mapper;

import com.prontuario.medicamentos.application.dto.PrescricaoDTO;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.domain.entity.PrescricaoEntity;
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

    public static PrescricaoEntity toEntity(Prescricao domain) {
        PrescricaoEntity entity = new PrescricaoEntity();
        entity.setId(domain.getId());
        entity.setConsultaId(domain.getConsultaId());
        entity.setPacienteId(domain.getPacienteId());
        entity.setMedicamentosIds(domain.getMedicamentosIds());
        entity.setPosologia(domain.getPosologia());
        entity.setDataInicio(domain.getDataInicio());
        entity.setDataTermino(domain.getDataTermino());
        entity.setObservacoes(domain.getObservacoes());
        entity.setAtiva(domain.getAtiva());

        return entity;
    }

    public Prescricao toDomain(PrescricaoEntity entity) {
        Prescricao domain = new Prescricao(
                entity.getConsultaId(),
                entity.getPacienteId(),
                entity.getMedicamentosIds(),
                entity.getPosologia(),
                entity.getDataInicio(),
                entity.getDataTermino(),
                entity.getObservacoes(),
                entity.getAtiva()
        );
        domain.setId(entity.getId());
        return domain;
    }

    public List<PrescricaoDTO> toDTOList(List<Prescricao> prescricoes) {
        return prescricoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Prescricao> toDomainList(List<PrescricaoEntity> entities) {
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }
}
