package com.projetofinal.backend_catalogo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_PRODUTOS = "catalogo.produtos.queue";
    public static final String EXCHANGE_PRODUTOS = "catalogo.exchange";
    public static final String ROUTING_KEY = "produto.criado";

    @Bean
    public Queue filaProdutos() {
        return new Queue(FILA_PRODUTOS, true);
    }

    @Bean
    public DirectExchange exchangeProdutos() {
        return new DirectExchange(EXCHANGE_PRODUTOS);
    }

    @Bean
    public Binding bindingProdutos(Queue filaProdutos, DirectExchange exchangeProdutos) {
        return BindingBuilder.bind(filaProdutos).to(exchangeProdutos).with(ROUTING_KEY);
    }

    // Garante que o objeto Produto seja convertido em JSON ao ser enviado
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}