package com.prontuario.pacientes.adapters.input.controlller;

import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import com.prontuario.pacientes.domain.entity.Paciente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PacienteUseCase pacienteUseCase;

    @GetMapping("/exportar-pacientes")
    public ResponseEntity<byte[]> exportarPacientesParaExcel() throws IOException {
        List<Paciente> pacientes = pacienteUseCase.listaPacientes();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pacientes");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nome Completo");
        headerRow.createCell(2).setCellValue("Data de Nascimento");
        headerRow.createCell(3).setCellValue("Gênero");
        headerRow.createCell(4).setCellValue("CPF");
        headerRow.createCell(5).setCellValue("Número do Prontuário");
        headerRow.createCell(6).setCellValue("Email");
        headerRow.createCell(7).setCellValue("Endereço");
        headerRow.createCell(8).setCellValue("Contato");

        int rowNum = 1;
        for (Paciente paciente : pacientes) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(paciente.getId());
            row.createCell(1).setCellValue(paciente.getNomeCompleto());
            row.createCell(2).setCellValue(paciente.getDataNascimento());
            row.createCell(3).setCellValue(paciente.getGenero());
            row.createCell(4).setCellValue(paciente.getCpf());
            row.createCell(5).setCellValue(paciente.getNumeroProntuario());
            row.createCell(6).setCellValue(paciente.getEmail());
            row.createCell(7).setCellValue(paciente.getEndereco());
            row.createCell(8).setCellValue(paciente.getContato());
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        byte[] excelData = byteArrayOutputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=pacientes.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }
}
