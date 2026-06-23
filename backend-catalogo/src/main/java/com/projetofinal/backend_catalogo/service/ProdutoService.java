package com.projetofinal.backend_catalogo.service;

import com.projetofinal.backend_catalogo.model.Produto;
import com.projetofinal.backend_catalogo.repository.ProdutoRepository;
import com.projetofinal.backend_catalogo.producer.ProdutoProducer;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoProducer produtoProducer; // <-- INJETAMOS O PRODUCER AQUI

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoProducer produtoProducer) {
        this.produtoRepository = produtoRepository;
        this.produtoProducer = produtoProducer;
    }

    public List<Produto> listarProdutos() { return produtoRepository.findAll(); }

    public Optional<Produto> buscarPorId(Long id) { return produtoRepository.findById(id); }

    public Produto salvarProduto(Produto produto) {
        Produto produtoSalvo = produtoRepository.save(produto);
        
        // <-- DISPARA A MENSAGEM AQUI
        produtoProducer.notificarProdutoCriado(produtoSalvo); 
        
        return produtoSalvo;
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) { produtoRepository.deleteById(id); }
}