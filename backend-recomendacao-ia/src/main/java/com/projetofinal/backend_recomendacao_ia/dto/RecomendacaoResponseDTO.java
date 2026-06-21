package com.projetofinal.backend_recomendacao_ia.dto;

public class RecomendacaoResponseDTO {
    
    private String sugestaoReceita;

    public RecomendacaoResponseDTO(String sugestaoReceita) {
        this.sugestaoReceita = sugestaoReceita;
    }

    // Getters e Setters
    public String getSugestaoReceita() {
        return sugestaoReceita;
    }

    public void setSugestaoReceita(String sugestaoReceita) {
        this.sugestaoReceita = sugestaoReceita;
    }
}