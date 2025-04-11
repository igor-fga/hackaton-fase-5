package com.prontuario.medicamentos.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicamentoDTO {

    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 100, message = "Princípio ativo deve ter no máximo 100 caracteres")
    private String principioAtivo;

    @NotBlank(message = "Fabricante é obrigatório")
    @Size(max = 100, message = "Fabricante deve ter no máximo 100 caracteres")
    private String fabricante;

    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    private String dosagem;
}
