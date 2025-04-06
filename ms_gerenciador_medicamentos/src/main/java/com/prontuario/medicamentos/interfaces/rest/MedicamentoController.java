package com.prontuario.medicamentos.interfaces.rest;

import com.prontuario.medicamentos.application.CadastrarMedicamentoService;
import com.prontuario.medicamentos.domain.model.Medicamento;
import com.prontuario.medicamentos.interfaces.rest.dto.MedicamentoRequest;
import com.prontuario.medicamentos.interfaces.rest.dto.MedicamentoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final CadastrarMedicamentoService service;

    public MedicamentoController(CadastrarMedicamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponse> cadastrar(
            @Valid @RequestBody MedicamentoRequest request) {

        Medicamento medicamento = new Medicamento(
                request.getNome(),
                request.getPrincipioAtivo(),
                request.getFabricante(),
                request.getDosagem()
        );

        Medicamento salvo = service.cadastrar(medicamento);
        return ResponseEntity
                .created(URI.create("/api/medicamentos/" + salvo.getId()))
                .body(new MedicamentoResponse(salvo.getId(), salvo.getNome()));
    }
}
