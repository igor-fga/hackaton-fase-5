package com.prontuario.consultas.adapters.repository.consulta;

import com.prontuario.consultas.domain.entity.Consulta;
import com.prontuario.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ConsultaRepositoryImpl implements ConsultaRepository {

    private final ConsultaJpaRepository consultaJpaRepository;

    public ConsultaRepositoryImpl(ConsultaJpaRepository consultaJpaRepository) {
        this.consultaJpaRepository = consultaJpaRepository;
    }

    @Override
    public Consulta save(Consulta consulta) {
        return consultaJpaRepository.save(consulta);
    }

    @Override
    public Optional<Consulta> findById(Long id) {
        return consultaJpaRepository.findById(id);
    }

    @Override
    public List<Consulta> findByPacienteId(Long pacienteId) {
        return consultaJpaRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<Consulta> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return consultaJpaRepository.findByDataHoraBetween(inicio, fim);
    }

    @Override
    public void delete(Long id) {
        consultaJpaRepository.deleteById(id);
    }

    @Override
    public List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicioDia, LocalDateTime fimDia) {
        return consultaJpaRepository.findByMedicoIdAndDataHoraBetween(medicoId, inicioDia, fimDia);
    }
}
