package com.prontuario.pacientes.infrastructure.config;

import com.prontuario.pacientes.adapters.output.repository.service.EmailServiceAdapter;
import com.prontuario.pacientes.application.outputPort.EmailOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfiguration {

    @Bean
    public EmailOutputPort emailPort(JavaMailSender mailSender) {
        return new EmailServiceAdapter(mailSender);
    }
}
