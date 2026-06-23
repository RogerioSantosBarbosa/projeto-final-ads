package com.projetofinal.backend_pedidos.repository;

import com.projetofinal.backend_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}