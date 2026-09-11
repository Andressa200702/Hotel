package br.senac.hotel.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hospede {
    private Usuario usuario;
    private String documento;

    public Hospede(String nome, String email, String senha) {
        this.usuario = new Usuario();
        this.usuario.setNome(nome);
        this.usuario.setEmail(email);
        this.usuario.setSenha(senha);
    }


}
