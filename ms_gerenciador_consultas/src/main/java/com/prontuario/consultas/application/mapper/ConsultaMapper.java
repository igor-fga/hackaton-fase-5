package com.prontuario.consultas.application.mapper;

import com.prontuario.consultas.application.dto.ConsultaDTO;
import com.prontuario.consultas.domain.entity.Consulta;

public class ConsultaMapper {
    public static Consulta toEntity(ConsultaDTO dto) {
        return new Consulta(
                null,
                dto.getPacienteId(),
                dto.getDataHora(),
                dto.getMedicoId(),
                dto.getMotivoConsulta(),
                dto.getAnamnese(),
                dto.getDiagnostico(),
                dto.getObservacoes()
        );
    }

    public static ConsultaDTO toDTO(Consulta entity) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setPacienteId(entity.getPacienteId());
        dto.setDataHora(entity.getDataHora());
        dto.setMedicoId(entity.getMedicoId());
        dto.setMotivoConsulta(entity.getMotivoConsulta());
        dto.setAnamnese(entity.getAnamnese());
        dto.setDiagnostico(entity.getDiagnostico());
        dto.setObservacoes(entity.getObservacoes());
        return dto;
    }
}
