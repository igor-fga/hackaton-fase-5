package com.prontuario.medicamentos.interfaces.rest.dto;

public class MedicamentoResponse {
    private Long id;
    private String nome;

    public MedicamentoResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
}
