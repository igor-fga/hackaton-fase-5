package com.prontuario.pacientes.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Paciente {

    @Id
    @GeneratedValue()
    private UUID id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String genero;

    @CPF
    private String cpf;

    @Column(unique = true)
    private String numeroProntuario;

    @Email
    private String email;
    private String endereco;
    private String contato;
}

