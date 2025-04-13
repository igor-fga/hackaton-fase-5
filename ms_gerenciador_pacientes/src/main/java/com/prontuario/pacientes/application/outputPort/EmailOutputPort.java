package com.prontuario.pacientes.application.outputPort;

public interface EmailOutputPort {

    void sendEmail(String to, String subject, String body);
}

