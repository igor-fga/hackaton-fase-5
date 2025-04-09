package com.prontuario.medicamentos.domain.entity;

public class Medicamento {
    private Long id;
    private String nome;
    private String principioAtivo;
    private String fabricante;
    private String dosagem;

    public Medicamento(String nome, String principioAtivo, String fabricante, String dosagem) {
        this.nome = nome;
        this.principioAtivo = principioAtivo;
        this.fabricante = fabricante;
        this.dosagem = dosagem;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getPrincipioAtivo() { return principioAtivo; }
    public String getFabricante() { return fabricante; }
    public String getDosagem() { return dosagem; }
}