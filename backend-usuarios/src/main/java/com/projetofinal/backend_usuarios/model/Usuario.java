package com.projetofinal.backend_usuarios.model;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String preferenciaDietetica;

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferenciaDietetica() {
        return preferenciaDietetica;
    }

    public void setPreferenciaDietetica(String preferenciaDietetica) {
        this.preferenciaDietetica = preferenciaDietetica;
    }
}