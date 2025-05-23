package com.prontuario.pacientes.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO{

    private Long id;

    @NotBlank
    private String nomeCompleto;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String genero;

    @CPF
    private String cpf;

    @NotBlank
    private String numeroProntuario;

    @Email
    private String email;

    @NotBlank
    private String endereco;

    @NotBlank
    private String contato;
}


