package com.prontuario.medicamentos.infrastructure.persistence.repository;

import com.prontuario.medicamentos.infrastructure.persistence.entity.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {}
