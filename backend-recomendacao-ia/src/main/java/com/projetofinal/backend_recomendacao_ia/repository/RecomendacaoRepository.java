package com.projetofinal.backend_recomendacao_ia.repository;

import com.projetofinal.backend_recomendacao_ia.model.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
    // Apenas estendendo JpaRepository já ganha métodos como save(), findAll(), findById()
}