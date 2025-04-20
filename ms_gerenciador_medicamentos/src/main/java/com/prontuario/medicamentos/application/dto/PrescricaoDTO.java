package com.prontuario.medicamentos.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescricaoDTO {

    private Long id;

    @NotNull(message = "O ID da consulta é obrigatório.")
    private Long consultaId;

    @NotNull(message = "O ID do paciente é obrigatório.")
    private Long pacienteId;

    @Valid
    @NotEmpty(message = "A lista de medicamentos não pode estar vazia.")
    private List<MedicamentoDTO> medicamentos;

    @NotBlank(message = "A posologia é obrigatória.")
    @Size(max = 255, message = "A posologia não pode exceder 255 caracteres.")
    private String posologia;

    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @NotNull(message = "A data de término é obrigatória.")
    private LocalDate dataTermino;

    @NotBlank(message = "As observações são obrigatórias.")
    @Size(max = 255, message = "As observações não podem exceder 255 caracteres.")
    private String observacoes;

    @NotNull(message = "O campo 'ativa' é obrigatório.")
    private Boolean ativa;
}
