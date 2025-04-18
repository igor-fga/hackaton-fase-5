package com.prontuario.consultas.adapters.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErroResponse {
    private String mensagem;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErroResponse(String mensagem) {
        this.mensagem = mensagem;
    }
}
