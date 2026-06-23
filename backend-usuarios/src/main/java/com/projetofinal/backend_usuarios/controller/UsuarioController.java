package com.projetofinal.backend_usuarios.controller;

import com.projetofinal.backend_usuarios.model.Usuario;
import com.projetofinal.backend_usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(
    name = "Usuários",
    description = "Operações relacionadas aos usuários do sistema"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
        summary = "Listar usuários",
        description = "Retorna todos os usuários cadastrados"
    )
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna um usuário específico"
    )
    @GetMapping("/usuarios/{id}")
    public Optional<Usuario> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @Operation(
        summary = "Cadastrar usuário",
        description = "Cria um novo usuário"
    )
    @PostMapping("/usuarios")
    public Usuario salvarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados de um usuário"
    )
    @PutMapping("/usuarios/{id}")
    public Usuario atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        return usuarioService.atualizarUsuario(id, usuario);
    }

    @Operation(
        summary = "Excluir usuário",
        description = "Remove um usuário"
    )
    @DeleteMapping("/usuarios/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}