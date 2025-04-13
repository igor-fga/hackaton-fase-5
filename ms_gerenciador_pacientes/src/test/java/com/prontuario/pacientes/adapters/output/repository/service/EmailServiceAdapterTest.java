package com.prontuario.pacientes.adapters.output.repository.service;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceAdapterTest {

    private JavaMailSender mailSender;
    private EmailServiceAdapter emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailServiceAdapter(mailSender);
    }

    @Test
    void deveEnviarEmailComSucesso() throws Exception {

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        String to = "teste@dominio.com";
        String subject = "Assunto";
        String body = "<p>Corpo do e-mail</p>";

        emailService.sendEmail(to, subject, body);

        verify(mailSender).send(mimeMessage);
        verify(mailSender).createMimeMessage();
    }

    @Test
    void deveLancarExcecaoAoFalharEnvio() throws Exception {

        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Falha no envio"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                emailService.sendEmail("teste@dominio.com", "Erro", "Falha")
        );
        assertEquals("Erro ao enviar e-mail: Falha no envio", thrown.getMessage());
    }
}



