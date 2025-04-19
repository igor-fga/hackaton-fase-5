package com.prontuario.medicamentos.adapters.controllers;

import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.application.usecase.MedicamentoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoUseCase medicamentoUseCase;

    public MedicamentoController(MedicamentoUseCase medicamentoUseCase) {
        this.medicamentoUseCase = medicamentoUseCase;
    }

    @PostMapping
    public ResponseEntity<MedicamentoDTO> cadastrar(@Valid @RequestBody MedicamentoDTO dto) {
        MedicamentoDTO salvo = medicamentoUseCase.cadastrar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoDTO>> listarTodos() {
        List<MedicamentoDTO> medicamentos = medicamentoUseCase.listarTodos();
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable Long id) {
        MedicamentoDTO medicamento = medicamentoUseCase.buscarPorId(id);
        return ResponseEntity.ok(medicamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MedicamentoDTO dto) {
        MedicamentoDTO atualizado = medicamentoUseCase.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorNome(@RequestParam String nome) {
        List<MedicamentoDTO> medicamentos = medicamentoUseCase.buscarPorNome(nome);
        return ResponseEntity.ok(medicamentos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicamentoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}