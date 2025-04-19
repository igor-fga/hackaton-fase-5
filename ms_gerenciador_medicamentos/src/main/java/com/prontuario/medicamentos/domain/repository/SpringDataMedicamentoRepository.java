package com.prontuario.medicamentos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.prontuario.medicamentos.domain.entity.Medicamento;

public interface SpringDataMedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByNomeContainingIgnoreCase(String nome);


}