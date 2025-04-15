package com.prontuario.consultas.application.usecase;

import com.prontuario.consultas.application.dto.DisponibilidadeResponse;
import com.prontuario.consultas.application.dto.VerificarDisponibilidadeRequest;
import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import com.prontuario.consultas.domain.entity.Medico;
import com.prontuario.consultas.domain.repository.ConsultaRepository;
import com.prontuario.consultas.domain.repository.HorarioTrabalhoRepository;
import com.prontuario.consultas.domain.repository.MedicoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class GerenciarDisponibilidadeUseCase {

    private static final int DURACAO_CONSULTA_MINUTOS = 30;

    private final HorarioTrabalhoRepository horarioTrabalhoRepository;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    public GerenciarDisponibilidadeUseCase(
            HorarioTrabalhoRepository horarioTrabalhoRepository,
            MedicoRepository medicoRepository,
            ConsultaRepository consultaRepository) {
        this.horarioTrabalhoRepository = horarioTrabalhoRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
    }


    public DisponibilidadeResponse verificarDisponibilidade(VerificarDisponibilidadeRequest request) {

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        String diaSemana = request.getData().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();

        Optional<HorarioTrabalho> horarioTrabalhoOpt = horarioTrabalhoRepository
                .findByMedicoIdAndDiaSemana(medico.getId(), diaSemana);

        if (horarioTrabalhoOpt.isEmpty()) {
            return new DisponibilidadeResponse(
                    medico.getId(),
                    medico.getNome(),
                    request.getData(),
                    new ArrayList<>()
            );
        }

        HorarioTrabalho horarioTrabalho = horarioTrabalhoOpt.get();

        List<LocalTime> horariosDisponiveis = calcularHorariosDisponiveis(
                medico.getId(),
                request.getData(),
                horarioTrabalho.getHoraInicio(),
                horarioTrabalho.getHoraFim()
        );

        return new DisponibilidadeResponse(
                medico.getId(),
                medico.getNome(),
                request.getData(),
                horariosDisponiveis
        );
    }

    private List<LocalTime> calcularHorariosDisponiveis(Long medicoId, LocalDate data,
                                                        LocalTime horaInicio, LocalTime horaFim) {
        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        // Gerar todos os horários possíveis com intervalo de DURACAO_CONSULTA_MINUTOS
        LocalTime horarioAtual = horaInicio;
        while (horarioAtual.plusMinutes(DURACAO_CONSULTA_MINUTOS).isBefore(horaFim) ||
                horarioAtual.plusMinutes(DURACAO_CONSULTA_MINUTOS).equals(horaFim)) {
            horariosDisponiveis.add(horarioAtual);
            horarioAtual = horarioAtual.plusMinutes(DURACAO_CONSULTA_MINUTOS);
        }

        // Buscar consultas já agendadas para o médico neste dia
        LocalDateTime inicioDia = LocalDateTime.of(data, LocalTime.MIN);
        LocalDateTime fimDia = LocalDateTime.of(data, LocalTime.MAX);

        List<Consulta> consultasExistentes = consultaRepository
                .findByMedicoIdAndDataHoraBetween(medicoId, inicioDia, fimDia);

        // Remover horários já ocupados com consultas
        consultasExistentes.forEach(consulta -> {
            LocalTime horarioConsulta = consulta.getDataHora().toLocalTime();
            horariosDisponiveis.remove(horarioConsulta);
        });

        return horariosDisponiveis;
    }
}
