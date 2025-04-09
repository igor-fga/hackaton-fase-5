package com.prontuario.medicamentos.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Princípio ativo é obrigatório")
    private String principioAtivo;

    private String fabricante;
    private String dosagem;
}
