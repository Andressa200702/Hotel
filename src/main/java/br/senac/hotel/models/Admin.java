package br.senac.hotel.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Admin {
    private String nome;
    private String usuario;

    public Admin(String nome, String usuario) {
        this.nome = nome;
        this.usuario = usuario;
    }
}
