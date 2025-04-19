package com.prontuario.medicamentos.application.usecase;

import com.prontuario.medicamentos.domain.entity.Medicamento;
import com.prontuario.medicamentos.application.dto.MedicamentoDTO;
import com.prontuario.medicamentos.adapters.mapper.MedicamentoMapper;
import com.prontuario.medicamentos.adapters.respository.MedicamentoRepository;
import com.prontuario.medicamentos.domain.exceptions.ControllerDatabaseException;
import com.prontuario.medicamentos.domain.exceptions.ControllerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoUseCase {

    private final MedicamentoRepository repository;
    private final MedicamentoMapper mapper;

    public MedicamentoUseCase(MedicamentoRepository repository, MedicamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MedicamentoDTO cadastrar(MedicamentoDTO dto) {
        try {
            Medicamento medicamento = mapper.toEntity(dto);
            Medicamento salvo = repository.salvar(medicamento);
            return mapper.toDTO(salvo);
        } catch (Exception ex) {
            throw new ControllerDatabaseException("Erro ao salvar medicamento", ex);
        }
    }

    public List<MedicamentoDTO> listarTodos() {
        return repository.listarTodos().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MedicamentoDTO buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ControllerNotFoundException("Medicamento não encontrado"));
    }

    public MedicamentoDTO atualizar(Long id, MedicamentoDTO dto) {
        Medicamento existente = repository.buscarPorId(id)
                .orElseThrow(() -> new ControllerNotFoundException("Medicamento não encontrado"));

        Medicamento medicamento = mapper.toEntity(dto);
        medicamento.setId(existente.getId());

        Medicamento atualizado = repository.salvar(medicamento);
        return mapper.toDTO(atualizado);
    }

    public List<MedicamentoDTO> buscarPorNome(String nome) {
        return repository.buscarPorNome(nome).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        repository.deletarPorId(id);
    }
}