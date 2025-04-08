package com.prontuario.consultas.adapters.controllers;

import com.prontuario.consultas.application.usecase.GerenciarConsultaUseCase;
import com.prontuario.consultas.domain.entity.Consulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    private final GerenciarConsultaUseCase gerenciarConsultaUseCase;

    public ConsultaController(GerenciarConsultaUseCase gerenciarConsultaUseCase) {
        this.gerenciarConsultaUseCase = gerenciarConsultaUseCase;
    }

    @PostMapping
    public ResponseEntity<Consulta> agendarConsulta(@RequestBody Consulta consulta) {
        Consulta consultaAgendada = gerenciarConsultaUseCase.agendarConsulta(consulta);
        return ResponseEntity.ok(consultaAgendada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarConsultaPorId(@PathVariable Long id) {
        Optional<Consulta> consulta = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        return consulta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> buscarConsultasPorPacienteId(@PathVariable Long pacienteId) {
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPacienteId(pacienteId);
        if (consultas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Consulta>> buscarConsultasPorPeriodo(@RequestParam String inicio, @RequestParam String fim) {
        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);
        List<Consulta> consultas = gerenciarConsultaUseCase.buscarConsultasPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizarConsulta(@PathVariable Long id, @RequestBody Consulta consultaAtualizada) {
        Optional<Consulta> consultaExistente = gerenciarConsultaUseCase.buscarConsultaPorId(id);
        if (consultaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        consultaAtualizada.setId(id);
        Consulta consultaSalva = gerenciarConsultaUseCase.agendarConsulta(consultaAtualizada);

        return ResponseEntity.ok(consultaSalva);
    }
}
