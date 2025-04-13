package com.prontuario.pacientes.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PacienteTest {

    @Test
    void testGettersAndSetters() {
        Paciente paciente = new Paciente();

        paciente.setId(1L);
        paciente.setNomeCompleto("João Silva");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));
        paciente.setGenero("Masculino");
        paciente.setCpf("123.456.789-09");
        paciente.setNumeroProntuario("1234567");
        paciente.setEmail("joao@example.com");
        paciente.setEndereco("Rua A, 123");
        paciente.setContato("11999999999");

        assertEquals(1L, paciente.getId());
        assertEquals("João Silva", paciente.getNomeCompleto());
        assertEquals(LocalDate.of(1990, 1, 1), paciente.getDataNascimento());
        assertEquals("Masculino", paciente.getGenero());
        assertEquals("123.456.789-09", paciente.getCpf());
        assertEquals("1234567", paciente.getNumeroProntuario());
        assertEquals("joao@example.com", paciente.getEmail());
        assertEquals("Rua A, 123", paciente.getEndereco());
        assertEquals("11999999999", paciente.getContato());
    }

    @Test
    void testConstructorAllArgs() {
        Paciente paciente = new Paciente(
                2L,
                "Maria Oliveira",
                LocalDate.of(1985, 5, 20),
                "Feminino",
                "987.654.321-00",
                "7654321",
                "maria@example.com",
                "Rua B, 456",
                "11988888888"
        );

        assertEquals(2L, paciente.getId());
        assertEquals("Maria Oliveira", paciente.getNomeCompleto());
        assertEquals(LocalDate.of(1985, 5, 20), paciente.getDataNascimento());
        assertEquals("Feminino", paciente.getGenero());
        assertEquals("987.654.321-00", paciente.getCpf());
        assertEquals("7654321", paciente.getNumeroProntuario());
        assertEquals("maria@example.com", paciente.getEmail());
        assertEquals("Rua B, 456", paciente.getEndereco());
        assertEquals("11988888888", paciente.getContato());
    }

    @Test
    void testEqualsAndHashCode() {
        Paciente p1 = new Paciente(1L, "João", LocalDate.now(), "M", "111.111.111-11", "0000001", "joao@mail.com", "Rua X", "111");
        Paciente p2 = new Paciente(1L, "João", LocalDate.now(), "M", "111.111.111-11", "0000001", "joao@mail.com", "Rua X", "111");

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testNotEquals() {
        Paciente p1 = new Paciente(1L, "João", LocalDate.now(), "M", "111.111.111-11", "0000001", "joao@mail.com", "Rua X", "111");
        Paciente p2 = new Paciente(2L, "Maria", LocalDate.now(), "F", "222.222.222-22", "0000002", "maria@mail.com", "Rua Y", "222");

        assertNotEquals(p1, p2);
    }

    @Test
    void testToString() {
        Paciente paciente = new Paciente(1L, "João", LocalDate.of(1990, 1, 1), "M", "123.456.789-00", "1234567", "joao@mail.com", "Rua Z", "11999999999");
        String expected = "Paciente(id=1, nomeCompleto=João, dataNascimento=1990-01-01, genero=M, cpf=123.456.789-00, numeroProntuario=1234567, email=joao@mail.com, endereco=Rua Z, contato=11999999999)";
        assertEquals(expected, paciente.toString());
    }
}