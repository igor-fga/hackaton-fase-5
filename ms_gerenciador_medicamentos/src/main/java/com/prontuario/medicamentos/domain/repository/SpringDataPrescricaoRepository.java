package com.prontuario.medicamentos.domain.repository;

import com.prontuario.medicamentos.domain.entity.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataPrescricaoRepository extends JpaRepository<Prescricao, Long> {
    List<Prescricao> findByPacienteId(Long pacienteId);
    List<Prescricao> findByPacienteIdAndDataInicioLessThanEqualAndDataTerminoGreaterThanEqual(
            Long pacienteId, LocalDate inicio, LocalDate fim);

}