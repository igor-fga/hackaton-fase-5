package com.prontuario.pacientes.application.usecase;

import com.prontuario.pacientes.domain.entity.Paciente;
import com.prontuario.pacientes.domain.exceptions.ControllerNotFoundException;
import com.prontuario.pacientes.domain.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PacienteUseCase {

    private final PacienteRepository pacienteRepository;

    public PacienteUseCase(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente criarPaciente(Paciente paciente) {
        return pacienteRepository.salvar(paciente);
    }

    public Paciente alterarPaciente(UUID id, Paciente paciente) {
        Paciente savedPaciente = pacienteRepository.buscarPorId(id)
                .orElseThrow(()->new ControllerNotFoundException("Paciente não encontrado."));

        savedPaciente.setNomeCompleto(paciente.getNomeCompleto());
        savedPaciente.setDataNascimento(paciente.getDataNascimento());
        savedPaciente.setEmail(paciente.getEmail());
        savedPaciente.setGenero(paciente.getGenero());
        savedPaciente.setCpf(paciente.getCpf());
        savedPaciente.setNumeroProntuario(paciente.getNumeroProntuario());
        savedPaciente.setEndereco(paciente.getEndereco());
        savedPaciente.setContato(paciente.getContato());

        return pacienteRepository.salvar(savedPaciente);
    }

    public Paciente buscarPaciente(UUID id) {
        return pacienteRepository.buscarPorId(id)
                .orElseThrow(()->new ControllerNotFoundException("Paciente não encontrado."));
    }

    public List<Paciente> listaPacientes() {
        return pacienteRepository.buscarTodos();
    }

    public void excluirPaciente(UUID id) {
        pacienteRepository.excluir(id);
    }
}
