package com.projetofinal.backend_pedidos.service;

import com.projetofinal.backend_pedidos.client.CatalogoClient;
import com.projetofinal.backend_pedidos.config.RabbitMQConfig;
import com.projetofinal.backend_pedidos.event.PedidoCriadoEvent;
import com.projetofinal.backend_pedidos.model.Pedido;
import com.projetofinal.backend_pedidos.repository.PedidoRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final CatalogoClient catalogoClient;

    public PedidoService(PedidoRepository repository,
                         RabbitTemplate rabbitTemplate,
                         CatalogoClient catalogoClient) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.catalogoClient = catalogoClient;
    }

    public Pedido criarPedido(Pedido pedido) {

        pedido.getItens().removeIf(item -> {

            Map<String, Object> produto =
                    catalogoClient.buscarProduto(item.getProdutoId());

            if (produto == null) {
                System.out.println("⚠ Produto não encontrado: " + item.getProdutoId());
                return true;
            }

            item.setNomeProduto((String) produto.get("nome"));
            item.setPrecoUnitario(((Number) produto.get("preco")).doubleValue());

            return false;
        });

        if (pedido.getItens().isEmpty()) {
            throw new RuntimeException("Nenhum item válido no pedido");
        }

        pedido.setData(LocalDateTime.now());
        pedido.setStatus("CRIADO");

        double total = pedido.getItens()
                .stream()
                .mapToDouble(i -> i.getPrecoUnitario() * i.getQuantidade())
                .sum();

        pedido.setTotal(total);

        Pedido salvo = repository.save(pedido);

        PedidoCriadoEvent event = new PedidoCriadoEvent(
                salvo.getId(),
                salvo.getTotal(),
                salvo.getData()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PEDIDO_QUEUE,
                event
        );

        return salvo;
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listarTodos() {
        return repository.findAll();
    }
}