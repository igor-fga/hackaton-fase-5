package com.prontuario.medicamentos.adapters.mapper;

import com.prontuario.medicamentos.application.dto.PrescricaoDTO;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.adapters.respository.MedicamentoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrescricaoMapper {

    private final MedicamentoMapper medicamentoMapper;
    private final MedicamentoRepository medicamentoRepository;

    public PrescricaoMapper(MedicamentoMapper medicamentoMapper,
                            MedicamentoRepository medicamentoRepository) {
        this.medicamentoMapper = medicamentoMapper;
        this.medicamentoRepository = medicamentoRepository;
    }

    public Prescricao toDomain(PrescricaoDTO dto) {
        List<Medicamento> medicamentos = dto.getMedicamentos().stream()
                .map(medDto -> medicamentoRepository.buscarPorId(medDto.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (medicamentos.isEmpty()) {
            throw new RuntimeException("Nenhum medicamento válido encontrado");
        }

        Prescricao prescricao = new Prescricao(
                dto.getConsultaId(),
                dto.getPacienteId(),
                medicamentos,
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
        dto.setMedicamentos(domain.getMedicamentos().stream()
                .map(medicamentoMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setPosologia(domain.getPosologia());
        dto.setDataInicio(domain.getDataInicio());
        dto.setDataTermino(domain.getDataTermino());
        dto.setObservacoes(domain.getObservacoes());
        dto.setAtiva(domain.getAtiva());
        return dto;
    }

    public List<PrescricaoDTO> toDTOList(List<Prescricao> prescricoes) {
        return prescricoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}