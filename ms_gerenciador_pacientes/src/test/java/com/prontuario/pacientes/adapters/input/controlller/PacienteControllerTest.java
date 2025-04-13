package com.prontuario.pacientes.adapters.input.controlller;

import com.prontuario.pacientes.adapters.mapper.PacienteMapper;
import com.prontuario.pacientes.application.dto.PacienteDTO;
import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import com.prontuario.pacientes.domain.entity.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class PacienteControllerTest {

    @Mock
    private PacienteUseCase pacienteUseCase;

    @Mock
    private PacienteMapper pacienteMapper;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criaPaciente_sucess(){
        PacienteDTO pacienteDTO = new PacienteDTO();
        Paciente paciente = new Paciente();
        Paciente pacienteCriado = new Paciente();

        when(pacienteMapper.toEntity(any(PacienteDTO.class))).thenReturn(paciente);
        when(pacienteUseCase.criarPaciente(any(Paciente.class))).thenReturn(pacienteCriado);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        ResponseEntity<PacienteDTO> response = pacienteController.criarPaciente(pacienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pacienteDTO, response.getBody());
    }

    @Test
    void alterarPaciente_sucess(){
        Long id = 1L;
        PacienteDTO pacienteDTO = new PacienteDTO();
        Paciente paciente = new Paciente();
        Paciente alteracaoDadosPaciente = new Paciente();
        PacienteDTO alteracaoDadosPacienteDTO = new PacienteDTO();

        when(pacienteMapper.toEntity(any(PacienteDTO.class))).thenReturn(paciente);
        when(pacienteUseCase.alterarPaciente(any(), any(Paciente.class))).thenReturn(alteracaoDadosPaciente);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(alteracaoDadosPacienteDTO);

        ResponseEntity<PacienteDTO> response = pacienteController.alterarPaciente(id, pacienteDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(alteracaoDadosPacienteDTO, response.getBody());
    }

    @Test
    void testListarPacientePorId(){
        Long id = 1L;
        PacienteDTO pacienteDTO = new PacienteDTO();
        Paciente paciente = new Paciente();

        when(pacienteUseCase.buscarPaciente(any())).thenReturn(paciente);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        ResponseEntity<PacienteDTO> response = pacienteController.buscarPaciente(id);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(pacienteDTO, response.getBody());
    }

    @Test
    void testListarPacientePorCpf() {
        String cpf = "72504540051";

        Paciente paciente = new Paciente(
                1L, "Artur Adriano Braz", LocalDate.of(1980, 2, 29), "MASCULINO",
                "72504540051", "1234556", "julianamariadalolio@gmail.com",
                "Rua da Paz, 2 - São Paulo, SP", "(11) 98990-9088"
        );

        PacienteDTO pacienteDTO = new PacienteDTO(
                1L, "Artur Adriano Braz", LocalDate.of(1980, 2, 29), "MASCULINO",
                "72504540051", "1234556", "julianamariadalolio@gmail.com",
                "Rua da Paz, 2 - São Paulo, SP", "(11) 98990-9088"
        );

        when(pacienteUseCase.buscarPacientePorCpf(any())).thenReturn(paciente);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        ResponseEntity<PacienteDTO> response = pacienteController.buscarPacientePorCpf(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pacienteDTO, response.getBody());
    }

    @Test
    void testListarTodosPacientes(){
        List<Paciente> pacientes = List.of(new Paciente(), new Paciente());
        List<PacienteDTO> pacientesDTO = List.of(new PacienteDTO(), new PacienteDTO());

        when(pacienteUseCase.listaPacientes()).thenReturn(pacientes);
        when(pacienteMapper.toDTOList(any(List.class))).thenReturn(pacientesDTO);

        ResponseEntity<List<PacienteDTO>> response = pacienteController.listaPacientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pacientesDTO, response.getBody());
    }

    @Test
    void testExcluirPaciente(){
        Long id = 1L;

        ResponseEntity<Void> response = pacienteController.excluirPaciente(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testBuscarPacientePorCpf_NaoEncontrado() {
        String cpfInexistente = "00000000000";

        when(pacienteUseCase.buscarPacientePorCpf(cpfInexistente)).thenReturn(null);

        ResponseEntity<PacienteDTO> response = pacienteController.buscarPacientePorCpf(cpfInexistente);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void criaPaciente_erroAoCadastrar() {
        PacienteDTO pacienteDTO = new PacienteDTO();
        Paciente paciente = new Paciente();

        when(pacienteMapper.toEntity(any(PacienteDTO.class))).thenReturn(paciente);
        when(pacienteUseCase.criarPaciente(any(Paciente.class)))
                .thenThrow(new RuntimeException("Erro ao cadastrar paciente"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pacienteController.criarPaciente(pacienteDTO);
        });

        assertEquals("Erro ao cadastrar paciente", exception.getMessage());
    }

    @Test
    void alterarPaciente_erroAoAlterar() {
        Long id = 1L;
        PacienteDTO pacienteDTO = new PacienteDTO();
        Paciente paciente = new Paciente();

        when(pacienteMapper.toEntity(any(PacienteDTO.class))).thenReturn(paciente);
        when(pacienteUseCase.alterarPaciente(eq(id), any(Paciente.class)))
                .thenThrow(new RuntimeException("Falha ao alterar paciente"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            pacienteController.alterarPaciente(id, pacienteDTO);
        });

        assertEquals("Falha ao alterar paciente", exception.getMessage());
    }

}
