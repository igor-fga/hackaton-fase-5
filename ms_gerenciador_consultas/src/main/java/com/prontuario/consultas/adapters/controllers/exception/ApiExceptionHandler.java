package com.prontuario.consultas.adapters.controllers.exception;

import com.prontuario.consultas.adapters.controllers.dto.ErroResponse;
import com.prontuario.consultas.domain.exceptions.NegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErroResponse> handleNegocioException(NegocioException ex) {
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }
}
