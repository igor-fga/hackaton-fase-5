package com.prontuario.medicamentos.adapters.controllers;

import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.application.dto.PrescricaoDTO;
import com.prontuario.medicamentos.application.usecase.PrescricaoUseCase;
import com.prontuario.medicamentos.adapters.mapper.PrescricaoMapper;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrescricaoControllerTest {

    @Mock
    private PrescricaoUseCase useCase;

    @Mock
    private PrescricaoMapper mapper;

    @InjectMocks
    private PrescricaoController controller;

    private PrescricaoDTO dto;
    private Prescricao domain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        MedicamentoDTO medicamento = new MedicamentoDTO();
        medicamento.setId(1L);
        medicamento.setNome("Dipirona");

        dto = new PrescricaoDTO();
        dto.setId(1L);
        dto.setConsultaId(10L);
        dto.setPacienteId(20L);
        dto.setMedicamentos(List.of(medicamento));
        dto.setPosologia("1 comprimido a cada 8 horas");
        dto.setDataInicio(LocalDate.now());
        dto.setDataTermino(LocalDate.now().plusDays(5));
        dto.setObservacoes("Tomar após as refeições");
        dto.setAtiva(true);

        domain = new Prescricao();
        domain.setId(dto.getId());
        domain.setConsultaId(dto.getConsultaId());
        domain.setPacienteId(dto.getPacienteId());
        domain.setDataInicio(dto.getDataInicio());
        domain.setDataTermino(dto.getDataTermino());
        domain.setPosologia(dto.getPosologia());
        domain.setObservacoes(dto.getObservacoes());
        domain.setAtiva(dto.getAtiva());
    }

    @Test
    void deveCadastrarPrescricaoComSucesso() {
        when(mapper.toDomain(dto)).thenReturn(domain);
        when(useCase.salvar(domain)).thenReturn(domain);
        when(mapper.toDTO(domain)).thenReturn(dto);

        PrescricaoDTO resultado = controller.cadastrar(dto);

        assertEquals(dto, resultado);
        verify(mapper).toDomain(dto);
        verify(useCase).salvar(domain);
        verify(mapper).toDTO(domain);
    }

    @Test
    void deveListarPrescricoesPorPaciente() {
        when(useCase.listarPorPaciente(20L)).thenReturn(List.of(domain));
        when(mapper.toDTO(domain)).thenReturn(dto);

        List<PrescricaoDTO> resultado = controller.listarPorPaciente(20L);

        assertEquals(1, resultado.size());
        assertEquals(dto, resultado.get(0));
        verify(useCase).listarPorPaciente(20L);
    }

    @Test
    void deveListarPrescricoesAtivasPorPaciente() {
        when(useCase.listarAtivasPorPaciente(20L)).thenReturn(List.of(domain));
        when(mapper.toDTO(domain)).thenReturn(dto);

        List<PrescricaoDTO> resultado = controller.listarAtivas(20L);

        assertEquals(1, resultado.size());
        assertEquals(dto, resultado.get(0));
        verify(useCase).listarAtivasPorPaciente(20L);
    }
}

