package com.prontuario.medicamentos.application.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicamentoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 100, message = "Princípio ativo deve ter no máximo 100 caracteres")
    private String principioAtivo;

    @NotBlank(message = "Fabricante é obrigatório")
    @Size(max = 100, message = "Fabricante deve ter no máximo 100 caracteres")
    private String fabricante;

    @NotBlank(message = "Dosagem é obrigatória")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?[a-zA-Z]*$", message = "Formato de dosagem inválido (ex: 10mg)")
    private String dosagem;
}