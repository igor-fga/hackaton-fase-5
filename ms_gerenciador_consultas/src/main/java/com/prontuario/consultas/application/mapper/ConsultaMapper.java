package com.prontuario.consultas.application.mapper;

import com.prontuario.consultas.application.dto.ConsultaRequest;
import com.prontuario.consultas.application.dto.ConsultaResponse;
import com.prontuario.consultas.domain.entity.Consulta;

public class ConsultaMapper {
   /*public static Consulta toEntity(ConsultaRequest dto) {
        return new Consulta(
                null,
                dto.getPacienteId(),
                dto.getTipoConsulta(),
                dto.getDataHora(),
                dto.getEspecialidade(),
                dto.getMedicoId(),
                dto.getMotivoConsulta(),
                dto.getAnamnese(),
                dto.getDiagnostico(),
                dto.getObservacoes()
        );
    }*/

    public static ConsultaResponse toDTO(Consulta entity) {
        ConsultaResponse dto = new ConsultaResponse();
        dto.setId(entity.getId());
        dto.setPacienteId(entity.getPacienteId());
        dto.setTipoConsulta(entity.getTipoConsulta());
        dto.setEspecialidade(entity.getEspecialidade());
        dto.setDataHora(entity.getDataHora());
        dto.setNomeMedico(entity.getMedico().getNome());
        dto.setMedicoId(entity.getMedico().getId());
        dto.setMotivoConsulta(entity.getMotivoConsulta());
        dto.setAnamnese(entity.getAnamnese());
        dto.setDiagnostico(entity.getDiagnostico());
        dto.setObservacoes(entity.getObservacoes());
        return dto;
    }
}
