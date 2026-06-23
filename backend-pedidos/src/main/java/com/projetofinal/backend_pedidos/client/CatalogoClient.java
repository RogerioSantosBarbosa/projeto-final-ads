package com.projetofinal.backend_pedidos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CatalogoClient {

    private final RestTemplate restTemplate;

    @Value("${catalogo.url}")
    private String catalogoUrl;

    public CatalogoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> buscarProduto(Long produtoId) {
        try {
            String url = catalogoUrl + "/produtos/" + produtoId;
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return null;
        }
    }
}