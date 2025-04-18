package com.prontuario.consultas.application.usecase;

import com.prontuario.consultas.application.dto.DisponibilidadeResponse;
import com.prontuario.consultas.application.dto.VerificarDisponibilidadeRequest;
import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import com.prontuario.consultas.domain.entity.Medico;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GerenciarDisponibilidadeUseCaseTest {

    @Mock
    private HorarioTrabalhoRepository horarioTrabalhoRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private GerenciarDisponibilidadeUseCase gerenciarDisponibilidadeUseCase;

    private Medico medico;
    private HorarioTrabalho horarioTrabalho;
    private LocalDate dataConsulta;
    private String diaSemana;
    private VerificarDisponibilidadeRequest request;

    @BeforeEach
    void setUp() {
        // Configurando data para uma terça-feira, por exemplo
        dataConsulta = LocalDate.of(2025, 4, 15);
        diaSemana = dataConsulta.getDayOfWeek()
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
        horarioTrabalho.setHoraFim(LocalTime.of(12, 0)); // 4 horas = 8 slots de 30 min

        // Configurando request
        request = new VerificarDisponibilidadeRequest();
        request.setMedicoId(medico.getId());
        request.setData(dataConsulta);
    }

    @Test
    @DisplayName("Deve retornar horários disponíveis quando médico atende no dia")
    void deveRetornarHorariosDisponiveisQuandoMedicoAtendeNoDia() {
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.of(horarioTrabalho));
        when(consultaRepository.findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        DisponibilidadeResponse response = gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);

        assertNotNull(response);
        assertEquals(medico.getId(), response.getMedicoId());
        assertEquals(medico.getNome(), response.getNomeMedico());
        assertEquals(dataConsulta, response.getData());
        assertEquals(8, response.getHorariosDisponiveis().size()); // 8 slots de 30 min em 4 horas

        LocalTime expectedTime = LocalTime.of(8, 0);
        for (LocalTime time : response.getHorariosDisponiveis()) {
            assertEquals(expectedTime, time);
            expectedTime = expectedTime.plusMinutes(30);
        }

        verify(medicoRepository).findById(medico.getId());
        verify(horarioTrabalhoRepository).findByMedicoIdAndDiaSemana(medico.getId(), diaSemana);
        verify(consultaRepository).findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando médico não atende no dia")
    void deveRetornarListaVaziaQuandoMedicoNaoAtendeNoDia() {
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.empty());

        DisponibilidadeResponse response = gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);

        assertNotNull(response);
        assertEquals(medico.getId(), response.getMedicoId());
        assertEquals(medico.getNome(), response.getNomeMedico());
        assertEquals(dataConsulta, response.getData());
        assertTrue(response.getHorariosDisponiveis().isEmpty());

        verify(medicoRepository).findById(medico.getId());
        verify(horarioTrabalhoRepository).findByMedicoIdAndDiaSemana(medico.getId(), diaSemana);
        verify(consultaRepository, never()).findByMedicoIdAndDataHoraBetween(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando médico não for encontrado")
    void deveLancarExcecaoQuandoMedicoNaoForEncontrado() {
        when(medicoRepository.findById(999L)).thenReturn(Optional.empty());
        request.setMedicoId(999L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);
        });
        assertEquals("Médico não encontrado", exception.getMessage());
        verify(medicoRepository).findById(999L);
        verify(horarioTrabalhoRepository, never()).findByMedicoIdAndDiaSemana(anyLong(), anyString());
    }

    @Test
    @DisplayName("Deve remover horários ocupados da lista de disponíveis")
    void deveRemoverHorariosOcupadosDaListaDeDisponiveis() {
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.of(horarioTrabalho));

        Consulta consultaExistente = new Consulta();
        consultaExistente.setId(1L);
        consultaExistente.setMedico(medico);
        consultaExistente.setDataHora(LocalDateTime.of(dataConsulta, LocalTime.of(9, 0)));

        when(consultaRepository.findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(consultaExistente));

        DisponibilidadeResponse response = gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);

        assertNotNull(response);
        assertEquals(7, response.getHorariosDisponiveis().size()); // 8 slots originais - 1 ocupado
        assertFalse(response.getHorariosDisponiveis().contains(LocalTime.of(9, 0)));

        List<LocalTime> expectedTimes = List.of(
                LocalTime.of(8, 0),
                LocalTime.of(8, 30),
                // 9:00 está ocupado
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30)
        );

        assertEquals(expectedTimes, response.getHorariosDisponiveis());
    }

    @Test
    @DisplayName("Deve retornar dias disponíveis corretamente")
    void deveRetornarDiasDisponiveisCorretamente() {
        LocalDate inicio = LocalDate.of(2025, 4, 14); // Segunda-feira
        LocalDate fim = LocalDate.of(2025, 4, 18);    // Sexta-feira

        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));

        HorarioTrabalho horarioTerca = new HorarioTrabalho();
        horarioTerca.setMedico(medico);
        horarioTerca.setDiaSemana("TERÇA-FEIRA");

        HorarioTrabalho horarioQuinta = new HorarioTrabalho();
        horarioQuinta.setMedico(medico);
        horarioQuinta.setDiaSemana("QUINTA-FEIRA");

        when(horarioTrabalhoRepository.findByMedicoId(medico.getId()))
                .thenReturn(List.of(horarioTerca, horarioQuinta));

        List<LocalDate> diasDisponiveis = gerenciarDisponibilidadeUseCase
                .verificarDiasDisponiveis(medico.getId(), inicio, fim);

        assertEquals(2, diasDisponiveis.size());

        List<String> diasSemana = diasDisponiveis.stream()
                .map(data -> data.getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                        .toUpperCase())
                .collect(Collectors.toList());

        assertTrue(diasSemana.contains("TERÇA-FEIRA"));
        assertTrue(diasSemana.contains("QUINTA-FEIRA"));

        assertTrue(diasDisponiveis.contains(LocalDate.of(2025, 4, 15))); // Terça
        assertTrue(diasDisponiveis.contains(LocalDate.of(2025, 4, 17))); // Quinta
    }

    @Test
    @DisplayName("Deve lançar exceção ao verificar dias disponíveis para médico inexistente")
    void deveLancarExcecaoAoVerificarDiasDisponiveisParaMedicoInexistente() {
        LocalDate inicio = LocalDate.of(2025, 4, 14);
        LocalDate fim = LocalDate.of(2025, 4, 18);

        when(medicoRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gerenciarDisponibilidadeUseCase.verificarDiasDisponiveis(999L, inicio, fim);
        });

        assertEquals("Médico não encontrado", exception.getMessage());
        verify(medicoRepository).findById(999L);
        verify(horarioTrabalhoRepository, never()).findByMedicoId(anyLong());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há dias disponíveis no período")
    void deveRetornarListaVaziaQuandoNaoHaDiasDisponiveisNoPeriodo() {
        // Definir um período de fim de semana (sábado e domingo)
        LocalDate inicio = LocalDate.of(2025, 4, 19); // Sábado
        LocalDate fim = LocalDate.of(2025, 4, 20);    // Domingo

        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));

        // Configurar horário apenas para terça
        HorarioTrabalho horarioTerca = new HorarioTrabalho();
        horarioTerca.setMedico(medico);
        horarioTerca.setDiaSemana("TERÇA-FEIRA");

        when(horarioTrabalhoRepository.findByMedicoId(medico.getId()))
                .thenReturn(List.of(horarioTerca));

        List<LocalDate> diasDisponiveis = gerenciarDisponibilidadeUseCase
                .verificarDiasDisponiveis(medico.getId(), inicio, fim);

        assertTrue(diasDisponiveis.isEmpty());

        verify(medicoRepository).findById(medico.getId());
        verify(horarioTrabalhoRepository).findByMedicoId(medico.getId());
    }

    @Test
    @DisplayName("Deve calcular corretamente horários com múltiplas consultas agendadas")
    void deveCalcularCorretamenteHorariosComMultiplasConsultasAgendadas() {
        // Arrange
        when(medicoRepository.findById(medico.getId())).thenReturn(Optional.of(medico));
        when(horarioTrabalhoRepository.findByMedicoIdAndDiaSemana(medico.getId(), diaSemana))
                .thenReturn(Optional.of(horarioTrabalho));

        // Criar múltiplas consultas já agendadas (8:30, 10:00, 11:30)
        Consulta consulta1 = new Consulta();
        consulta1.setMedico(medico);
        consulta1.setDataHora(LocalDateTime.of(dataConsulta, LocalTime.of(8, 30)));

        Consulta consulta2 = new Consulta();
        consulta2.setMedico(medico);
        consulta2.setDataHora(LocalDateTime.of(dataConsulta, LocalTime.of(10, 0)));

        Consulta consulta3 = new Consulta();
        consulta3.setMedico(medico);
        consulta3.setDataHora(LocalDateTime.of(dataConsulta, LocalTime.of(11, 30)));

        when(consultaRepository.findByMedicoIdAndDataHoraBetween(
                eq(medico.getId()), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(consulta1, consulta2, consulta3));

        DisponibilidadeResponse response = gerenciarDisponibilidadeUseCase.verificarDisponibilidade(request);

        assertNotNull(response);
        assertEquals(5, response.getHorariosDisponiveis().size()); // 8 slots originais - 3 ocupados

        // Verificar se os horários esperados estão presentes
        List<LocalTime> expectedTimes = List.of(
                LocalTime.of(8, 0),
                // 8:30 está ocupado
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                // 10:00 está ocupado
                LocalTime.of(10, 30),
                LocalTime.of(11, 0)
                // 11:30 está ocupado
        );

        assertEquals(expectedTimes, response.getHorariosDisponiveis());
    }
}