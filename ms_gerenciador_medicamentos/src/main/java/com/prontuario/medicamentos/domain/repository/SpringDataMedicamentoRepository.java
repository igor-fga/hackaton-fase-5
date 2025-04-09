package com.prontuario.medicamentos.domain.repository;

import com.prontuario.medicamentos.domain.entity.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {}
