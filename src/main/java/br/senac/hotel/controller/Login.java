package br.senac.hotel.controller;

import br.senac.hotel.models.Usuario;

public class Login {
    private SistemaCadastro sistemaCadastro;

    public Login(SistemaCadastro sistemaCadastro) {
        this.sistemaCadastro = sistemaCadastro;
    }

    public boolean autenticar(String email, String senha) {
        Usuario usuario = sistemaCadastro.buscarPorEmail(email);

        if (usuario == null) {
            return false;
        }
            return usuario.login(email, senha);
        }
    }
