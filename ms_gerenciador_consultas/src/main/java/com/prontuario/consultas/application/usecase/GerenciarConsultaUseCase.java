package com.prontuario.consultas.application.usecase;

import com.prontuario.consultas.application.dto.ConsultaRequest;
import com.prontuario.consultas.application.dto.ConsultaResponse;
import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.entity.HorarioTrabalho;
import com.prontuario.consultas.domain.entity.Medico;
import com.prontuario.consultas.domain.exceptions.HorarioForaExpedienteException;
import com.prontuario.consultas.domain.exceptions.HorarioOcupadoException;
import com.prontuario.consultas.domain.exceptions.MedicoNaoAtendeNesteDiaException;
import com.prontuario.consultas.domain.exceptions.MedicoNaoEncontradoException;
import com.prontuario.consultas.domain.repository.ConsultaRepository;
import com.prontuario.consultas.domain.repository.HorarioTrabalhoRepository;
import com.prontuario.consultas.domain.repository.MedicoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class GerenciarConsultaUseCase {
    private static final int DURACAO_CONSULTA_MINUTOS = 30;

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final HorarioTrabalhoRepository horarioTrabalhoRepository;

    public GerenciarConsultaUseCase(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
                                    HorarioTrabalhoRepository horarioTrabalhoRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.horarioTrabalhoRepository = horarioTrabalhoRepository;
    }


    public Optional<Consulta> buscarConsultaPorId(Long id) {
        return this.consultaRepository.findById(id);
    }

    public List<Consulta> buscarConsultasPorPacienteId(Long pacienteId) {
        return this.consultaRepository.findByPacienteId(pacienteId);
    }

    public List<Consulta> buscarConsultasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return this.consultaRepository.findByPeriodo(inicio, fim);
    }

    public boolean cancelarConsulta(Long id) {
        Optional<Consulta> consulta = this.consultaRepository.findById(id);
        if (consulta.isPresent()) {
            this.consultaRepository.delete(consulta.get().getId());
            return true;
        }
        return false;
    }

    public ConsultaResponse criarConsulta(ConsultaRequest request) {

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(MedicoNaoEncontradoException::new);

        LocalDate dataConsulta = request.getDataHora().toLocalDate();
        LocalTime horaConsulta = request.getDataHora().toLocalTime();

        String diaSemana = dataConsulta.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                .toUpperCase();

        HorarioTrabalho horarioTrabalho = horarioTrabalhoRepository
                .findByMedicoIdAndDiaSemana(medico.getId(), diaSemana)
                .orElseThrow(MedicoNaoAtendeNesteDiaException::new);

        if (horaConsulta.isBefore(horarioTrabalho.getHoraInicio()) ||
                horaConsulta.isAfter(horarioTrabalho.getHoraFim().minusMinutes(DURACAO_CONSULTA_MINUTOS))) {
            throw new HorarioForaExpedienteException();
        }

        LocalDateTime inicioBusca = request.getDataHora();
        LocalDateTime fimBusca = request.getDataHora().plusMinutes(DURACAO_CONSULTA_MINUTOS);

        List<Consulta> consultasExistentes = consultaRepository
                .findByMedicoIdAndDataHoraBetween(medico.getId(), inicioBusca, fimBusca);

        if (!consultasExistentes.isEmpty()) {
            throw new HorarioOcupadoException();
        }

        Consulta consulta = new Consulta();
        consulta.setPacienteId(request.getPacienteId());
        consulta.setTipoConsulta(request.getTipoConsulta());
        consulta.setDataHora(request.getDataHora());
        consulta.setEspecialidade(request.getEspecialidade());
        consulta.setMedico(medico);
        consulta.setMotivoConsulta(request.getMotivoConsulta());
        consulta.setAnamnese(request.getAnamnese());
        consulta.setDiagnostico(request.getDiagnostico());
        consulta.setObservacoes(request.getObservacoes());

        Consulta consultaSalva = consultaRepository.save(consulta);

        return new ConsultaResponse(
                consultaSalva.getId(),
                consultaSalva.getPacienteId(),
                consultaSalva.getTipoConsulta(),
                consultaSalva.getDataHora(),
                consultaSalva.getEspecialidade(),
                consultaSalva.getMedico().getId(),
                consultaSalva.getMedico().getNome(),
                consultaSalva.getMotivoConsulta(),
                consultaSalva.getAnamnese(),
                consultaSalva.getDiagnostico(),
                consultaSalva.getObservacoes()
        );
    }

    public Consulta salvarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public List<Medico> buscarMedicos(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {

            return medicoRepository.findAll();
        } else {

            return medicoRepository.findByEspecialidade(especialidade);
        }
    }
}
