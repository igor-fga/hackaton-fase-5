package com.prontuario.medicamentos.domain.repository;

import com.prontuario.medicamentos.domain.entity.PrescricaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataPrescricaoRepository extends JpaRepository<PrescricaoEntity, Long> {
    List<PrescricaoEntity> findByPacienteId(Long pacienteId);
    List<PrescricaoEntity> findByPacienteIdAndDataInicioLessThanEqualAndDataTerminoGreaterThanEqual(Long pacienteId, LocalDate inicio, LocalDate fim);
}