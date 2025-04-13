package com.prontuario.pacientes.adapters.input.controlller;

import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import com.prontuario.pacientes.domain.entity.Paciente;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PacienteUseCase pacienteUseCase;

    @Test
    void deveExportarPacientesParaExcel() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNomeCompleto("João da Silva");
        paciente.setDataNascimento(LocalDate.of(1980,04,13));
        paciente.setGenero("Masculino");
        paciente.setCpf("123.456.789-00");
        paciente.setNumeroProntuario("PRT123");
        paciente.setEmail("joao@email.com");
        paciente.setEndereco("Rua A, 123");
        paciente.setContato("11999999999");

        Mockito.when(pacienteUseCase.listaPacientes()).thenReturn(List.of(paciente));

        byte[] responseBytes = mockMvc.perform(get("/admin/exportar-pacientes"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, containsString("attachment; filename=pacientes.xlsx")))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        Workbook workbook = WorkbookFactory.create(new java.io.ByteArrayInputStream(responseBytes));
        Sheet sheet = workbook.getSheetAt(0);

        assertEquals("ID", sheet.getRow(0).getCell(0).getStringCellValue());
        assertEquals("João da Silva", sheet.getRow(1).getCell(1).getStringCellValue());

        workbook.close();
    }
}
