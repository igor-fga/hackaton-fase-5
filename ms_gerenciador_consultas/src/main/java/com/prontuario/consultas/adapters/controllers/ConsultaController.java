package com.prontuario.consultas.adapters.controllers;

import com.prontuario.consultas.application.dto.ConsultaRequest;
import com.prontuario.consultas.application.dto.ConsultaResponse;
import com.prontuario.consultas.application.dto.DisponibilidadeResponse;
import com.prontuario.consultas.application.dto.VerificarDisponibilidadeRequest;
import com.prontuario.consultas.application.mapper.ConsultaMapper;
import com.prontuario.consultas.application.usecase.GerenciarConsultaUseCase;
import com.prontuario.consultas.application.usecase.GerenciarDisponibilidadeUseCase;
import com.prontuario.consultas.domain.entity.Consulta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    private final GerenciarConsultaUseCase gerenciarConsultaUseCase;
    private final GerenciarDisponibilidadeUseCase gerenciarDisponibilidadeUseCase;

    public ConsultaController(GerenciarConsultaUseCase gerenciarConsultaUseCase,
                              GerenciarDisponibilidadeUseCase gerenciarDisponibilidadeUseCase) {
        this.gerenciarConsultaUseCase = gerenciarConsultaUseCase;
        this.gerenciarDisponibilidadeUseCase = gerenciarDisponibilidadeUseCase;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponse> agendarConsulta(@RequestBody ConsultaRequest consulta) {
        ConsultaResponse consultaAgendada = gerenciarConsultaUseCase.criarConsulta(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaAgendada);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarConsultaPorId(@PathVariable Long id) {
        Optional<Consulta> consulta = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        return consulta.map(value -> ResponseEntity.ok(ConsultaMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaResponse>> buscarConsultasPorPacienteId(@PathVariable Long pacienteId) {
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPacienteId(pacienteId);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ConsultaResponse> consultaDTOs = consultas.stream()
                .map(ConsultaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultaDTOs);
    }


    @GetMapping("/periodo")
    public ResponseEntity<List<ConsultaResponse>> buscarConsultasPorPeriodo(@RequestParam String inicio, @RequestParam String fim) {
        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPeriodo(dataInicio, dataFim);
        List<ConsultaResponse> consultaDTOs = consultas.stream()
                .map(ConsultaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultaDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaRequest consultaAtualizada) {
        Optional<Consulta> consultaExistente = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        if (consultaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consulta consulta = consultaExistente.get();
        consulta.setPacienteId(consultaAtualizada.getPacienteId());
        consulta.setTipoConsulta(consultaAtualizada.getTipoConsulta());
        consulta.setDataHora(consultaAtualizada.getDataHora());
        consulta.setEspecialidade(consultaAtualizada.getEspecialidade());
        consulta.setMotivoConsulta(consultaAtualizada.getMotivoConsulta());
        consulta.setAnamnese(consultaAtualizada.getAnamnese());
        consulta.setDiagnostico(consultaAtualizada.getDiagnostico());
        consulta.setObservacoes(consultaAtualizada.getObservacoes());

        Consulta consultaSalva = gerenciarConsultaUseCase.salvarConsulta(consulta);

        return ResponseEntity.ok(ConsultaMapper.toDTO(consultaSalva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConsulta(@PathVariable Long id) {
        boolean consultaCancelada = gerenciarConsultaUseCase.cancelarConsulta(id);
        if (consultaCancelada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/disponibilidade")
    public ResponseEntity<DisponibilidadeResponse> verificarDisponibilidade(
            @Valid @RequestBody VerificarDisponibilidadeRequest request) {
        DisponibilidadeResponse response = gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{medicoId}/dias-disponiveis")
    public ResponseEntity<List<LocalDate>> verificarDiasDisponiveis(
            @PathVariable Long medicoId,
            @RequestParam String inicio,
            @RequestParam String fim) {
        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        List<LocalDate> diasDisponiveis = gerenciarDisponibilidadeUseCase.verificarDiasDisponiveis(medicoId, dataInicio, dataFim);
        return ResponseEntity.ok(diasDisponiveis);
    }
}
