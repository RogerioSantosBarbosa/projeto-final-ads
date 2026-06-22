package com.projetofinal.backend_recomendacao_ia.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_recomendacoes")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guarda o que o usuário pediu
    @Column(nullable = false)
    private String produtosCarrinho;

    @Column(nullable = false)
    private String objetivoDieta;

    // Guarda a resposta da IA (TEXT permite strings muito longas)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String sugestaoGerada;

    // Data e hora em que a dieta foi gerada
    private LocalDateTime dataCriacao;

    // Construtor vazio obrigatório para o Spring JPA
    public Recomendacao() {
    }

    public Recomendacao(String produtosCarrinho, String objetivoDieta, String sugestaoGerada) {
        this.produtosCarrinho = produtosCarrinho;
        this.objetivoDieta = objetivoDieta;
        this.sugestaoGerada = sugestaoGerada;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProdutosCarrinho() { return produtosCarrinho; }
    public void setProdutosCarrinho(String produtosCarrinho) { this.produtosCarrinho = produtosCarrinho; }

    public String getObjetivoDieta() { return objetivoDieta; }
    public void setObjetivoDieta(String objetivoDieta) { this.objetivoDieta = objetivoDieta; }

    public String getSugestaoGerada() { return sugestaoGerada; }
    public void setSugestaoGerada(String sugestaoGerada) { this.sugestaoGerada = sugestaoGerada; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}