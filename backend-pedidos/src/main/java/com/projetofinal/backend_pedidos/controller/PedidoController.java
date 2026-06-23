package com.projetofinal.backend_pedidos.controller;

import com.projetofinal.backend_pedidos.model.Pedido;
import com.projetofinal.backend_pedidos.service.PedidoService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
    name = "Pedidos",
    description = "API responsável pelo gerenciamento de pedidos do sistema de e-commerce"
)
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Operation(
        summary = "Criar um novo pedido",
        description = "Cria um pedido com lista de itens e calcula automaticamente o valor total"
    )
    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        return service.criarPedido(pedido);
    }

    @Operation(
        summary = "Buscar pedido por ID",
        description = "Retorna um pedido específico com seus itens"
    )
    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(
        summary = "Listar todos os pedidos",
        description = "Retorna todos os pedidos cadastrados no sistema"
    )
    @GetMapping
    public List<Pedido> listarTodos() {
        return service.listarTodos();
    }
}