package com.prontuario.consultas.application.usecase;

import com.prontuario.consultas.application.dto.ConsultaRequest;
import com.prontuario.consultas.application.dto.ConsultaResponse;
import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import com.prontuario.consultas.domain.entity.Medico;
import com.prontuario.consultas.domain.entity.TipoConsulta;
import com.prontuario.consultas.domain.repository.ConsultaRepository;
import com.prontuario.consultas.domain.repository.HorarioTrabalhoRepository;
import com.prontuario.consultas.domain.repository.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerenciarConsultaUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private HorarioTrabalhoRepository horarioTrabalhoRepository;

    @InjectMocks
    private GerenciarConsultaUseCase gerenciarConsultaUseCase;

    private Medico medico;
    private Consulta consulta;
    private ConsultaRequest consultaRequest;
    private HorarioTrabalho horarioTrabalho;
    private LocalDateTime dataHoraConsulta;
    private String diaSemana;

    @BeforeEach
    void setUp() {
        // Configurando data e horário para um dia útil (ex: terça-feira)
        dataHoraConsulta = LocalDateTime.of(2025, 4, 15, 14, 30);
        diaSemana = dataHoraConsulta.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();

        // Configurando médico
        medico = new Medico();
        medico.setId(1L);
        medico.setNome("Dr. João Silva");
        medico.setEspecialidade("Cardiologia");

        // Configurando horário de trabalho
        horarioTrabalho = new HorarioTrabalho();
        horarioTrabalho.setId(1L);
        horarioTrabalho.setMedico(medico);
        horarioTrabalho.setDiaSemana(diaSemana);
        horarioTrabalho.setHoraInicio(LocalTime.of(8, 0));
        horarioTrabalho.setHoraFim(LocalTime.of(18, 0));

        // Configurando consulta
        consulta = new Consulta();
        consulta.setId(1L);
        consulta.setPacienteId(100L);
        consulta.setTipoConsulta(TipoConsulta.RETORNO);
        consulta.setDataHora(dataHoraConsulta);
        consulta.setEspecialidade("Cardiologia");
        consulta.setMedico(medico);
        consulta.setMotivoConsulta("Dor no peito");
        consulta.setAnamnese("Paciente relata dor no peito há 2 dias");
        consulta.setDiagnostico("Angina estável");
        consulta.setObservacoes("Realizar exames complementares");

        // Configurando request de consulta
        consultaRequest = new ConsultaRequest();
        consultaRequest.setPacienteId(100L);
        consultaRequest.setMedicoId(medico.getId());
        consultaRequest.setTipoConsulta(TipoConsulta.RETORNO);
        consultaRequest.setDataHora(dataHoraConsulta);
        consultaRequest.setEspecialidade("Cardiologia");
        consultaRequest.setMotivoConsulta("Dor no peito");
        consultaRequest.setAnamnese("Paciente relata dor no peito há 2 dias");
        consultaRequest.setDiagnostico("Angina estável");
        consultaRequest.setObservacoes("Realizar exames complementares");
    }


    @Test
    @DisplayName("Deve encontrar consulta quando buscar por ID existente")
    void buscarConsultaPorId_deveRetornarConsulta_quandoExistir() {
        Long consultaId = 1L;
        Consulta consultaEsperada = new Consulta();
        consultaEsperada.setId(consultaId);

        when(consultaRepository.findById(consultaId)).thenReturn(Optional.of(consultaEsperada));

        Optional<Consulta> resultado = gerenciarConsultaUseCase.buscarConsultaPorId(consultaId);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(consultaId);
        verify(consultaRepository).findById(consultaId);
    }

    @Test
    @DisplayName("Deve retornar vazio quando buscar por ID inexistente")
    void deveRetornarVazioQuandoBuscarPorIdInexistente() {
        when(consultaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Consulta> resultado = gerenciarConsultaUseCase.buscarConsultaPorId(999L);

        assertFalse(resultado.isPresent());
        verify(consultaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve retornar consultas de um paciente")
    void deveRetornarConsultasDePaciente() {
        List<Consulta> consultas = List.of(consulta);
        when(consultaRepository.findByPacienteId(100L)).thenReturn(consultas);

        List<Consulta> resultado = gerenciarConsultaUseCase.buscarConsultasPorPacienteId(100L);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(consulta, resultado.get(0));
        verify(consultaRepository, times(1)).findByPacienteId(100L);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando paciente não tem consultas")
    void deveRetornarListaVaziaQuandoPacienteNaoTemConsultas() {
        when(consultaRepository.findByPacienteId(999L)).thenReturn(new ArrayList<>());

        List<Consulta> resultado = gerenciarConsultaUseCase.buscarConsultasPorPacienteId(999L);

        assertTrue(resultado.isEmpty());
        verify(consultaRepository, times(1)).findByPacienteId(999L);
    }

    @Test
    @DisplayName("Deve retornar consultas do período especificado")
    void deveRetornarConsultasDoPeriodoEspecificado() {
        LocalDateTime inicio = LocalDateTime.of(2025, 4, 15, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 4, 15, 23, 59);
        List<Consulta> consultas = List.of(consulta);

        when(consultaRepository.findByPeriodo(inicio, fim)).thenReturn(consultas);

        List<Consulta> resultado = gerenciarConsultaUseCase.buscarConsultasPorPeriodo(inicio, fim);

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(consulta, resultado.get(0));
        verify(consultaRepository, times(1)).findByPeriodo(inicio, fim);
    }

    @Test
    @DisplayName("Deve cancelar consulta com sucesso")
    void deveCancelarConsultaComSucesso() {
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        doNothing().when(consultaRepository).delete(1L);

        boolean resultado = gerenciarConsultaUseCase.cancelarConsulta(1L);

        assertTrue(resultado);
        verify(consultaRepository, times(1)).findById(1L);
        verify(consultaRepository, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Deve retornar falso ao tentar cancelar consulta inexistente")
    void deveRetornarFalsoAoTentarCancelarConsultaInexistente() {
        when(consultaRepository.findById(999L)).thenReturn(Optional.empty());

        boolean resultado = gerenciarConsultaUseCase.cancelarConsulta(999L);

        assertFalse(resultado);
        verify(consultaRepository, times(1)).findById(999L);
        verify(consultaRepository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("Deve criar consulta com sucesso")
    void deveCriarConsultaComSucesso() {
        // Arrange
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.of(horarioTrabalho));
        when(consultaRepository.findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        ConsultaResponse response = gerenciarConsultaUseCase.criarConsulta(consultaRequest);

        assertNotNull(response);
        assertEquals(consulta.getId(), response.getId());
        assertEquals(consulta.getPacienteId(), response.getPacienteId());
        assertEquals(consulta.getMedico().getNome(), response.getNomeMedico());
        verify(medicoRepository, times(1)).findById(medico.getId());
        verify(horarioTrabalhoRepository, times(1)).findByMedicoIdAndDiaSemana(medico.getId(), diaSemana);
        verify(consultaRepository, times(1)).findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(consultaRepository, times(1)).save(any(Consulta.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando médico não for encontrado")
    void deveLancarExcecaoQuandoMedicoNaoForEncontrado() {
        when(medicoRepository.findById(999L)).thenReturn(Optional.empty());
        consultaRequest.setMedicoId(999L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarConsultaUseCase.criarConsulta(consultaRequest);
        });
        assertEquals("Médico não encontrado", exception.getMessage());
        verify(medicoRepository, times(1)).findById(999L);
        verify(horarioTrabalhoRepository, never()).findByMedicoIdAndDiaSemana(anyLong(), anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção quando médico não atender no dia da semana")
    void deveLancarExcecaoQuandoMedicoNaoAtenderNoDiaDaSemana() {
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarConsultaUseCase.criarConsulta(consultaRequest);
        });
        assertEquals("Médico não atende neste dia da semana", exception.getMessage());
        verify(medicoRepository, times(1)).findById(medico.getId());
        verify(horarioTrabalhoRepository, times(1)).findByMedicoIdAndDiaSemana(medico.getId(), diaSemana);
    }

    @Test
    @DisplayName("Deve lançar exceção quando horário estiver fora do expediente")
    void deveLancarExcecaoQuandoHorarioEstiverForaDoExpediente() {
        // Arrange
        LocalDateTime horarioForaExpediente = LocalDateTime.of(2025, 4, 15, 7, 0); // 7h da manhã
        consultaRequest.setDataHora(horarioForaExpediente);

        String diaSemanaRequest = horarioForaExpediente.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();

        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemanaRequest))
                .thenReturn(Optional.of(horarioTrabalho));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarConsultaUseCase.criarConsulta(consultaRequest);
        });
        assertEquals("Horário fora do expediente do médico", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando horário já estiver ocupado")
    void deveLancarExcecaoQuandoHorarioJaEstiverOcupado() {
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.of(horarioTrabalho));
        when(consultaRepository.findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(consulta)); // Horário já ocupado

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarConsultaUseCase.criarConsulta(consultaRequest);
        });
        assertEquals("Horário já ocupado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar consulta com sucesso")
    void deveSalvarConsultaComSucesso() {
        when(consultaRepository.save(consulta)).thenReturn(consulta);

        Consulta resultado = gerenciarConsultaUseCase.salvarConsulta(consulta);

        assertNotNull(resultado);
        assertEquals(consulta, resultado);
        verify(consultaRepository, times(1)).save(consulta);
    }
}