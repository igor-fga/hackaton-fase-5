package com.prontuario.pacientes.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PacienteDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testPacienteDTOValido() {
        PacienteDTO dto = new PacienteDTO(
                1L,
                "Maria Silva",
                LocalDate.of(1990, 5, 20),
                "Feminino",
                "390.533.447-05", // válido
                "1234567",
                "maria@email.com",
                "Rua das Flores, 123",
                "11999998888"
        );

        Set<ConstraintViolation<PacienteDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO válido não deve gerar violações");
    }

    @Test
    void testCamposObrigatoriosVazios() {
        PacienteDTO dto = new PacienteDTO(
                null,
                "", // nome
                null, // dataNascimento
                "Masculino",
                "390.533.447-05",
                "", // numeroProntuario
                "email@email.com",
                "", // endereco
                "" // contato
        );

        Set<ConstraintViolation<PacienteDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals(5, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nomeCompleto")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dataNascimento")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("numeroProntuario")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("endereco")));
    }

    @Test
    void testEmailInvalido() {
        PacienteDTO dto = new PacienteDTO(
                1L,
                "Carlos",
                LocalDate.of(1980, 3, 3),
                "Masculino",
                "390.533.447-05",
                "7654321",
                "email-invalido", // inválido
                "Rua XPTO",
                "11900000000"
        );

        Set<ConstraintViolation<PacienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testCpfInvalido() {
        PacienteDTO dto = new PacienteDTO(
                1L,
                "Carla",
                LocalDate.of(1992, 2, 2),
                "Feminino",
                "000.000.000-00",
                "1234567",
                "carla@email.com",
                "Rua 1",
                "11999999999"
        );

        Set<ConstraintViolation<PacienteDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")));
    }

}