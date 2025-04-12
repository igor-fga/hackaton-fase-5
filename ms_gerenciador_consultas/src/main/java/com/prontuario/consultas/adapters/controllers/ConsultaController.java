package com.prontuario.consultas.adapters.controllers;

import com.prontuario.consultas.application.dto.ConsultaDTO;
import com.prontuario.consultas.application.mapper.ConsultaMapper;
import com.prontuario.consultas.application.usecase.GerenciarConsultaUseCase;
import com.prontuario.consultas.domain.entity.Consulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    private final GerenciarConsultaUseCase gerenciarConsultaUseCase;

    public ConsultaController(GerenciarConsultaUseCase gerenciarConsultaUseCase) {
        this.gerenciarConsultaUseCase = gerenciarConsultaUseCase;
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> agendarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        Consulta consulta = ConsultaMapper.toEntity(consultaDTO);
        Consulta consultaAgendada = gerenciarConsultaUseCase.agendarConsulta(consulta);
        return ResponseEntity.ok(ConsultaMapper.toDTO(consultaAgendada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarConsultaPorId(@PathVariable Long id) {
        Optional<Consulta> consulta = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        return consulta.map(value -> ResponseEntity.ok(ConsultaMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaDTO>> buscarConsultasPorPacienteId(@PathVariable Long pacienteId) {
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPacienteId(pacienteId);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ConsultaDTO> consultaDTOs = consultas.stream()
                .map(ConsultaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultaDTOs);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ConsultaDTO>> buscarConsultasPorPeriodo(@RequestParam String inicio, @RequestParam String fim) {
        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPeriodo(dataInicio, dataFim);
        List<ConsultaDTO> consultaDTOs = consultas.stream()
                .map(ConsultaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultaDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTOAtualizada) {
        Optional<Consulta> consultaExistente = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        if (consultaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Consulta consultaAtualizada = ConsultaMapper.toEntity(consultaDTOAtualizada);
        //consultaAtualizada.setId(id);
        Consulta consultaSalva = gerenciarConsultaUseCase.agendarConsulta(consultaAtualizada);

        return ResponseEntity.ok(ConsultaMapper.toDTO(consultaSalva));
    }
}
