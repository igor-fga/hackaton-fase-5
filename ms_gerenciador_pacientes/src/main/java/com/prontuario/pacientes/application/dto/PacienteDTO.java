package com.prontuario.pacientes.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PacienteDTO{

    private UUID id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String genero;
    private String cpf;
    private String numeroProntuario;
    private String email;
    private String endereco;
    private String contato;
}


