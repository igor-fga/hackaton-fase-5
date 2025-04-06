package com.prontuario.medicamentos.interfaces.rest;

import com.prontuario.medicamentos.application.CadastrarMedicamentoService;
import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.interfaces.rest.dto.MedicamentoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private CadastrarMedicamentoService service;
@PostMapping
public ResponseEntity<Long> cadastrar(@RequestBody MedicamentoRequest request) {
    Medicamento medicamento = new Medicamento(
            request.getNome(),
            request.getPrincipioAtivo(),
            request.getFabricante(),
            request.getDosagem()
    );

    Medicamento salvo = service.cadastrar(medicamento);
    return ResponseEntity.ok(salvo.getId());
    }
}
