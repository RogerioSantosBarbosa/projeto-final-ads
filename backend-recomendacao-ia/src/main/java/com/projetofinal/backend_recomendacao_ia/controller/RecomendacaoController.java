package com.projetofinal.backend_recomendacao_ia.controller;

import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoRequestDTO;
import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoResponseDTO;
import com.projetofinal.backend_recomendacao_ia.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @PostMapping("/recomendacoes")
    public ResponseEntity<RecomendacaoResponseDTO> obterRecomendacao(@RequestBody RecomendacaoRequestDTO request) {
        // Chama o cérebro passando os dados que chegaram
        RecomendacaoResponseDTO resposta = recomendacaoService.gerarRecomendacao(request);
        
        // Devolve o status 200 OK com o JSON da receita
        return ResponseEntity.ok(resposta);
    }
}