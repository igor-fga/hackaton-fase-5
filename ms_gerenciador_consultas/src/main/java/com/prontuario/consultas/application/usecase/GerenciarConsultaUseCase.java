package com.prontuario.consultas.application.usecase;

import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class GerenciarConsultaUseCase {
    private final ConsultaRepository consultaRepository;

    public GerenciarConsultaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta agendarConsulta(Consulta consulta) {
        return this.consultaRepository.save(consulta);
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
}
