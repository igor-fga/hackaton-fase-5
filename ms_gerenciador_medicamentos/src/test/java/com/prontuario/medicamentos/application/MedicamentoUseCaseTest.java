package com.prontuario.medicamentos.application;

import com.prontuario.medicamentos.adapters.mapper.MedicamentoMapper;
import com.prontuario.medicamentos.adapters.respository.MedicamentoRepository;
import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.application.usecase.MedicamentoUseCase;
import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.domain.exceptions.ControllerDatabaseException;
import com.prontuario.medicamentos.domain.exceptions.ControllerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicamentoUseCaseTest {

    @Mock
    private MedicamentoRepository repository;

    @Mock
    private MedicamentoMapper mapper;

    @InjectMocks
    private MedicamentoUseCase useCase;

    private MedicamentoDTO medicamentoDTO;
    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        medicamentoDTO = new MedicamentoDTO();
        medicamentoDTO.setId(1L);
        medicamentoDTO.setNome("Paracetamol");

        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Paracetamol");
    }

    @Test
    void cadastrar_deveRetornarMedicamentoDTO() {
        when(mapper.toEntity(medicamentoDTO)).thenReturn(medicamento);
        when(repository.salvar(medicamento)).thenReturn(medicamento);
        when(mapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        MedicamentoDTO result = useCase.cadastrar(medicamentoDTO);

        assertEquals(medicamentoDTO, result);
        verify(mapper, times(1)).toEntity(medicamentoDTO);
        verify(repository, times(1)).salvar(medicamento);
        verify(mapper, times(1)).toDTO(medicamento);
    }

    @Test
    void cadastrar_deveLancarExcecao() {
        when(mapper.toEntity(medicamentoDTO)).thenReturn(medicamento);
        when(repository.salvar(medicamento)).thenThrow(new RuntimeException());

        assertThrows(ControllerDatabaseException.class, () -> useCase.cadastrar(medicamentoDTO));
    }

    @Test
    void buscarPorId_deveRetornarMedicamentoDTO() {
        when(repository.buscarPorId(1L)).thenReturn(Optional.of(medicamento));
        when(mapper.toDTO(medicamento)).thenReturn(medicamentoDTO);

        MedicamentoDTO result = useCase.buscarPorId(1L);

        assertEquals(medicamentoDTO, result);
        verify(repository, times(1)).buscarPorId(1L);
        verify(mapper, times(1)).toDTO(medicamento);
    }

    @Test
    void buscarPorId_deveLancarExcecao() {
        when(repository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> useCase.buscarPorId(1L));
    }
}
