package com.prontuario.medicamentos.adapters.controllers;

import com.prontuario.medicamentos.adapters.mapper.MedicamentoMapper;
import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.application.usecase.MedicamentoUseCase;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoUseCase medicamentoUseCase;
    private final MedicamentoMapper medicamentoMapper;

    public MedicamentoController(MedicamentoUseCase medicamentoUseCase, MedicamentoMapper medicamentoMapper) {
        this.medicamentoUseCase = medicamentoUseCase;
        this.medicamentoMapper = medicamentoMapper;
    }

    @PostMapping
    public ResponseEntity<MedicamentoDTO> cadastrar(@Valid @RequestBody MedicamentoDTO dto) {
        Medicamento medicamento = medicamentoMapper.toEntity(dto);
        Medicamento salvo = medicamentoUseCase.cadastrar(medicamento);
        return ResponseEntity
                .status(201)
                .body(medicamentoMapper.toDTO(salvo));
    }
    @GetMapping
    public ResponseEntity<List<MedicamentoDTO>> listarTodos() {
        var medicamentos = medicamentoUseCase.listarTodos();
        return ResponseEntity.ok(medicamentoMapper.toDTOList(medicamentos));
    }
    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable Long id) {
        Medicamento medicamento = medicamentoUseCase.buscarPorId(id);
        return ResponseEntity.ok(medicamentoMapper.toDTO(medicamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> atualizar(@PathVariable Long id, @RequestBody MedicamentoDTO dto) {
        Medicamento atualizado = medicamentoUseCase.atualizar(id, medicamentoMapper.toEntity(dto));
        return ResponseEntity.ok(medicamentoMapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicamentoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
