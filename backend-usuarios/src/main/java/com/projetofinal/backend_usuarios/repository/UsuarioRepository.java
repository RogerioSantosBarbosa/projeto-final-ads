package com.projetofinal.backend_usuarios.repository;

import com.projetofinal.backend_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}