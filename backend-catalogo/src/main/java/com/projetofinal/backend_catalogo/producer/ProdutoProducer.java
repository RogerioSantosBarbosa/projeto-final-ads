package com.projetofinal.backend_catalogo.producer;

import com.projetofinal.backend_catalogo.config.RabbitMQConfig;
import com.projetofinal.backend_catalogo.model.Produto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProdutoProducer {

    private final RabbitTemplate rabbitTemplate;

    public ProdutoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void notificarProdutoCriado(Produto produto) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_PRODUTOS, 
            RabbitMQConfig.ROUTING_KEY, 
            produto
        );
        System.out.println("Mensagem enviada pro RabbitMQ: Produto " + produto.getNome() + " cadastrado!");
    }
}