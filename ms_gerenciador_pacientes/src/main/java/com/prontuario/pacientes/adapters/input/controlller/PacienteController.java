package com.prontuario.pacientes.adapters.input.controlller;

import com.prontuario.pacientes.adapters.mapper.PacienteMapper;
import com.prontuario.pacientes.application.dto.PacienteDTO;
import com.prontuario.pacientes.application.usecase.PacienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pacientes", description = "Operações relacionadas aos pacientes")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteUseCase pacienteUseCase;
    private final PacienteMapper pacienteMapper;

    public PacienteController(PacienteUseCase pacienteUseCase, PacienteMapper pacienteMapper) {
        this.pacienteUseCase = pacienteUseCase;
        this.pacienteMapper = pacienteMapper;
    }

    @Operation(summary = "Criar novo paciente", description = "Cria um novo paciente com os dados informados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PacienteDTO> criarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        var paciente = pacienteMapper.toEntity(pacienteDTO);
        var pacienteCriado = pacienteUseCase.criarPaciente(paciente);
        return ResponseEntity.status(201).body(pacienteMapper.toDTO(pacienteCriado));
    }

    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados do paciente com o ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> alterarPaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        var paciente = pacienteMapper.toEntity(pacienteDTO);
        var pacienteCriado = pacienteUseCase.alterarPaciente(id, paciente);
        return ResponseEntity.ok(pacienteMapper.toDTO(pacienteCriado));
    }

    @Operation(summary = "Buscar paciente por ID", description = "Retorna os dados do paciente correspondente ao ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> buscarPaciente(@PathVariable Long id) {
        var paciente = pacienteUseCase.buscarPaciente(id);
        return ResponseEntity.ok(pacienteMapper.toDTO(paciente));
    }

    @Operation(summary = "Buscar todos os pacientes", description = "Este endpoint retorna todos os pacientes registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listaPacientes() {
        var pacientes = pacienteUseCase.listaPacientes();
        return ResponseEntity.ok(pacienteMapper.toDTOList(pacientes));
    }

    @Operation(summary = "Excluir paciente", description = "Exclui o paciente com o ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteUseCase.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar paciente por CPF", description = "Retorna os dados do paciente correspondente ao CPF.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "204", description = "Nenhum paciente encontrado com o CPF informado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PacienteDTO> buscarPacientePorCpf(@PathVariable String cpf) {
        var pacientes = pacienteUseCase.buscarPacientePorCpf(cpf);
        if (pacientes == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacienteMapper.toDTO(pacientes));
    }

    @Operation(summary = "Buscar pacientes por nome ordenado", description = "Retorna uma lista paginada de pacientes ordenada pelo nome.")
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso")
    })
    @GetMapping("/nome_ordenado")
    public Page<PacienteDTO> buscarPacientesPorNomeOrdenado(
            @RequestParam(defaultValue = "") String nome,
            @PageableDefault(size = 10, sort = "nomeCompleto") Pageable pageable) {
        return pacienteUseCase.buscarPorNome(nome, pageable)
                .map(pacienteMapper::toDTO);
    }
}
