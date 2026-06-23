package com.projetofinal.backend_pedidos.consumer;

import com.projetofinal.backend_pedidos.config.RabbitMQConfig;
import com.projetofinal.backend_pedidos.event.PedidoCriadoEvent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = RabbitMQConfig.PEDIDO_QUEUE)
    public void receberMensagem(PedidoCriadoEvent event) {

        System.out.println("📩 EVENTO RECEBIDO DO RABBITMQ");
        System.out.println("--------------------------------");
        System.out.println("Pedido ID: " + event.getPedidoId());
        System.out.println("Total: " + event.getTotal());
        System.out.println("Data: " + event.getData());
        System.out.println("--------------------------------");
    }
}