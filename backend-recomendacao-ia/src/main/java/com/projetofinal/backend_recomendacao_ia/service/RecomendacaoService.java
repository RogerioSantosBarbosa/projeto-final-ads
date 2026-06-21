package com.projetofinal.backend_recomendacao_ia.service;

import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoRequestDTO;
import com.projetofinal.backend_recomendacao_ia.dto.RecomendacaoResponseDTO;
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

    // Puxa a chave de API guardada em application.properties
    @Value("${ai.api-key}")
    private String apiKey;

    // URL oficial da API do Gemini (usando o modelo rápido flash 1.5)
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-3.5-flash:generateContent?key=";

    public RecomendacaoResponseDTO gerarRecomendacao(RecomendacaoRequestDTO request) {
        
        // 1. Prompt Inteligente
        String prompt = "Atue como um nutricionista e chef de cozinha. O usuário quer uma dieta com o objetivo: " 
                        + request.getObjetivoDieta() + ". "
                        + "Crie uma sugestão de refeição utilizando os seguintes produtos disponíveis no mercado: " 
                        + String.join(", ", request.getProdutosCarrinho()) + ". "
                        + "Responda de forma direta, listando a receita e como usar os ingredientes.";

        // 2. Prepara o envio 
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(content));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 3. Dispara a requisição para o Google
            ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_API_URL + apiKey, entity, Map.class);

            // 4. Navega no JSON de resposta do Gemini para extrair apenas o texto final
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> contentMap = (Map<String, Object>) firstCandidate.get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) contentMap.get("parts");
            String textoResposta = (String) parts.get(0).get("text");

            return new RecomendacaoResponseDTO(textoResposta);

        } catch (Exception e) {
            System.err.println("Erro na comunicação com a API: " + e.getMessage());
            return new RecomendacaoResponseDTO("Desculpe, não consegui gerar uma recomendação no momento. Tente novamente mais tarde.");
        }
    }
}