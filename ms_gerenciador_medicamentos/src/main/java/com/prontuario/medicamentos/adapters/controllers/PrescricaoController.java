package com.prontuario.medicamentos.adapters.controllers;

import com.prontuario.medicamentos.application.dto.PrescricaoDTO;
import com.prontuario.medicamentos.application.usecase.PrescricaoUseCase;
import com.prontuario.medicamentos.adapters.mapper.PrescricaoMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescricoes")
public class PrescricaoController {

    private final PrescricaoUseCase useCase;
    private final PrescricaoMapper prescricaoMapper;

    public PrescricaoController(PrescricaoUseCase useCase, PrescricaoMapper prescricaoMapper) {
        this.useCase = useCase;
        this.prescricaoMapper = prescricaoMapper;
    }

    @PostMapping
    @Transactional
    public PrescricaoDTO cadastrar(@RequestBody PrescricaoDTO dto) {
        return prescricaoMapper.toDTO(useCase.salvar(prescricaoMapper.toDomain(dto)));
    }

    @GetMapping("/paciente/{pacienteId}")
    @Transactional
    public List<PrescricaoDTO> listarPorPaciente(@PathVariable Long pacienteId) {
        return useCase.listarPorPaciente(pacienteId)
                .stream().map(prescricaoMapper::toDTO).toList();
    }

    @GetMapping("/ativas/paciente/{pacienteId}")
    @Transactional
    public List<PrescricaoDTO> listarAtivas(@PathVariable Long pacienteId) {
        return useCase.listarAtivasPorPaciente(pacienteId)
                .stream().map(prescricaoMapper::toDTO).toList();
    }
}