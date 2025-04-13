package com.prontuario.pacientes.adapters.input.controlller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prontuario.pacientes.application.dto.PacienteDTO;
import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import com.prontuario.pacientes.domain.entity.Paciente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PacienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PacienteUseCase pacienteUseCase;

    @Test
    void criarPaciente() throws Exception{
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setNomeCompleto("Anselmo Braz");
        pacienteDTO.setDataNascimento(LocalDate.of(1980,02,29));
        pacienteDTO.setGenero("MASCULINO");
        pacienteDTO.setCpf("74892294055");
        pacienteDTO.setNumeroProntuario("1230987");
        pacienteDTO.setEmail("julianamariadalolio@gmail.com");
        pacienteDTO.setEndereco("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP");
        pacienteDTO.setContato("11990672390");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pacienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nomeCompleto").value("Anselmo Braz"))
                .andExpect(jsonPath("$.dataNascimento").value("29/02/1980"))
                .andExpect(jsonPath("$.genero").value("MASCULINO"))
                .andExpect(jsonPath("$.cpf").value("74892294055"))
                .andExpect(jsonPath("$.numeroProntuario").value("1230987"))
                .andExpect(jsonPath("$.email").value("julianamariadalolio@gmail.com"))
                .andExpect(jsonPath("$.endereco").value("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP"))
                .andExpect(jsonPath("$.contato").value("11990672390"));
    }

    @Test
    void testListarPacientes() throws Exception {
        mockMvc.perform(get("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void alterarPaciente() throws Exception {

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setNomeCompleto("Anselmo Braz");
        pacienteDTO.setDataNascimento(LocalDate.of(1980, 2, 29));
        pacienteDTO.setGenero("MASCULINO");
        pacienteDTO.setCpf("74892294055");
        pacienteDTO.setNumeroProntuario("1230987");
        pacienteDTO.setEmail("julianamariadalolio@gmail.com");
        pacienteDTO.setEndereco("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP");
        pacienteDTO.setContato("11990672390");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pacienteDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        PacienteDTO pacienteCriado = objectMapper.readValue(jsonResponse, PacienteDTO.class);
        Long idCriado = pacienteCriado.getId();

        pacienteCriado.setNomeCompleto("Anselmo Braz Atualizado");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/pacientes/{id}", idCriado)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pacienteCriado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idCriado))
                .andExpect(jsonPath("$.nomeCompleto").value("Anselmo Braz Atualizado"))
                .andExpect(jsonPath("$.dataNascimento").value("29/02/1980"))
                .andExpect(jsonPath("$.genero").value("MASCULINO"))
                .andExpect(jsonPath("$.cpf").value("74892294055"))
                .andExpect(jsonPath("$.numeroProntuario").value("1230987"))
                .andExpect(jsonPath("$.email").value("julianamariadalolio@gmail.com"))
                .andExpect(jsonPath("$.endereco").value("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP"))
                .andExpect(jsonPath("$.contato").value("11990672390"));
    }

    @Test
    void testBuscarPaciente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNomeCompleto("Anselmo Braz");
        paciente.setDataNascimento(LocalDate.of(1980, 2, 29));
        paciente.setGenero("MASCULINO");
        paciente.setCpf("74892294055");
        paciente.setNumeroProntuario("1230987");
        paciente.setEmail("julianamariadalolio@gmail.com");
        paciente.setEndereco("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP");
        paciente.setContato("11990672390");

        paciente.setId(pacienteUseCase.criarPaciente(paciente).getId());

        mockMvc.perform(get("/api/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(paciente.getId().toString()))
                .andExpect(jsonPath("$.nomeCompleto").value("Anselmo Braz"))
                .andExpect(jsonPath("$.dataNascimento").value("29/02/1980"))
                .andExpect(jsonPath("$.genero").value("MASCULINO"))
                .andExpect(jsonPath("$.cpf").value("74892294055"))
                .andExpect(jsonPath("$.numeroProntuario").value("1230987"))
                .andExpect(jsonPath("$.email").value("julianamariadalolio@gmail.com"))
                .andExpect(jsonPath("$.endereco").value("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP"))
                .andExpect(jsonPath("$.contato").value("11990672390"));
    }


    @Test
    void testExcluirPaciente() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNomeCompleto("Anselmo Braz");
        paciente.setDataNascimento(LocalDate.of(1980, 2, 29));
        paciente.setGenero("MASCULINO");
        paciente.setCpf("74892294055");
        paciente.setNumeroProntuario("1230987");
        paciente.setEmail("julianamariadalolio@gmail.com");
        paciente.setEndereco("Rua Floriza Klein Vasconscelos, nro 38, Bairro: Vila Nova Osasco, Osasco, SP");
        paciente.setContato("11990672390");

        paciente.setId(pacienteUseCase.criarPaciente(paciente).getId());

        mockMvc.perform(delete("/api/pacientes/" + paciente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
