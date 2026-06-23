package com.projetofinal.backend_pedidos.event;

import java.time.LocalDateTime;

public class PedidoCriadoEvent {

    private Long pedidoId;
    private Double total;
    private LocalDateTime data;

    public PedidoCriadoEvent() {}

    public PedidoCriadoEvent(Long pedidoId, Double total, LocalDateTime data) {
        this.pedidoId = pedidoId;
        this.total = total;
        this.data = data;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}