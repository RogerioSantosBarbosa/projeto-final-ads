package com.projetofinal.backend_recomendacao_ia.controller;

import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoRequestDTO;
import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoResponseDTO;
import com.projetofinal.backend_recomendacao_ia.model.Recomendacao;
import com.projetofinal.backend_recomendacao_ia.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes") // Garante que POST e GET partilham este endereço base
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    // Rota para a IA gerar e guardar a dieta (POST)
    @PostMapping
    public ResponseEntity<RecomendacaoResponseDTO> gerarRecomendacao(@RequestBody RecomendacaoRequestDTO request) {
        return ResponseEntity.ok(recomendacaoService.gerarRecomendacao(request));
    }

    // Rota para o Godot obter o histórico (GET)
    @GetMapping
    public ResponseEntity<List<Recomendacao>> listarRecomendacoes() {
        return ResponseEntity.ok(recomendacaoService.listarHistorico());
    }
}