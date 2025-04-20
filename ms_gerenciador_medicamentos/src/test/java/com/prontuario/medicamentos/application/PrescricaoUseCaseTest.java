package com.prontuario.medicamentos.application;

import com.prontuario.medicamentos.adapters.respository.PrescricaoRepository;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.entity.Prescricao;
import com.prontuario.medicamentos.application.usecase.PrescricaoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrescricaoUseCaseTest {

    private PrescricaoRepository repository;
    private PrescricaoUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(PrescricaoRepository.class);
        useCase = new PrescricaoUseCase(repository);
    }

    @Test
    void deveSalvarPrescricaoComMedicamentos() {
        // Arrange
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Dipirona");
        medicamento.setPrincipioAtivo("Dipirona");
        medicamento.setFabricante("Genérico");
        medicamento.setDosagem("500mg");

        Prescricao prescricao = new Prescricao(
                100L,
                200L,
                List.of(medicamento),
                "1 comprimido a cada 8h",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Sem restrições",
                true
        );

        Prescricao prescricaoSalva = new Prescricao(
                100L,
                200L,
                List.of(medicamento),
                "1 comprimido a cada 8h",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                "Sem restrições",
                true
        );
        prescricaoSalva.setId(10L);

        when(repository.salvar(prescricao)).thenReturn(prescricaoSalva);

        // Act
        Prescricao resultado = useCase.salvar(prescricao);

        // Assert
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        verify(repository, times(1)).salvar(prescricao);
    }

    @Test
    void naoDeveSalvarPrescricaoSemMedicamentos() {
        Prescricao prescricao = new Prescricao(
                101L,
                201L,
                Collections.emptyList(),
                "1 comprimido ao dia",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                "Tomar após as refeições",
                true
        );

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.salvar(prescricao);
        });

        assertEquals("A prescrição deve conter ao menos um medicamento.", ex.getMessage());
        verify(repository, never()).salvar(any());
    }

    @Test
    void deveListarPrescricoesPorPaciente() {
        Long pacienteId = 123L;

        when(repository.listarPorPaciente(pacienteId)).thenReturn(List.of(new Prescricao()));

        List<Prescricao> resultado = useCase.listarPorPaciente(pacienteId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).listarPorPaciente(pacienteId);
    }

    @Test
    void deveListarPrescricoesAtivasPorPaciente() {
        Long pacienteId = 456L;

        when(repository.listarAtivasPorPaciente(pacienteId)).thenReturn(List.of(new Prescricao()));

        List<Prescricao> resultado = useCase.listarAtivasPorPaciente(pacienteId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).listarAtivasPorPaciente(pacienteId);
    }
}
