package com.prontuario.pacientes.adapters.mapper;

import com.prontuario.pacientes.application.dto.PacienteDTO;
import com.prontuario.pacientes.domain.entity.Paciente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNomeCompleto(dto.getNomeCompleto());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setGenero(dto.getGenero());
        paciente.setCpf(dto.getCpf());
        paciente.setNumeroProntuario(dto.getNumeroProntuario());
        paciente.setEmail(dto.getEmail());
        paciente.setEndereco(dto.getEndereco());
        paciente.setContato(dto.getContato());
        return paciente;
    }

    public PacienteDTO toDTO(Paciente paciente) {
        var dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNomeCompleto(paciente.getNomeCompleto());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setGenero(paciente.getGenero());
        dto.setCpf(paciente.getCpf());
        dto.setNumeroProntuario(paciente.getNumeroProntuario());
        dto.setEmail(paciente.getEmail());
        dto.setEndereco(paciente.getEndereco());
        dto.setContato(paciente.getContato());
        return dto;
    }

    public List<PacienteDTO> toDTOList(List<Paciente> pacientes) {
        return pacientes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
