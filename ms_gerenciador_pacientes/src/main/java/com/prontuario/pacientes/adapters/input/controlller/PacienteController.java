package com.prontuario.pacientes.adapters.input.controlller;

import com.prontuario.pacientes.adapters.mapper.PacienteMapper;
import com.prontuario.pacientes.application.dto.PacienteDTO;
import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteUseCase pacienteUseCase;
    private final PacienteMapper pacienteMapper;

    public PacienteController(PacienteUseCase pacienteUseCase, PacienteMapper pacienteMapper) {
        this.pacienteUseCase = pacienteUseCase;
        this.pacienteMapper = pacienteMapper;
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> criarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        var paciente = pacienteMapper.toEntity(pacienteDTO);
        var pacienteCriado = pacienteUseCase.criarPaciente( paciente);
        return ResponseEntity.status(201).body(pacienteMapper.toDTO(pacienteCriado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> alterarPaciente(@PathVariable UUID id, @RequestBody PacienteDTO pacienteDTO) {
        var paciente = pacienteMapper.toEntity(pacienteDTO);
        var pacienteCriado = pacienteUseCase.alterarPaciente(id, paciente);
        return ResponseEntity.ok(pacienteMapper.toDTO(pacienteCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente(@PathVariable UUID id) {
        var paciente = pacienteUseCase.buscarPaciente(id);
        return ResponseEntity.ok(pacienteMapper.toDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listaPacientes() {
        var pacientes = pacienteUseCase.listaPacientes();
        return ResponseEntity.ok(pacienteMapper.toDTOList(pacientes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable UUID id) {
        pacienteUseCase.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
