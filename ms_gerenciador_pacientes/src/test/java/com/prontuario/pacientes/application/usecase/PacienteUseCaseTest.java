package com.prontuario.pacientes.application.usecase;

import com.prontuario.pacientes.application.inputPort.SendEmailInpuPort;
import com.prontuario.pacientes.domain.entity.Paciente;
import com.prontuario.pacientes.domain.exceptions.ControllerNotFoundException;
import com.prontuario.pacientes.domain.exceptions.CpfJaExisteException;
import com.prontuario.pacientes.domain.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteUseCaseTest {
    private PacienteRepository pacienteRepository;
    private SendEmailInpuPort sendEmailInpuPort;
    private PacienteUseCase pacienteUseCase;

    @BeforeEach
    void setUp() {
        pacienteRepository = mock(PacienteRepository.class);
        sendEmailInpuPort = mock(SendEmailInpuPort.class);
        pacienteUseCase = new PacienteUseCase(pacienteRepository, sendEmailInpuPort);
    }

    @Test
    void criarPaciente_comSucesso() {
        Paciente paciente = getPaciente();

        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(false);
        when(pacienteRepository.salvar(paciente)).thenReturn(paciente);

        Paciente result = pacienteUseCase.criarPaciente(paciente);

        assertEquals(paciente, result);
        verify(sendEmailInpuPort).sendEmail(eq(paciente.getEmail()), anyString(), contains(paciente.getNomeCompleto()));
    }

    @Test
    void criarPaciente_comCpfExistente_deveLancarExcecao() {
        Paciente paciente = getPaciente();
        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(true);

        assertThrows(CpfJaExisteException.class, () -> pacienteUseCase.criarPaciente(paciente));
    }

    @Test
    void alterarPaciente_comSucesso() {
        Paciente paciente = getPaciente();
        Paciente salvo = getPaciente();
        salvo.setNomeCompleto("Nome Antigo");

        when(pacienteRepository.buscarPorId(1L)).thenReturn(Optional.of(salvo));
        when(pacienteRepository.salvar(any())).thenReturn(paciente);

        Paciente result = pacienteUseCase.alterarPaciente(1L, paciente);

        assertEquals(paciente.getNomeCompleto(), result.getNomeCompleto());
        verify(sendEmailInpuPort).sendEmail(eq(paciente.getEmail()), contains("Dados atualizados"), contains(paciente.getNomeCompleto()));
    }

    @Test
    void alterarPaciente_naoEncontrado_deveLancarExcecao() {
        when(pacienteRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.alterarPaciente(1L, getPaciente()));
    }

    @Test
    void buscarPaciente_comSucesso() {
        Paciente paciente = getPaciente();
        when(pacienteRepository.buscarPorId(1L)).thenReturn(Optional.of(paciente));

        Paciente result = pacienteUseCase.buscarPaciente(1L);
        assertEquals(paciente, result);
    }

    @Test
    void buscarPaciente_naoEncontrado() {
        when(pacienteRepository.buscarPorId(1L)).thenReturn(Optional.empty());
        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.buscarPaciente(1L));
    }

    @Test
    void buscarPacientePorCpf_comSucesso() {
        Paciente paciente = getPaciente();
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(paciente));

        Paciente result = pacienteUseCase.buscarPacientePorCpf("12345678900");
        assertEquals(paciente, result);
    }

    @Test
    void buscarPacientePorCpf_naoEncontrado() {
        when(pacienteRepository.findByCpf("000"))
                .thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.buscarPacientePorCpf("000"));
    }

    @Test
    void listaPacientes_deveRetornarLista() {
        List<Paciente> lista = List.of(getPaciente());
        when(pacienteRepository.buscarTodos()).thenReturn(lista);

        List<Paciente> result = pacienteUseCase.listaPacientes();
        assertEquals(1, result.size());
    }

    @Test
    void excluirPaciente_deveChamarRepositorio() {
        pacienteUseCase.excluirPaciente(1L);
        verify(pacienteRepository).excluir(1L);
    }

    @Test
    void buscarPorNome_deveRetornarPagina() {
        List<Paciente> lista = List.of(getPaciente());
        Page<Paciente> page = new PageImpl<>(lista);
        Pageable pageable = PageRequest.of(0, 10);

        when(pacienteRepository.findByNomeCompletoContainingIgnoreCase("Joao", pageable))
                .thenReturn(page);

        Page<Paciente> result = pacienteUseCase.buscarPorNome("Joao", pageable);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void criarPaciente_comCpfDuplicado_deveLancarExcecao() {
        Paciente paciente = new Paciente();
        paciente.setCpf("12345678901");

        when(pacienteRepository.existsByCpf("12345678901")).thenReturn(true);

        assertThrows(CpfJaExisteException.class, () -> pacienteUseCase.criarPaciente(paciente));

        verify(pacienteRepository, times(1)).existsByCpf("12345678901");
        verifyNoMoreInteractions(pacienteRepository, sendEmailInpuPort);
    }

    @Test
    void alterarPaciente_inexistente_deveLancarExcecao() {
        Paciente paciente = new Paciente();
        Long id = 1L;

        when(pacienteRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.alterarPaciente(id, paciente));

        verify(pacienteRepository).buscarPorId(id);
        verifyNoMoreInteractions(pacienteRepository, sendEmailInpuPort);
    }

    @Test
    void buscarPaciente_inexistente_deveLancarExcecao() {
        when(pacienteRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.buscarPaciente(99L));
    }

    @Test
    void buscarPacientePorCpf_inexistente_deveLancarExcecao() {
        when(pacienteRepository.findByCpf("00000000000")).thenReturn(Optional.empty());

        assertThrows(ControllerNotFoundException.class, () -> pacienteUseCase.buscarPacientePorCpf("00000000000"));
    }


    private Paciente getPaciente() {
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomeCompleto("João da Silva");
        p.setDataNascimento(LocalDate.of(1990, 1, 1));
        p.setEmail("joao@email.com");
        p.setGenero("Masculino");
        p.setCpf("12345678900");
        p.setNumeroProntuario("PR123");
        p.setEndereco("Rua A, 123");
        p.setContato("(11) 99999-9999");
        return p;
    }
}