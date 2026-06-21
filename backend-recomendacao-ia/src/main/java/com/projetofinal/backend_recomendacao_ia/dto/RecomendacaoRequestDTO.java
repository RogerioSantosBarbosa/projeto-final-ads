package com.projetofinal.backend_recomendacao_ia.dto;

import java.util.List;

public class RecomendacaoRequestDTO {
    
    private List<String> produtosCarrinho;
    private String objetivoDieta; // Ex: "Hipercalórica", "Sem glúten"

    // Getters e Setters
    public List<String> getProdutosCarrinho() {
        return produtosCarrinho;
    }

    public void setProdutosCarrinho(List<String> produtosCarrinho) {
        this.produtosCarrinho = produtosCarrinho;
    }

    public String getObjetivoDieta() {
        return objetivoDieta;
    }

    public void setObjetivoDieta(String objetivoDieta) {
        this.objetivoDieta = objetivoDieta;
    }
}