package com.prontuario.medicamentos.adapters.controllers;
import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.application.usecase.MedicamentoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicamentoControllerTest {

    @Mock
    private MedicamentoUseCase useCase;

    @InjectMocks
    private MedicamentoController controller;

    private MedicamentoDTO medicamentoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        medicamentoDTO = new MedicamentoDTO();
        medicamentoDTO.setId(1L);
        medicamentoDTO.setNome("Paracetamol");
        medicamentoDTO.setPrincipioAtivo("Paracetamol");
        medicamentoDTO.setFabricante("Genérico");
        medicamentoDTO.setDosagem("500mg");
    }

    @Test
    void cadastrar_deveRetornarCreated() {
        when(useCase.cadastrar(any(MedicamentoDTO.class))).thenReturn(medicamentoDTO);

        ResponseEntity<MedicamentoDTO> response = controller.cadastrar(medicamentoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(medicamentoDTO, response.getBody());
        verify(useCase, times(1)).cadastrar(any(MedicamentoDTO.class));
    }

    @Test
    void listarTodos_deveRetornarOk() {
        List<MedicamentoDTO> lista = Collections.singletonList(medicamentoDTO);
        when(useCase.listarTodos()).thenReturn(lista);

        ResponseEntity<List<MedicamentoDTO>> response = controller.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lista, response.getBody());
        verify(useCase, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_deveRetornarOk() {
        when(useCase.buscarPorId(1L)).thenReturn(medicamentoDTO);

        ResponseEntity<MedicamentoDTO> response = controller.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentoDTO, response.getBody());
        verify(useCase, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizar_deveRetornarOk() {
        when(useCase.atualizar(1L, medicamentoDTO)).thenReturn(medicamentoDTO);

        ResponseEntity<MedicamentoDTO> response = controller.atualizar(1L, medicamentoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentoDTO, response.getBody());
        verify(useCase, times(1)).atualizar(1L, medicamentoDTO);
    }

    @Test
    void deletar_deveRetornarNoContent() {
        doNothing().when(useCase).deletar(1L);

        ResponseEntity<Void> response = controller.deletar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase, times(1)).deletar(1L);
    }
}
