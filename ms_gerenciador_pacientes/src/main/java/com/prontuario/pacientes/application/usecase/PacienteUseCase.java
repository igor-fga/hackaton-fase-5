package com.prontuario.pacientes.application.usecase;

import com.prontuario.pacientes.application.inputPort.SendEmailInpuPort;
import com.prontuario.pacientes.domain.entity.Paciente;
import com.prontuario.pacientes.domain.exceptions.ControllerNotFoundException;
import com.prontuario.pacientes.domain.exceptions.CpfJaExisteException;

import com.prontuario.pacientes.domain.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteUseCase {

    private final PacienteRepository pacienteRepository;
    private final SendEmailInpuPort sendEmailInpuPort;

    public PacienteUseCase(PacienteRepository pacienteRepository, SendEmailInpuPort sendEmailInpuPort) {
        this.pacienteRepository = pacienteRepository;
        this.sendEmailInpuPort = sendEmailInpuPort;
    }

    public Paciente criarPaciente(Paciente paciente) {

        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new CpfJaExisteException("Paciente já possui cadastro.");
        }
        Paciente novoPaciente = pacienteRepository.salvar(paciente);
        if (paciente.getEmail() != null && !paciente.getEmail().isBlank()) {
            sendEmailInpuPort.sendEmail(
                    paciente.getEmail(),
                    "Cadastro realizado com sucesso",
                    "Olá " + paciente.getNomeCompleto() + ", seu cadastro foi realizado com sucesso no sistema Prontuário Digital."
            );
        }
        return novoPaciente;
    }

     public Paciente alterarPaciente(Long id, Paciente paciente) {
         Paciente savedPaciente = pacienteRepository.buscarPorId(id)
                 .orElseThrow(() -> new ControllerNotFoundException("Paciente não encontrado."));

         savedPaciente.setNomeCompleto(paciente.getNomeCompleto());
         savedPaciente.setDataNascimento(paciente.getDataNascimento());
         savedPaciente.setEmail(paciente.getEmail());
         savedPaciente.setGenero(paciente.getGenero());
         savedPaciente.setCpf(paciente.getCpf());
         savedPaciente.setNumeroProntuario(paciente.getNumeroProntuario());
         savedPaciente.setEndereco(paciente.getEndereco());
         savedPaciente.setContato(paciente.getContato());
         Paciente pacienteAtualizado = pacienteRepository.salvar(savedPaciente);

         if (paciente.getEmail() != null && !paciente.getEmail().isBlank()) {
             sendEmailInpuPort.sendEmail(
                     paciente.getEmail(),
                     "Dados atualizados com sucesso",
                     "Olá " + paciente.getNomeCompleto() + ", seus dados foram atualizados com sucesso no sistema Prontuário Digital."
             );
         }
         return pacienteAtualizado;
     }

    public Paciente buscarPaciente(Long id) {
        return pacienteRepository.buscarPorId(id)
                .orElseThrow(()->new ControllerNotFoundException("Paciente não encontrado."));
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(()->new ControllerNotFoundException("Paciente não encontrado."));
    }

    public List<Paciente> listaPacientes() {
        return pacienteRepository.buscarTodos();
    }

    public void excluirPaciente(Long id) {
        pacienteRepository.excluir(id);
    }

    public Page<Paciente> buscarPorNome(String nomeCompleto, Pageable pageable) {
        return pacienteRepository.findByNomeCompletoContainingIgnoreCase(nomeCompleto, pageable);
    }
}
