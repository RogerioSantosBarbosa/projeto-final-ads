package com.projetofinal.backend_recomendacao_ia.service;

import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoRequestDTO;
import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecomendacaoService {

    @Value("${ai.api-key}")
    private String apiKey;

    @Autowired
    private com.projetofinal.backend_recomendacao_ia.repository.RecomendacaoRepository repository;

    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-3.5-flash:generateContent?key=";

    public RecomendacaoResponseDTO gerarRecomendacao(RecomendacaoRequestDTO request) {

        // 1. Prompt Blindado com Ordem de Completude
        String instrucaoSistema = "INSTRUÇÕES DE SISTEMA: Você é um nutricionista clínico e chef de cozinha experiente. " +
            "Seu objetivo é analisar os ingredientes enviados e a restrição alimentar do usuário para garantir uma refeição 100% segura e nutritiva.\n\n" +
            
            "REGRA 1 (ALERTA DE SEGURANÇA): Se houver QUALQUER produto que ameace a restrição informada, inicie a resposta com '⚠️ ALERTA DE SAÚDE', listando os ingredientes perigosos e o motivo.\n\n" +
            
            "REGRA 2 (RECEITA): Logo após o alerta, crie a receita APENAS com os ingredientes seguros. É OBRIGATÓRIO conter: Nome do Prato, Ingredientes (com quantidades) e Modo de Preparo passo a passo.\n\n" +
            
            "REGRA 3 (COMPLETUDE): Não interrompa a resposta pela metade. Você DEVE escrever todo o modo de preparo até a etapa final de montagem/empratamento do prato.\n\n";

        String promptUsuario = "Ingredientes disponíveis no carrinho: " + String.join(", ", request.getProdutosCarrinho()) + 
                               "\nRestrição ou Objetivo alimentar: " + request.getObjetivoDieta();

        // 2. Construção do JSON 
        Map<String, Object> requestBody = new HashMap<>();
        
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", instrucaoSistema + promptUsuario);
        
        Map<String, Object> part = new HashMap<>();
        part.put("parts", List.of(textPart));
        
        requestBody.put("contents", List.of(part));

        // Configurações de precisão e segurança ajustadas
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("temperature", 0.3); // Equilíbrio entre fluidez e precisão técnica
        generationConfig.put("maxOutputTokens", 4096); // Margem gigante para garantir que o texto caiba
        
        requestBody.put("generationConfig", generationConfig);

        // 3. Preparação do Envio
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Dispara a requisição
            ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_API_URL + apiKey, entity, Map.class);

            // Navega na resposta
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            
            // Extração segura contra filtros de segurança (Safety Blocks)
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> contentMap = (Map<String, Object>) firstCandidate.get("content");
                
                if (contentMap != null && contentMap.containsKey("parts")) {
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentMap.get("parts");
                    String textoResposta = (String) parts.get(0).get("text");
                    
                    // --- SALVANDO NO BANCO DE DADOS ANTES DE DEVOLVER A RESPOSTA ---
                    String produtosString = String.join(", ", request.getProdutosCarrinho());
                    com.projetofinal.backend_recomendacao_ia.model.Recomendacao recomendacao = 
                        new com.projetofinal.backend_recomendacao_ia.model.Recomendacao(produtosString, request.getObjetivoDieta(), textoResposta);
                    
                    repository.save(recomendacao);
                    // ---------------------------------------------------------------

                    return new RecomendacaoResponseDTO(textoResposta);
                } else {
                    return new RecomendacaoResponseDTO("Aviso: A receita foi bloqueada pelos filtros de segurança da IA. Provavelmente os ingredientes informados entram em conflito grave com a sua restrição médica.");
                }
            }

            return new RecomendacaoResponseDTO("Não foi possível gerar a receita com os dados fornecidos.");

        } catch (Exception e) {
            System.err.println("Erro na comunicação com a API: " + e.getMessage());
            return new RecomendacaoResponseDTO("Desculpe, não consegui gerar uma recomendação no momento. Tente novamente mais tarde.");
        }
    }

    // Busca o histórico no banco
    public java.util.List<com.projetofinal.backend_recomendacao_ia.model.Recomendacao> listarHistorico() {
        return repository.findAll();
    }
}