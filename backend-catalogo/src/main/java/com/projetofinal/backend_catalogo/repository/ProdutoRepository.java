package com.projetofinal.backend_catalogo.repository;

import com.projetofinal.backend_catalogo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}