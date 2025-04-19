package com.prontuario.medicamentos.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Princípio ativo é obrigatório")
    private String principioAtivo;

    private String fabricante;
    private String dosagem;

    // Adicionando o construtor que não exige o 'id', pois será gerado automaticamente
    public Medicamento(String nome, String principioAtivo, String fabricante, String dosagem) {
        this.nome = nome;
        this.principioAtivo = principioAtivo;
        this.fabricante = fabricante;
        this.dosagem = dosagem;
    }
}
