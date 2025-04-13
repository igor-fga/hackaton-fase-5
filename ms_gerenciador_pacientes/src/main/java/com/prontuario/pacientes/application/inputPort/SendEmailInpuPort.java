package com.prontuario.pacientes.application.inputPort;

public interface SendEmailInpuPort {

    void sendEmail(String to, String subject, String body);

}

