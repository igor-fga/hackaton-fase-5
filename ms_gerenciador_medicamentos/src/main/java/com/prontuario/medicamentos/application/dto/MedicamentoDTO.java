package com.prontuario.medicamentos.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicamentoDTO {

    private Long id;
    private String nome;
    private String principioAtivo;
    private String fabricante;
    private String dosagem;
}