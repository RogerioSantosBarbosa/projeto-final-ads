package com.projetofinal.backend_catalogo.controller;

import com.projetofinal.backend_catalogo.model.Produto;
import com.projetofinal.backend_catalogo.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Tag(
    name = "Catálogo de Produtos",
    description = "Operações relacionadas aos produtos do supermercado"
)
public class CatalogoController {

    private final ProdutoService produtoService;

    public CatalogoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
        summary = "Listar produtos",
        description = "Retorna todos os produtos cadastrados no catálogo"
    )
    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @Operation(
        summary = "Cadastrar produto",
        description = "Adiciona um novo produto ao catálogo"
    )
    @PostMapping("/produtos")
    public Produto salvarProduto(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

   @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna um produto específico pelo ID"
    )
    @GetMapping("/produtos/{id}")
    public Optional<Produto> buscarProduto(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza os dados de um produto existente"
    )
    @PutMapping("/produtos/{id}")
    public Produto atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produto produto) {

        return produtoService.atualizarProduto(id, produto);
    }

    @Operation(
        summary = "Excluir produto",
        description = "Remove um produto do catálogo pelo ID"
    )
    @DeleteMapping("/produtos/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }
}