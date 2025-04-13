package com.prontuario.pacientes.application.usecase;

import com.prontuario.pacientes.application.inputPort.SendEmailInpuPort;
import com.prontuario.pacientes.application.outputPort.EmailOutputPort;
import org.springframework.stereotype.Service;

@Service
public class EmailUseCase implements SendEmailInpuPort {

    private final EmailOutputPort outputPort;

    public EmailUseCase(EmailOutputPort outputPort) {
        this.outputPort = outputPort;
    }
    @Override
    public void sendEmail(String to, String subject, String body) {
        outputPort.sendEmail(to, subject, body);
    }
}
